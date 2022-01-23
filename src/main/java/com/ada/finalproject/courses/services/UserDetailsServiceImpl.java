package com.ada.finalproject.courses.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.User;
import com.ada.finalproject.courses.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findByEmail(username);
		if (userOpt.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		User user = userOpt.get();
		String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(authority)));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}

}