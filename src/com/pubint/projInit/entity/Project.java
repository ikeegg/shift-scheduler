package com.pubint.projInit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "project")
@XmlRootElement(name = "project")
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "projectID")
	private String projectID;

	@Column(name = "itemNumber")
	private String itemNumber;

	@Column(name = "category")
	private Integer categoryID;

	@Column(name = "line")
	private String lineCode;

	@Column(name = "isbn13")
	private String isbn13;

	@Transient
	private String title;

	@Column(name = "pageCount")
	private long pageCount;

	@Column(name = "trimSize")
	private String trimSize;

	@Column(name = "color")
	private String color;

	@Column(name = "binding")
	private String binding;

	@Column(name = "status")
	private String status;

	@Column(name = "editor")
	private String editor;

	@Column(name = "digitalServices")
	private String digitalServices;

	@Column(name = "artDirector")
	private String artDirector;

	public Project() {
		super();
	}

	public Project(String projectID, String itemNumber) {
		this();

		this.projectID = projectID;
		this.itemNumber = itemNumber;
	}

	@Override
	public String toString() {
		return "" +
			"Project (id: " + id + ") =>\n" +
			"{" +
			"projectID: " + projectID + "\n" +
			"isbn13: " + isbn13 + "}";
	}

	public Project copy() {
		return new Project(projectID, itemNumber);
	}

	@XmlAttribute(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public String getTrimSize() {
		return trimSize;
	}

	public void setTrimSize(String trimSize) {
		this.trimSize = trimSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getDigitalServices() {
		return digitalServices;
	}

	public void setDigitalServices(String digitalServices) {
		this.digitalServices = digitalServices;
	}

	public String getArtDirector() {
		return artDirector;
	}

	public void setArtDirector(String artDirector) {
		this.artDirector = artDirector;
	}
}
