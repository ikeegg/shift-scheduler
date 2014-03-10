package com.pubint.projInit.session;

import java.io.StringWriter;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.xpath.XPath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.pubint.projInit.base.Base;
import com.pubint.projInit.entity.Project;
import com.pubint.projInit.entityList.ProjectList;
import com.pubint.projInit.exception.MissingProjectException;

@Stateless
@LocalBean
public class ProjectBean extends Base {
	private static final long serialVersionUID = 1L;

	static Logger log = LoggerFactory.getLogger(ProjectBean.class);

	@PersistenceContext(unitName = "pilCommon")
	private EntityManager entityManager;

	public ProjectBean() {
		super();
	}

	public String getXML(long projectID) throws MissingProjectException {
		return getXML(getProject(projectID), null);
	}

	public String getXML(long projectID, String title) throws MissingProjectException {
		return getXML(getProject(projectID), title);
	}

	public String getXML(String mas90ProjectID) throws MissingProjectException {
		return getXML(getProject(mas90ProjectID), null);
	}

	public String getXML(String mas90ProjectID, String title) throws MissingProjectException {
		return getXML(getProject(mas90ProjectID), title);
	}

	public String getXML(Project project, String title) {
		if (project == null) {
			return "";
		}

		project.setTitle(title);

		StringWriter xml = new StringWriter();

		try {
			JAXBContext context = JAXBContext.newInstance(project.getClass());

			Marshaller m = context.createMarshaller();

			m.marshal(project, xml);
		} catch (JAXBException je) {
			log.error("Exception trying to generate XML for project: " + project.getProjectID(), je);
		}

		return fixXML(xml);
	}

	public Project getProject(long projectID) throws MissingProjectException {
		return entityManager.find(Project.class, projectID);
	}

	public Project getProject(String mas90ProjectID) throws MissingProjectException {
		log.debug("Getting project (for real now): " + mas90ProjectID);

		String sql = "" +
			"select p from Project p " +
			"where " +
			"(p.projectID = :projectID)";

		Query query = entityManager.createQuery(sql);

		query.setParameter("projectID", mas90ProjectID);
		query.setMaxResults(1);

		if ((query.getResultList() == null) || query.getResultList().isEmpty()) {
			log.error("Result list is null...Project not found!");

			throw new MissingProjectException("Unable to find project: " + mas90ProjectID);
		}

		log.debug("Found the project...now return it...");

		return (Project) query.getSingleResult();
	}

	public String updateProject(Element element, XPath xpath) {
		return "";
	}

	public String deleteProject(Element element, XPath xpath) {
		return "";
	}

	public String selectList() {
		return selectList(null);
	}

	public String selectList(Integer category) {
		String sql;
		Query query;

		if (category == null) {
			sql = "" +
				"select p from Project p " +
				"order by " +
				"p.projectID";

			query = entityManager.createQuery(sql);
		} else {
			sql = "" +
				"select p from Project p " +
				"where " +
				"(p.categoryID = :cat) " +
				"order by " +
				"p.projectID";

			query = entityManager.createQuery(sql);
			query.setParameter("cat", category);
		}

		if (query.getResultList().isEmpty()) {
			return null;
		}

		@SuppressWarnings("unchecked") ProjectList projects = new ProjectList(query.getResultList());

		StringWriter xml = new StringWriter();

		try {
			JAXBContext context = JAXBContext.newInstance(projects.getClass());

			Marshaller m = context.createMarshaller();

			m.marshal(projects, xml);
		} catch (JAXBException je) {
			log.error("Exception trying to generate XML for projectList having " + projects.getProjects().size() + " projects...", je);
		}

		return fixXML(xml);
	}
}
