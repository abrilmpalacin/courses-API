package com.ada.finalproject.courses.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ada.finalproject.courses.models.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer> {

	Optional<Admin> findByEmail(String email);
	
	@Query(
			value = "SELECT * FROM admin;",
			nativeQuery = true)
	List<Admin> findAllActiveAdministrators();
	
}
