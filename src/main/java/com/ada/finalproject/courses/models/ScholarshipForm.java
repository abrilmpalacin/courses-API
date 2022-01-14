package com.ada.finalproject.courses.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "scholarship_forms")
@JsonIgnoreProperties(value = {"studying", "working"})
public class ScholarshipForm {
	
	@ApiModelProperty(
			position = 0,
			dataType = "int",
	        example = "7",
	        required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ApiModelProperty(
			position = 1,
			dataType = "boolean",
	        example = "true",
	        required = true)
	@JsonProperty("isStudying")
	@NotNull
	private boolean isStudying;
	
	@ApiModelProperty(
			position = 2,
			dataType = "boolean",
	        example = "false",
	        required = true)
	@JsonProperty("isWorking")
	@NotNull
	private boolean isWorking;
	
	@ApiModelProperty(
			position = 3,
			dataType = "double",
	        example = "0",
	        required = true)
	private double incomes;
	
	@ApiModelProperty(
			position = 4,
			dataType = "int",
	        example = "0",
	        required = true)
	private int dependentFamilyMembers;	
	
	@ApiModelProperty(
			position = 5,
			dataType = "boolean",
	        example = "true",
	        required = false)
	@JsonProperty
	@NotNull
	private boolean pending = true;
	
	@ApiModelProperty(
			position = 6,
			dataType = "boolean",
	        example = "false",
	        required = false)
	@JsonProperty
	@NotNull
	private boolean approved = false;
	
	@ApiModelProperty(
			position = 7,
			dataType = "Inscription",
	        required = false)
	@JsonBackReference
	@OneToOne(optional = false)
	@JoinColumn(name = "inscription_id", referencedColumnName = "id")
	private Inscription inscription;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public boolean isStudying() {
		return isStudying;
	}

	public void setStudying(boolean isStudying) {
		this.isStudying = isStudying;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	public double getIncomes() {
		return incomes;
	}

	public void setIncomes(double incomes) {
		this.incomes = incomes;
	}

	public int getDependentFamilyMembers() {
		return dependentFamilyMembers;
	}

	public void setDependentFamilyMembers(int dependentFamilyMembers) {
		this.dependentFamilyMembers = dependentFamilyMembers;
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

	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}
	
}
