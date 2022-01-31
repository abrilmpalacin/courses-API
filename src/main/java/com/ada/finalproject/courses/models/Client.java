package com.ada.finalproject.courses.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client extends User {
		
	@ApiModelProperty(
			position = 4,
			dataType = "Date",
			example = "pwd123",
			required = true)
	@NotNull
	private LocalDate birthDate;
	
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
			position = 7,
			dataType = "Inscription",
	        required = false)
	@Nullable
	@JsonManagedReference
	@OneToMany(mappedBy = "client", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIncludeProperties({"id", "course"})
	private List<Inscription> inscriptions = new ArrayList<>();
	
	public Client() {
		
	}
	
	public Client(@NotNull @NotEmpty String fullName, @NotNull @NotEmpty String email, 
			@NotNull @NotEmpty String password, @NotNull @NotEmpty String token, 
			@NotNull LocalDate birthDate, @NotNull @NotEmpty String gender, @NotNull @NotEmpty String adress) {
		super(fullName, email, password, token);
		this.birthDate = birthDate;
		this.gender = gender;
		this.adress = adress;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
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

}
