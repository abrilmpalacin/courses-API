package com.ada.finalproject.courses.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ada.finalproject.courses.validation.ValidEmail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
public class Admin {

	@ApiModelProperty(
			position = 0,
			dataType = "int",
	        example = "1",
	        required = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ApiModelProperty(
			position = 1,
			dataType = "String",
	        example = "admin",
	        required = false)
	@NotNull
	@NotEmpty
	private String name;
	
	@ApiModelProperty(
			position = 2,
			dataType = "String",
	        example = "admin@appdomain.com",
	        required = true)
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@ApiModelProperty(
			position = 3,
			dataType = "String",
	        example = "randompwd",
	        required = true,
	        hidden = true)
	@NotNull
	@NotEmpty
	private String password;
	
	@ApiModelProperty(
			position = 4,
			dataType = "String",
	        example = "Bearer eyJhbGciOiJIUzUxMiJ9"
	        		+ ".eyJqdGkiOiJhZGFKV1QiLCJzdWIiOiJhZG1pbkBhcHBkb21haW4uY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV"
	        		+ "9BRE1JTiJdLCJpYXQiOjE2NDIxNDg4NzgsImV4cCI6MTY0MjE0OTQ3OH0"
	        		+ ".B8IPmfxBNlOD91C-ZHr531gmGkzB2PZnciwv3pICiVZd3bUsjWvX7OM2NjCjWzO8_d6fOeXJ8-iujk--b9Cn0Q",
	        required = false)
	@NotNull
	@NotEmpty
	@Column(length = 1000)
	private String token;
	
	public Admin() {
		
	}

	public Admin(@NotNull @NotEmpty String name, @NotNull @NotEmpty String email,
			@NotNull @NotEmpty String password, @NotNull @NotEmpty String token) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
