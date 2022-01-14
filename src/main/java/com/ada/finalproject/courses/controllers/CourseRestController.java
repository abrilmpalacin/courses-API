package com.ada.finalproject.courses.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ada.finalproject.courses.models.Client;
import com.ada.finalproject.courses.models.Company;
import com.ada.finalproject.courses.models.Course;
import com.ada.finalproject.courses.models.Inscription;
import com.ada.finalproject.courses.models.Representative;
import com.ada.finalproject.courses.services.AdminService;
import com.ada.finalproject.courses.services.ClientService;
import com.ada.finalproject.courses.services.CompanyService;
import com.ada.finalproject.courses.services.CourseService;
import com.ada.finalproject.courses.services.InscriptionService;
import com.ada.finalproject.courses.services.RepresentativeService;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Creating, retrieving, updating and deleting courses and enrolling in them",
tags = {"courses"})
@RequestMapping("/api/v1/courses")
@RestController
public class CourseRestController {
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	RepresentativeService representativeService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	InscriptionRestController inscriptionRestController;
	
	@Autowired
	InscriptionService inscriptionService;
	
	@ApiOperation(value = "Register a course", 
				  notes = "The operation must be carried out by the representative of the company")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Resource created successfully", response = Course.class),
    		@ApiResponse(code = 500, message = "Something went wrong while processing request")
    })
	@PostMapping("")
	public Course newCourse(@RequestHeader (name = "Authorization") String token, 
			@RequestBody Course course) throws Exception {
		String secretKey = "myUltraSecretKey";
	
		Representative representative = representativeService
				.findByEmail(Jwts.parser()
					.setSigningKey(secretKey.getBytes())
				    .parseClaimsJws(token.replaceAll("\"", "").substring(7))
				    .getBody()
					.getSubject())
				.get();
		
		if (!representative.getCompany().isApproved()) {
			throw new Exception("You don't have permission to register a course");
		}
		
		int companyId = representative.getCompany().getId();
		Optional<Company> company = companyService.findById(companyId);
		course.setCompany(company.get());
		course.setActualQuota(course.getQuota());
		course.setActualAmountScholarships(course.getAmountScholarships());
		return courseService.save(course);
	}
	
	@ApiOperation(value = "Enroll in a course", 
			  notes = "Clients registered on the platform can enroll in courses requesting scholarship or not")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource created successfully", response = Inscription.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PostMapping("/{company}/{course}")
	public Inscription enrollCourse(@RequestHeader(name = "Authorization") String token, @PathVariable String company,
			@PathVariable("course") String name, @RequestBody Inscription inscription) throws Exception {					
		String secretKey = "myUltraSecretKey";
		
		Client client = clientService
				.findByEmail(Jwts.parser()
					.setSigningKey(secretKey.getBytes())
				    .parseClaimsJws(token.replaceAll("\"", "").substring(7))
				    .getBody()
					.getSubject())
				.get();
		
		inscription.setClient(client);
		
		Optional<Course> courseOpt = courseService.findByCompanyAndName(company, name);

		if (courseOpt.isPresent()) {
			Course course = courseOpt.get();
			inscription.setCourse(course);
			
			if (course.getActualQuota() <= course.getQuota()) {
				if (inscription.getScholarshipForm() == null) {
					// TODO: Payment Request API call
				}
				
				List<Inscription> recordHistoricalInscriptions = inscription.getClient().getInscriptions();

				List<Inscription> inscriptionsWithScholarshipGrantedInProgress =
						recordHistoricalInscriptions.stream().filter(ins -> ins.getScholarshipForm().isApproved()
						&& ins.getCourse().isInProgress())
						.collect(Collectors.toList());

				if (inscriptionsWithScholarshipGrantedInProgress.size() != 0) {
					throw new Exception("Can't apply for a new scholarship");
				}
				
				return inscriptionService.save(inscription);
				// inscriptionRestController.newInscription(token, inscription);
			} else {
				throw new Exception("Sorry, maximum number of enrollments available has been reached");
			}
		} else {
			throw new Exception("Selected course doesn't exist");
		}
	}
	
	@ApiOperation(value = "Retrieve all courses")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Course.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("")
	public Iterable<Course> all() {
		return courseService.findAll();
	}
	
	@ApiOperation(value = "Retrieve a course by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = Course.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/{id}")
	public Optional<Course> one(@PathVariable int id) {
		return courseService.findById(id);
	}
	
	@ApiOperation(value = "Retrieve all courses available for enroll in")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Course.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/available")
	public Iterable<Course> getAllAvailableCourses() {
		List<Course> courses = (List<Course>) courseService.findAll();
		
		List<Course> availableCourses =  courses.stream()  
				.filter(availableCourse -> availableCourse.getActualQuota() > 0)
                .collect(Collectors.toList());
		return availableCourses;
	}
	
	@ApiOperation(value = "Retrieve all courses by category")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Course.class),
	        @ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/category")
	public Iterable<Course> getAllCoursesByCategory(@RequestParam("name") String category) {
		return courseService.findAllByCategory(category);
	}
	
	@ApiOperation(value = "Retrieve all courses by company")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Course.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/company")
	public Iterable<Course> getAllCoursesByCompany(@RequestParam("name") String company) {
		return courseService.findAllByCompany(company);
	}
	
	@ApiOperation(value = "Retrieve course by searching it by company and course name")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = Course.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/{company}/{course}")
	public Optional<Course> getCourseByCategoryAndName(@PathVariable String company, @PathVariable("course") String name) {
		return courseService.findByCompanyAndName(company, name);
	}
	
	@ApiOperation(value = "Retrieve all courses in progress and with at least one client enrollment")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Course.class),
	        @ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/participants")
	public Iterable<Course> getAllCoursesByInProgress(@RequestParam boolean inProgress) {
		List<Course> coursesInProgress = (List<Course>) courseService.findAllByInProgress(inProgress);
		
		List<Course> coursesInProgressWithInscriptions = coursesInProgress.stream()
				.filter(courseInProgress -> courseInProgress.getActualQuota() < courseInProgress.getQuota())
				.collect(Collectors.toList());
		return coursesInProgressWithInscriptions;				
	}
	
	@ApiOperation(value = "Update a course by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource updated successfully", response = Course.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PutMapping("/{id}")
	public Course replaceCourse(@PathVariable int id, @RequestBody Course newCourse) {
		return courseService.findById(id)
			.map(course -> {
				course.setName(newCourse.getName());
				course.setDescription(newCourse.getDescription());
				course.setModality(newCourse.getModality());
				course.setHourLoad(newCourse.getHourLoad());
				course.setCategory(newCourse.getCategory());
				course.setQuota(newCourse.getQuota());
				course.setAmountScholarships(newCourse.getAmountScholarships());
				course.setCompany(newCourse.getCompany());
				// course.setEnrolls(newCourse.getEnrolls());
				// course.setScholarshipForms(newCourse.getScholarshipForms());
;		        return courseService.save(course);
		      })
		      .orElseGet(() -> {
		    	newCourse.setId(id);
		        return courseService.save(newCourse);
		      });
	}
	
	@ApiOperation(value = "Delete all courses")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("")
	public void deleteAllCourses() {
		courseService.deleteAll();
	}
	
	@ApiOperation(value = "Delete a course by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("/{id}")
	public void deleteCourse(@PathVariable int id) {
		courseService.deleteById(id);
	}
	
}
