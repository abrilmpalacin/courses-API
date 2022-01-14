package com.ada.finalproject.courses.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ada.finalproject.courses.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "representatives")
public class Representative {
	
	@ApiModelProperty(
			position = 0,
			dataType = "int",
	        example = "5",
	        required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ApiModelProperty(
			position = 1,
			dataType = "String",
	        example = "Ezequiel González",
	        required = true)
	@NotNull
	@NotEmpty
	private String fullName;
	
	@ApiModelProperty(
			position = 2,
			dataType = "String",
	        example = "Co-Founder & CEO",
	        required = true)
	@NotNull
	@NotEmpty
	private String position;
	
	@ApiModelProperty(
			position = 3,
			dataType = "Company",
	        required = false)
	@JsonBackReference
	@OneToOne(optional = false)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;
	
	@ApiModelProperty(
			position = 4,
			dataType = "String",
			example = "someemail@adaitw.com.ar",
	        required = true)
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@ApiModelProperty(
			position = 5,
			dataType = "String",
			example = "otherpwd123",
	        required = true,
	        hidden = true)
	@NotNull
	@NotEmpty
	private String password;
	
	@ApiModelProperty(
			position = 6,
			dataType = "String",
			example = "Bearer eyJhbGciOiJIUzUxMiJ9"
					+ ".eyJqdGkiOiJhZGFKV1QiLCJzdWIiOiJlemVxdWllbGdvbnphbGV6QGFkYWl0dy5jb20uYXIiLCJhdXRob3JpdG"
					+ "llcyI6WyJST0xFX1JFUFJFU0VOVEFUSVZFIl0sImlhdCI6MTY0MjE1MTMwMSwiZXhwIjoxNjQyMTUxOTAxfQ"
					+ ".VDh0pDwzgsO8tpHpliV9b3SYQyULmUDj76SHnx5a1uFmtMvd-gqVxqRmQf2s9si6C78jbU6f192NkTlzuaVzWg",
	        required = false)
	@NotNull
	@NotEmpty
	@Column(length = 1000)
	private String token;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;		
	}
	
}
