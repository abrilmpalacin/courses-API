package com.ada.finalproject.courses.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ada.finalproject.courses.models.User;
import com.ada.finalproject.courses.models.Company;
import com.ada.finalproject.courses.models.Course;
import com.ada.finalproject.courses.models.Inscription;
import com.ada.finalproject.courses.models.ScholarshipForm;
import com.ada.finalproject.courses.services.UserService;
import com.ada.finalproject.courses.services.ClientService;
import com.ada.finalproject.courses.services.CompanyService;
import com.ada.finalproject.courses.services.InscriptionService;
import com.ada.finalproject.courses.services.ScholarshipFormService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Retrieving administrator, and manage company registration and enrollment requests",
tags = {"admin"})
@RequestMapping("/api/v1/admin")
@RestController
public class AdminRestController {

	@Autowired 
	UserService userService;
	
	@Autowired 
	ClientService clientService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	InscriptionService inscriptionService;
	
	@Autowired
	ScholarshipFormService scholarshipFormService;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	public User instantiate(String name, String email, String password) {
		String token = getJWTToken(email);
		User administrator = new User(name, email, password, token);
		return administrator;
	}

	
	@ApiOperation(value = "Retrieve the administrator of the platform, admin log-in", 
			  notes = "The operation must be carried out by the admin")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = User.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PostMapping("/sign-in")
	public User login(@RequestParam String email, @RequestParam("password") String pwd) throws Exception {
		String token = getJWTToken(email);
        		
		Optional<User> userOpt = userService.findByEmail(email);
		
		if (userOpt.isPresent()) {
			String pwdStored = userOpt.get().getPassword();
			if (pwd != pwdStored) {
				User admin = userOpt.get();
				admin.setToken(token);
				return userService.save(admin);
			} else {
				throw new Exception("Invalid password entered.");
			}
		} else {
			throw new Exception("Account not registered. Please, sign-up.");
		}
	}
	
	@ApiOperation(value = "Accept or reject registration requests for submitting courses from company representatives")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources managed successfully", response = Company.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PostMapping("/requests/companies/{ids}")
	public Iterable<Company> manageCompanyRequests(@PathVariable List<Integer> ids, 
			@RequestParam boolean approve) {
		List<Company> companies = (List<Company>) companyService.findAllById(ids);
				
		List<Company> managedRequests =  companies.stream()  
				.map(company -> {
					company.setApproved(approve);
					company.setPending(false);
					return companyService.save(company);
				})
                .collect(Collectors.toList());
		return managedRequests;
	}
	
	@ApiOperation(value = "Accept or decline client scholarship forms")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources managed successfully", response = Inscription.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PostMapping("/requests/inscriptions/{ids}")
	public Iterable<Inscription> manageInscriptionRequests(@PathVariable List<Integer> ids, 
			@RequestParam boolean approve) {
		List<Inscription> inscriptionsRequested = (List<Inscription>) inscriptionService.findAllById(ids);
						
		List<Inscription> managedRequests =  inscriptionsRequested.stream()  
				.map(inscriptionRequested -> {			
					ScholarshipForm sch = inscriptionRequested.getScholarshipForm();
					sch.setApproved(approve);
					sch.setPending(false);
					
					if (approve) {
						Course course = inscriptionRequested.getCourse();
						course.setActualQuota(course.getActualQuota() - 1);
						course.setActualAmountScholarships(course.getActualAmountScholarships() - 1);
					}
					return inscriptionService.save(inscriptionRequested);
				})
                .collect(Collectors.toList());
		return managedRequests;
	}
	
	@ApiOperation(value = "Retrieve all company registration requests pending approval")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Company.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PreAuthorize("hasRole('ADMIN')")  
	@GetMapping("/requests/companies")
	public Iterable<Company> showPendingCompanyRequests() {
		List<Company> companies = (List<Company>) companyService.findAll();
		
		List<Company> pendingRequests =  companies.stream()  
				.filter(company -> company.isPending() == true)
                .collect(Collectors.toList());
		return pendingRequests; 
	}
	
	@ApiOperation(value = "Retrieve all scholarship forms pending approval")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Inscription.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/requests/inscriptions")
	public List<Inscription> showPendingInscriptionRequests() {
		List<Inscription> inscriptions = (List<Inscription>) inscriptionService.findAll();
		
		List<Inscription> pendingRequests =  inscriptions.stream()  
				.filter(inscription -> inscription.getScholarshipForm().isPending() == true)
                .collect(Collectors.toList());
		return pendingRequests; 
	}
	
	private String getJWTToken(String email) {
		String secretKey = "myUltraSecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
		
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
