package com.ada.finalproject.courses.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import com.ada.finalproject.courses.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client {
	
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
	        example = "Abril Milagros Palacín",
	        required = true)
	@NotNull
	@NotEmpty
	private String fullName;
	
	@ApiModelProperty(
			position = 2,
			dataType = "String",
		    example = "abrilmpalacin@gmail.com",
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
			dataType = "Date",
			example = "pwd123",
			required = true)
	@NotNull
	private Date birthDate;
	
	@ApiModelProperty(
			position = 5,
			dataType = "String",
			example = "Female",
			required = true)
	@NotNull
	@NotEmpty
	private String gender;
	
	@ApiModelProperty(
			position = 6,
			dataType = "String",
			example = "Some Adress 123",
			required = true)
	@NotNull
	@NotEmpty
	private String adress;

	@ApiModelProperty(
			position = 8,
			dataType = "Inscription",
	        required = false)
	@Nullable
	@JsonManagedReference
	@OneToMany(mappedBy = "client", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIncludeProperties({"id", "course"})
	private List<Inscription> inscriptions = new ArrayList<>();

	@ApiModelProperty(
			position = 7,
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
	
	public Client() {
		
	}
	
	public Client(String email, String token) {
		this.email = email;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public List<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;		
	}

}
