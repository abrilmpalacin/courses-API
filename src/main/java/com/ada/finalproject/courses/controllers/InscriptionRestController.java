package com.ada.finalproject.courses.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.finalproject.courses.models.Inscription;
import com.ada.finalproject.courses.models.ScholarshipForm;
import com.ada.finalproject.courses.services.ClientService;
import com.ada.finalproject.courses.services.CourseService;
import com.ada.finalproject.courses.services.InscriptionService;
import com.ada.finalproject.courses.services.ScholarshipFormService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Retrieving, updating and deleting inscriptions",
tags = {"inscriptions"})
@RequestMapping("/api/v1/inscriptions")
@RestController
public class InscriptionRestController {
	
	@Autowired
	InscriptionService inscriptionService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	ScholarshipFormService scholarshipFormService;
		
	@ApiOperation(value = "Retrieve all inscriptions")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Inscription.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("")
	public Iterable<Inscription> all() {
		return inscriptionService.findAll();
	}
	
	@ApiOperation(value = "Retrieve a inscription by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = Inscription.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/{id}")
	public Optional<Inscription> one(@PathVariable int id) {
		return inscriptionService.findById(id);
	}
	
	@ApiOperation(value = "Update a inscription by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource updated successfully", response = Inscription.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PutMapping("/{id}")
	public Inscription replaceInscription(@PathVariable int id, @RequestBody Inscription newInscription) {
		return inscriptionService.findById(id)
			.map(inscription -> {
				inscription.setCourse(newInscription.getCourse());
				inscription.setClient(newInscription.getClient());
				inscription.getScholarshipForm().setInscription(inscription);
				
				ScholarshipForm sch = newInscription.getScholarshipForm();
				sch.setInscription(inscription);
				inscription.setScholarshipForm(sch);
		        return inscriptionService.save(inscription);
		      })
		      .orElseGet(() -> {
		    	newInscription.setId(id);
		        return inscriptionService.save(newInscription);
		      });
	}
	
	@ApiOperation(value = "Delete all inscriptions")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("")
	public void deleteAllInscriptions() {
		inscriptionService.deleteAll();
	}
	
	@ApiOperation(value = "Delete a inscription by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("/{id}")
	public void deleteScholarshipForm(@PathVariable int id) {
		scholarshipFormService.deleteById(id);
	}
	
}
