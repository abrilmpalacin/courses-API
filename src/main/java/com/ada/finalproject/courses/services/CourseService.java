package com.ada.finalproject.courses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.Course;
import com.ada.finalproject.courses.repositories.CourseRepository;

@Service
public class CourseService {

	@Autowired
	CourseRepository courseRepository;

	public Course save(Course entity) {
		return courseRepository.save(entity);
	}
	
	public Iterable<Course> saveAll(Iterable<Course> entities) {
		return courseRepository.saveAll(entities);
	}
	
	public Optional<Course> findById(int id) {
		return courseRepository.findById(id);
	}
	
	public Optional<Course> findByCompanyAndName(String company, String name) {
		return courseRepository.findByCompanyAndName(company, name);
	}
	
	public boolean existsById(int id) {
		return courseRepository.existsById(id);
	}
	
	public Iterable<Course> findAll() {
		return courseRepository.findAll();
	}
	
	public Iterable<Course> findAllById(Iterable<Integer> ids) {
		return courseRepository.findAllById(ids);
	}
	
	public Iterable<Course> findAllByCategory(String category) {
		return courseRepository.findAllByCategory(category);
	}
	
	public Iterable<Course> findAllByCompany(String company) {
		return courseRepository.findAllByCompany(company);
	}
	
	public Iterable<Course> findAllByInProgress(boolean inProgress) {
		return courseRepository.findAllByInProgress(inProgress);
	}
	
	public long count() {
		return courseRepository.count();
	}
	
	public void deleteById(int id) {
		courseRepository.deleteById(id);
	}
	
	public void delete(Course entity) {
		courseRepository.delete(entity);
	}
	
	public void deleteAllById(Iterable<Integer> ids) {
		courseRepository.deleteAllById(ids);
	}
	
	public void deleteAll(Iterable<Course> entities) {
		courseRepository.deleteAll(entities);
	}
	
	public void deleteAll() {
		courseRepository.deleteAll();
	}
	
}
