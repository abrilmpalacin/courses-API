package com.ada.finalproject.courses.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "companies")
public class Company {
	
	@ApiModelProperty(
			position = 0,
			dataType = "int",
	        example = "3",
	        required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ApiModelProperty(
			position = 1,
			dataType = "String",
	        example = "Ada ITW",
	        required = true)
	@NotNull
	@NotEmpty
	private String name;
	
	@ApiModelProperty(
			position = 2,
			dataType = "String",
	        example = "123456789101",
	        required = true)
	@NotNull
	private long cuil;
	
	@ApiModelProperty(
			position = 3,
			dataType = "String",
	        example = "NPO",
	        required = true)
	@NotNull
	@NotEmpty
	private String legalForm;
	
	@ApiModelProperty(
			position = 4,
			dataType = "String",
	        example = "Tte. Gral. Juan Domingo Perón 698, Buenos Aires",
	        required = true)
	@NotNull
	@NotEmpty
	private String adress;
	
	@ApiModelProperty(
			position = 5,
			dataType = "String",
	        example = "Programming",
	        required = true)
	@NotNull
	@NotEmpty
	private String category;
	
	@ApiModelProperty(
			position = 6,
			dataType = "LocalDate",
	        example = "YYYY/mm/dd",
	        required = true)
	@NotNull
	private LocalDate foundationYear;
	
	@ApiModelProperty(
			position = 7,
			dataType = "long",
	        example = "348 444 4444",
	        required = true)
	@NotNull
	private long contactNumber;
	
	@ApiModelProperty(
			position = 9,
			dataType = "boolean",
			example = "false",
	        required = false)
	@JsonProperty
	@NotNull
	private boolean pending = true;
	
	@ApiModelProperty(
			position = 10,
			dataType = "boolean",
			example = "false",
	        required = false)
	@JsonProperty
	@NotNull
	private boolean approved = false;
	
	@ApiModelProperty(
			position = 8,
			dataType = "Representative",
	        required = true)
	@NotNull
	@JsonManagedReference
	@OneToOne(mappedBy = "company", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Representative representative;
	
	@ApiModelProperty(
			position = 11,
			dataType = "Course",
	        required = false)
	@Nullable
	@JsonManagedReference
	@OneToMany(mappedBy = "company", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Course> courses = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCuil() {
		return cuil;
	}

	public void setCuil(long cuil) {
		this.cuil = cuil;
	}

	public String getLegalForm() {
		return legalForm;
	}

	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getFoundationYear() {
		return foundationYear;
	}

	public void setFoundationYear(LocalDate foundationYear) {
		this.foundationYear = foundationYear;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Representative getRepresentative() {
		return representative;
	}

	public void setRepresentative(Representative representative) {
		this.representative = representative;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}	
	
}
