package com.ada.finalproject.courses.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
@JsonIgnoreProperties(value = {"scholarshipForms"})
public class Course {

	@ApiModelProperty(
			position = 0,
			dataType = "int",
	        example = "4",
	        required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ApiModelProperty(
			position = 1,
			dataType = "String",
	        example = "BackEnd",
	        required = true)
	@NotNull
	@NotEmpty
	private String name;
	
	@ApiModelProperty(
			position = 2,
			dataType = "String",
	        example = "BackEnd course description",
	        required = true)
	@NotNull
	@NotEmpty
	private String description;
	
	@ApiModelProperty(
			position = 3,
			dataType = "String",
	        example = "Remote",
	        required = true)
	@NotNull
	@NotEmpty
	private String modality;
	
	@ApiModelProperty(
			position = 4,
			dataType = "int",
	        example = "400",
	        required = true)
	@NotNull
	private int hourLoad;
	
	@ApiModelProperty(
			position = 5,
			dataType = "String",
	        example = "BackEnd Development",
	        required = true)
	@NotNull
	@NotEmpty
	private String category;
	
	@ApiModelProperty(
			position = 6,
			dataType = "int",
	        example = "50",
	        required = true)
	@NotNull
	private int quota;
	
	@ApiModelProperty(
			position = 7,
			dataType = "int",
	        example = "50",
	        required = false)
	private int actualQuota;
	
	@ApiModelProperty(
			position = 8,
			dataType = "int",
	        example = "10",
	        required = true)
	@NotNull
	private int amountScholarships;
	
	@ApiModelProperty(
			position = 9,
			dataType = "int",
	        example = "10",
	        required = false)
	private int actualAmountScholarships;
	
	@ApiModelProperty(
			position = 10,
			dataType = "boolean",
	        example = "true",
	        required = false)
	@JsonProperty
	@NotNull
	private boolean inProgress = true;
	
	@ApiModelProperty(
			position = 11,
			dataType = "Company",
	        required = false)
	@JsonBackReference
	@ManyToOne(optional = false)
    @JoinColumn(name = "FK_company_id", referencedColumnName = "id", nullable = true)
	@JsonIncludeProperties({"id"})
	private Company company;
	
	@ApiModelProperty(
			position = 12,
			dataType = "Inscription",
	        required = false)
	@Nullable
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_course_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Inscription> inscriptions = new ArrayList<>();
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public int getHourLoad() {
		return hourLoad;
	}

	public void setHourLoad(int hourLoad) {
		this.hourLoad = hourLoad;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}
	
	public int getActualQuota() {
		return actualQuota;
	}

	public void setActualQuota(int actualQuota) {
		this.actualQuota = actualQuota;
	}

	public int getAmountScholarships() {
		return amountScholarships;
	}

	public void setAmountScholarships(int amountScholarships) {
		this.amountScholarships = amountScholarships;
	}
	
	public int getActualAmountScholarships() {
		return actualAmountScholarships;
	}

	public void setActualAmountScholarships(int actualAmountScholarships) {
		this.actualAmountScholarships = actualAmountScholarships;
	}

	public int getId() {
		return id;
	}
	
	public boolean isInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}
	
}
