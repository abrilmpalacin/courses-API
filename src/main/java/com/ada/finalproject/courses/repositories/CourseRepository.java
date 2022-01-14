package com.ada.finalproject.courses.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ada.finalproject.courses.models.Course;

public interface CourseRepository extends CrudRepository<Course, Integer> {

	@Query(
			value = "SELECT * FROM courses c WHERE FK_company_id = (SELECT id FROM companies WHERE name = :company) AND name = :name",
			nativeQuery = true)
	Optional<Course> findByCompanyAndName(String company, String name);

	Iterable<Course> findAllByCategory(String category);

	@Query(
			value = "SELECT * FROM courses c WHERE c.FK_company_id = (SELECT id FROM companies comp WHERE comp.name = :company);",
			nativeQuery = true)
	Iterable<Course> findAllByCompany(String company);

	Iterable<Course> findAllByInProgress(boolean inProgress);

}
