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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pubint.projInit.base.Base;
import com.pubint.projInit.ksEntity.KsProject;

@Stateless
@LocalBean
public class KsProjectBean extends Base {
	private static final long serialVersionUID = 1L;

	static Logger log = LoggerFactory.getLogger(KsProjectBean.class);

	@PersistenceContext(unitName = "kitchenSync")
	private EntityManager entityManager;

	public KsProjectBean() {
		super();
	}

	public String getXML(String projectID) {
		return getXML(getKsProject(projectID));
	}

	public String getXML(KsProject project) {
		if (project == null) {
			return "";
		}

		StringWriter xml = new StringWriter();

		try {
			JAXBContext context = JAXBContext.newInstance(project.getClass());

			Marshaller m = context.createMarshaller();

			m.marshal(project, xml);
		} catch (JAXBException je) {
			log.error("Exception trying to generate XML for project: " + project.getMas90ProjectID(), je);
		}

		return fixXML(xml);
	}

	public KsProject getKsProject(String mas90ProjectID) {
		String sql = "" +
			"select p from KsProject p " +
			"where " +
			"(p.mas90ProjectID = :projectID)" +
			"";

		Query query = entityManager.createQuery(sql);
		query.setParameter("projectID", mas90ProjectID);
		query.setMaxResults(1);

		if (query.getResultList().isEmpty()) {
			return new KsProject();
		}

		KsProject project = (KsProject) query.getSingleResult();

		return project;
	}
}
