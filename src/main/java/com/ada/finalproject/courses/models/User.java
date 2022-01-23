package com.ada.finalproject.courses.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ada.finalproject.courses.validation.ValidEmail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@ApiModelProperty(
			position = 0,
			dataType = "int",
	        example = "2",
	        required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ApiModelProperty(
			position = 1,
			dataType = "String",
	        example = "User Name",
	        required = true)
	@NotNull
	@NotEmpty
	private String fullName;
	
	@ApiModelProperty(
			position = 2,
			dataType = "String",
		    example = "whatever@gmail.com",
		    required = true)
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@ApiModelProperty(
			position = 3,
			dataType = "String",
			example = "pwd123",
			required = true,
			hidden = true)
	@NotNull
	@NotEmpty
	private String password;
	
	@ApiModelProperty(
			position = 4,
			dataType = "String",
			example = "Bearer eyJhbGciOiJIUzUxMiJ9."
					+ "eyJqdGkiOiJhZGFKV1QiLCJzdWIiOiJhYnJpbG1wYWxhY2luQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbIlJ"
					+ "PTEVfQ0xJRU5UIl0sImlhdCI6MTY0MjE0ODM3NSwiZXhwIjoxNjQyMTQ4OTc1fQ"
					+ ".nBOlkDZaCUTRDxjZs9HF4yMEBr38qlI4ruCS2XToCgaTOAtARkva5DCe-P00VpeQ0zkuiyQ_vpA13wscb0Fjkw",
			required = false)
	@NotNull
	@NotEmpty
	@Column(length = 1000)
	private String token;
	
	public User() {

	}
	
	public User(@NotNull @NotEmpty String fullName, @NotNull @NotEmpty String email, @NotNull @NotEmpty String password,
			@NotNull @NotEmpty String token) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.token = token;
	}

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
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;		
	}

}
