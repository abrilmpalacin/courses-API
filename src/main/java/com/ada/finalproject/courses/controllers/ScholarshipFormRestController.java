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

import com.ada.finalproject.courses.models.ScholarshipForm;
import com.ada.finalproject.courses.services.ClientService;
import com.ada.finalproject.courses.services.CourseService;
import com.ada.finalproject.courses.services.ScholarshipFormService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Retrieving, updating and deleting scholarships",
tags = {"scholarships"})
@RequestMapping("/api/v1/scholarships")
@RestController
public class ScholarshipFormRestController {

	@Autowired
	ScholarshipFormService scholarshipFormService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	CourseService courseService;
		
	@ApiOperation(value = "Retrieve all scholarship forms")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = ScholarshipForm.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("")
	public Iterable<ScholarshipForm> all() {
		return scholarshipFormService.findAll();
	}
	
	@ApiOperation(value = "Retrieve a scholarship form by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = ScholarshipForm.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/{id}")
	public Optional<ScholarshipForm> one(@PathVariable int id) {
		return scholarshipFormService.findById(id);
	}
	
	@ApiOperation(value = "Update a scholarship form by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource updated successfully", response = ScholarshipForm.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PutMapping("/{id}")
	public ScholarshipForm replaceScholarshipForm(@PathVariable int id, @RequestBody ScholarshipForm newScholarshipForm) {
		return scholarshipFormService.findById(id)
			.map(scholarshipForm -> {
				scholarshipForm.setStudying(newScholarshipForm.isStudying());
				scholarshipForm.setWorking(newScholarshipForm.isWorking());
				scholarshipForm.setIncomes(newScholarshipForm.getIncomes());
				scholarshipForm.setDependentFamilyMembers(newScholarshipForm.getDependentFamilyMembers());
;		        return scholarshipFormService.save(scholarshipForm);
		      })
		      .orElseGet(() -> {
		    	newScholarshipForm.setId(id);
		        return scholarshipFormService.save(newScholarshipForm);
		      });
	}
	
	@ApiOperation(value = "Delete all scholarship forms")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("")
	public void deleteAllScholarshipForms() {
		scholarshipFormService.deleteAll();
	}
	
	@ApiOperation(value = "Delete a scholarship form by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("/{id}")
	public void deleteScholarshipForm(@PathVariable int id) {
		scholarshipFormService.deleteById(id);
	}
	
}
