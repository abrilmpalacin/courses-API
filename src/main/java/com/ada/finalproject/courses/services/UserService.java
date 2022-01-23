package com.ada.finalproject.courses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.User;
import com.ada.finalproject.courses.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public User save(User entity) {
		return userRepository.save(entity);
	}
	
	public Iterable<User> saveAll(Iterable<User> entities) {
		return userRepository.saveAll(entities);
	}
	
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public boolean existsById(int id) {
		return userRepository.existsById(id);
	}
	
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
	
	public Iterable<User> findAllById(Iterable<Integer> ids) {
		return userRepository.findAllById(ids);
	}
	
	public long count() {
		return userRepository.count();
	}
	
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}
	
	public void delete(User entity) {
		userRepository.delete(entity);
	}
	
	public void deleteAllById(Iterable<Integer> ids) {
		userRepository.deleteAllById(ids);
	}
	
	public void deleteAll(Iterable<User> entities) {
		userRepository.deleteAll(entities);
	}
	
	public void deleteAll() {
		userRepository.deleteAll();
	}

}
