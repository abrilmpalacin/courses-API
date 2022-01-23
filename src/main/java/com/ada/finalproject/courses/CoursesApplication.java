package com.ada.finalproject.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ada.finalproject.courses.controllers.AdminRestController;
import com.ada.finalproject.courses.models.User;
import com.ada.finalproject.courses.services.UserService;

@SpringBootApplication
public class CoursesApplication {
	
	@Autowired
	AdminRestController adminController;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(CoursesApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			if (userService.count() == 0) { 
				User administrator = adminController.instantiate("administrator", "admin@appdomain.com", 
						encoder.encode("randompwd"));
				userService.save(administrator);
			}
		};
	}
	
}
