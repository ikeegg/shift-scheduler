package com.pubint.projInit.pojo;

import javax.annotation.PostConstruct;

import com.pubint.projInit.entity.Project;

public class ProjectControl {
	private Project project;

	@PostConstruct
	public void initialize() {

	}

	public Project getProject() {
		return project;
	}

	public void findProject(String mas90ProjectID) {

	}
}
