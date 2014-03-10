package com.pubint.projInit.ksEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "Project")
@XmlRootElement(name = "ksProject")
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class KsProject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Project_Id")
	private Long id;

	@Column(name = "Project_Status_Id")
	private Long statusID;

	@Column(name = "Is_Internal")
	private Boolean internal;

	@Column(name = "Project_Type_Id")
	private Long projectTypeID;

	@Column(name = "Project_Title")
	private String title;

	@Column(name = "Mas90Number")
	private String mas90ProjectID;

	public KsProject() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getStatusID() {
		return statusID;
	}

	public void setStatusID(Long statusID) {
		this.statusID = statusID;
	}

	public Boolean getInternal() {
		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;
	}

	public Long getProjectTypeID() {
		return projectTypeID;
	}

	public void setProjectTypeID(Long projectTypeID) {
		this.projectTypeID = projectTypeID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMas90ProjectID() {
		return mas90ProjectID;
	}

	public void setMas90ProjectID(String mas90ProjectID) {
		this.mas90ProjectID = mas90ProjectID;
	}
}
