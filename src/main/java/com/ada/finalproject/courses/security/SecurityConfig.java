package com.ada.finalproject.courses.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ada.finalproject.courses.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
				.antMatchers ("/", 
							  "/csrf", 
							  "/v2/api-docs", 
							  "/swagger-resources/configuration/ui", 
							  "/configuration/ui", 
							  "/swagger-resources", 
							  "/swagger-resources/configuration/security", 
							  "/configuration/security", 
							  "/swagger-ui.html", 
							  "/webjars/**"). permitAll()
				.antMatchers("/api/v1/admin/sign-in", 
							 "/api/v1/clients/sign-up",
							 "/api/v1/clients/sign-in", 
							 "/api/v1/companies/sign-up",
							 "/api/v1/companies/sign-in").permitAll()
				.antMatchers("/api/v1/admin/**").hasRole("ADMIN")
				.antMatchers("/api/v1/courses/{([A-z])\\w+}/{([A-z])\\w+}").permitAll()
				.antMatchers("/api/v1/courses/**").hasAnyRole("ADMIN", "REPRESENTATIVE")
				.antMatchers("/api/v1/clients/**",
						     "/api/v1/companies/**",
						     "/api/v1/inscriptions/**", 
						     "/api/v1/representatives/**", 
						     "/api/v1/scholarships/**").hasRole("ADMIN")
			.and()
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
			.httpBasic();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(encoder());
    }
	
	@Bean
	public BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
}
