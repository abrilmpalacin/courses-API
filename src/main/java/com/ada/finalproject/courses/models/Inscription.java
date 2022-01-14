package com.ada.finalproject.courses.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "inscriptions")
public class Inscription {

	@ApiModelProperty(
			position = 0,
			dataType = "int",
	        example = "6",
	        required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ApiModelProperty(
			position = 1,
			dataType = "Course",
	        required = false)
	@ManyToOne
    @JoinColumn(name = "FK_course_id", referencedColumnName = "id", insertable = true, updatable = true)
	@JsonIncludeProperties({"id", "name"})
	private Course course;
	
	@ApiModelProperty(
			position = 2,
			dataType = "Client",
	        required = false)
	@JsonBackReference
	@ManyToOne(optional = false)
	@JoinColumn(name = "FK_client_id", referencedColumnName = "id")
	@JsonIncludeProperties({"id"})
	private Client client;
	
	@ApiModelProperty(
			position = 3,
			dataType = "ScholarshipForm",
	        required = false)
	@Nullable
	@JsonManagedReference
	@OneToOne(mappedBy = "inscription", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIncludeProperties({"id"})
	private ScholarshipForm scholarshipForm = null;
	
	public Inscription() {
		
	}
	
	public Inscription(Course course, Client client, ScholarshipForm scholarshipForm) {
		this.course = course;
		this.client = client;
		this.scholarshipForm = scholarshipForm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public ScholarshipForm getScholarshipForm() {
		return scholarshipForm;
	}

	public void setScholarshipForm(ScholarshipForm scholarshipForm) {
		this.scholarshipForm = scholarshipForm;
	}
	
}
