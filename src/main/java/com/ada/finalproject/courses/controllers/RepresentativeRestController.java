package com.ada.finalproject.courses.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.finalproject.courses.models.Representative;
import com.ada.finalproject.courses.services.RepresentativeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Retrieving, updating and deleting representatives",
tags = {"representatives"})
@RequestMapping("/api/v1/representatives")
@RestController
public class RepresentativeRestController {

	@Autowired
	RepresentativeService representativeService;
	
	@Autowired
	BCryptPasswordEncoder encoder;
		
	@ApiOperation(value = "Retrieve all representatives")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Representative.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("")
	public Iterable<Representative> all() {
		return representativeService.findAll();
	}
	
	@ApiOperation(value = "Retrieve a representative by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = Representative.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/{id}")
	public Optional<Representative> one(@PathVariable int id) {
		return representativeService.findById(id);
	}
	
	@ApiOperation(value = "Update a representative by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource updated successfully", response = Representative.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PutMapping("/{id}")
	public Representative replaceRepresentative(@PathVariable int id, @RequestBody Representative newRepresentative) {
		return representativeService.findById(id)
			.map(representative -> {
				representative.setFullName(newRepresentative.getFullName());
				representative.setPosition(newRepresentative.getPosition());
				representative.setCompany(newRepresentative.getCompany());
				representative.setEmail(newRepresentative.getEmail());
				representative.setPassword(encoder.encode(newRepresentative.getPassword()));
;		        return representativeService.save(representative);
		      })
		      .orElseGet(() -> {
		    	newRepresentative.setId(id);
		        return representativeService.save(newRepresentative);
		      });
	}
	
	@ApiOperation(value = "Delete all representatives")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("")
	public void deleteAllRepresentatives() {
		representativeService.deleteAll();
	}
	
	@ApiOperation(value = "Delete a representative by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("/{id}")
	public void deleteRepresentative(@PathVariable int id) {
		representativeService.deleteById(id);
	}

}
