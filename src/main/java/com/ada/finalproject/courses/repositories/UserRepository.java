package com.ada.finalproject.courses.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ada.finalproject.courses.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByEmail(String email);
	
}
