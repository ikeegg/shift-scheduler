package com.pubint.projInit.entityList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.pubint.projInit.entity.Project;

@XmlRootElement(name = "projects")
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProjectList {
	private List<Project> projects;

	public ProjectList() {
		super();

		projects = new ArrayList<Project>();
	}

	public ProjectList(List<Project> startingList) {
		this();

		setProjects(startingList);
	}

	@XmlElementWrapper(name = "projectList")
	@XmlElement(name = "project")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> startingList) {
		projects = startingList;
	}

	public void addProject(Project project) {
		if (! projects.contains(project)) {
			projects.add(project);
		}
	}

	public void dropProject(Project project) {
		if (projects.contains(project)) {
			projects.remove(project);
		}
	}
}
