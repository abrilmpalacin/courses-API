package com.ada.finalproject.courses.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ada.finalproject.courses.models.Company;
import com.ada.finalproject.courses.models.Representative;
import com.ada.finalproject.courses.services.CompanyService;
import com.ada.finalproject.courses.services.RepresentativeService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Creating, retrieving, updating and deleting companies, and representative log-in",
tags = {"companies"})
@RequestMapping("/api/v1/companies")
@RestController
public class CompanyRestController {

	@Autowired
	CompanyService companyService;
	
	@Autowired
	RepresentativeService representativeService;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@ApiOperation(value = "Register a company", 
			  notes = "The operation must be carried out by the representative of the company")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource created successfully", response = Company.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PostMapping("/sign-up")
	@ResponseBody
	public Company register(@RequestBody Company newCompany) throws Exception {
		String token = getJWTToken(newCompany.getRepresentative().getEmail());

		Optional<Representative> representativeOpt = representativeService.findByEmail(newCompany.getRepresentative().getEmail());
		
		if (representativeOpt.isEmpty()) {
			Representative newRepresentative = newCompany.getRepresentative();
			newRepresentative.setToken(token);
			String pwdHash = encoder.encode(newRepresentative.getPassword());
			newRepresentative.setPassword(pwdHash);
			return companyService.save(newCompany);
		} else {
			throw new Exception("Account already exists.");
		}
	}
	
	@ApiOperation(value = "Retrieve a company representative, representative log-in", 
			  notes = "The operation must be carried out the representative of the company")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = Representative.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PostMapping("/sign-in")
	public Representative login(@RequestParam String email, @RequestParam("password") String pwd) throws Exception {
		String token = getJWTToken(email);
		
		Optional<Representative> userOpt = representativeService.findByEmail(email);
		
		if (userOpt.isPresent()) {
			String pwdHash = userOpt.get().getPassword();
			if (encoder.matches(pwd, pwdHash)) {
				Representative representative = userOpt.get();
				representative.setToken(token);
				return representativeService.save(representative);
			} else {
				throw new Exception("Invalid password entered.");
			}
		} else {
			throw new Exception("Account not registered. Please, sign-up.");
		}
	}
	
	@ApiOperation(value = "Retrieve all companies")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Company.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("")
	public Iterable<Company> all() {
		return companyService.findAll();
	}
	
	@ApiOperation(value = "Retrieve a company by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = Company.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/{id}")
	public Optional<Company> one(@PathVariable int id) {
		return companyService.findById(id);
	}
	
	@ApiOperation(value = "Update a company by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource updated successfully", response = Company.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PutMapping("/{id}")
	public Company replaceCompany(@PathVariable int id, @RequestBody Company newCompany) {
		return companyService.findById(id)
			.map(company -> {
				company.setName(newCompany.getName());
				company.setCuil(newCompany.getCuil());
				company.setLegalForm(newCompany.getLegalForm());
				company.setAdress(newCompany.getAdress());
				company.setCategory(newCompany.getCategory());
				company.setFoundationYear(newCompany.getFoundationYear());
				company.setContactNumber(newCompany.getContactNumber());
;		        return companyService.save(company);
		      })
		      .orElseGet(() -> {
		    	newCompany.setId(id);
		        return companyService.save(newCompany);
		      });
	}
	
	@ApiOperation(value = "Delete all companies")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("")
	public void deleteAllCompanies() {
		companyService.deleteAll();
	}
	
	@ApiOperation(value = "Delete a company by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable int id) {
		companyService.deleteById(id);
	}
	
	private String getJWTToken(String email) {
		String secretKey = "myUltraSecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_REPRESENTATIVE");
		
		String token = Jwts
				.builder()
				.setId("adaJWT")
				.setSubject(email)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	
}
