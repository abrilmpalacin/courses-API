package com.ada.finalproject.courses.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "representatives")
public class Representative extends User {
	
	@ApiModelProperty(
			position = 5,
			dataType = "String",
	        example = "Co-Founder & CEO",
	        required = true)
	@NotNull
	@NotEmpty
	private String position;
	
	@ApiModelProperty(
			position = 6,
			dataType = "Company",
	        required = false)
	@JsonBackReference
	@OneToOne(optional = false)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
