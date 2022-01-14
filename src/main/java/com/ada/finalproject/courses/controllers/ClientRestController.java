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
import org.springframework.web.bind.annotation.RestController;

import com.ada.finalproject.courses.models.Client;
import com.ada.finalproject.courses.services.ClientService;
import com.ada.finalproject.courses.services.CourseService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Creating, retrieving, updating and deleting clients, and client log-in",
tags = {"clients"})
@RequestMapping("/api/v1/clients")
@RestController
public class ClientRestController {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	CourseRestController courseController;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@ApiOperation(value = "Register a client", 
			  notes = "The operation must be carried out by a client")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource created successfully", response = Client.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PostMapping("/sign-up")
	public Client register(@RequestBody Client newClient) throws Exception {
		String token = getJWTToken(newClient.getEmail());
		    
		Optional<Client> userOpt = clientService.findByEmail(newClient.getEmail());
		
		if (userOpt.isEmpty()) {	
			String pwdHash = encoder.encode(newClient.getPassword());
			newClient.setPassword(pwdHash);	
			newClient.setToken(token);
		    return clientService.save(newClient);
		} else {
			throw new Exception("Account already exists.");
		}
	} 
	
	@ApiOperation(value = "Retrieve a client, client log-in", 
			  notes = "The operation must be carried out by a client")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = Client.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PostMapping("/sign-in")
	public Client login(@RequestParam String email, @RequestParam("password") String pwd) throws Exception {
		String token = getJWTToken(email);
		
		Optional<Client> userOpt = clientService.findByEmail(email);
		
		if (userOpt.isPresent()) {
			String pwdHash = userOpt.get().getPassword();
			if (encoder.matches(pwd, pwdHash)) {
				Client client = userOpt.get();
				client.setToken(token);
				return clientService.save(client);
			} else {
				throw new Exception("Invalid password entered.");
			}
		} else {
			throw new Exception("Account not registered. Please, sign-up.");
		}
	}
		
	@ApiOperation(value = "Retrieve all client")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources obtained successfully", response = Client.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("")
	public Iterable<Client> all() {
		return clientService.findAll();
	}
	
	@ApiOperation(value = "Retrieve a client by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource obtained successfully", response = Client.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@GetMapping("/{id}")
	public Optional<Client> one(@PathVariable int id) {
		return clientService.findById(id);
	}

	@ApiOperation(value = "Update a client by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource updated successfully", response = Client.class),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@PutMapping("/{id}")
	public Client replaceClient(@PathVariable int id, @RequestBody Client newUser) {
		String token = getJWTToken(newUser.getEmail());
		
		return clientService.findById(id)
			.map(user -> {
				user.setFullName(newUser.getFullName());
				user.setEmail(newUser.getEmail());
				user.setPassword(encoder.encode(newUser.getPassword()));
				user.setBirthDate(newUser.getBirthDate());
				user.setGender(newUser.getGender());
				user.setAdress(newUser.getAdress());
				user.setToken(token);
;		        return clientService.save(user);
		      })
		      .orElseGet(() -> {
		    	newUser.setId(id);
		        return clientService.save(newUser);
		      });
	}
	
	@ApiOperation(value = "Delete all clients")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resources deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("")
	public void deleteAllClients() {
		clientService.deleteAll();
	}
	
	@ApiOperation(value = "Delete a client by searching it by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resource deleted successfully"),
			@ApiResponse(code = 500, message = "Something went wrong while processing request")
	})
	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable int id) {
		clientService.deleteById(id);
	}	
	
	private String getJWTToken(String email) {
		String secretKey = "myUltraSecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_CLIENT");
		
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
