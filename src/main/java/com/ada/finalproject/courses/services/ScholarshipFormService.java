package com.ada.finalproject.courses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.ScholarshipForm;
import com.ada.finalproject.courses.repositories.ScholarshipFormRepository;

@Service
public class ScholarshipFormService {

	@Autowired
	ScholarshipFormRepository scholarshipFormRepository;

	public ScholarshipForm save(ScholarshipForm entity) {
		return scholarshipFormRepository.save(entity);
	}
	
	public Iterable<ScholarshipForm> saveAll(Iterable<ScholarshipForm> entities) {
		return scholarshipFormRepository.saveAll(entities);
	}
	
	public Optional<ScholarshipForm> findById(int id) {
		return scholarshipFormRepository.findById(id);
	}
	
	public boolean existsById(int id) {
		return scholarshipFormRepository.existsById(id);
	}
	
	public Iterable<ScholarshipForm> findAll() {
		return scholarshipFormRepository.findAll();
	}
	
	public Iterable<ScholarshipForm> findAllById(Iterable<Integer> ids) {
		return scholarshipFormRepository.findAllById(ids);
	}
	
	public long count() {
		return scholarshipFormRepository.count();
	}
	
	public void deleteById(int id) {
		scholarshipFormRepository.deleteById(id);
	}
	
	public void delete(ScholarshipForm entity) {
		scholarshipFormRepository.delete(entity);
	}
	
	public void deleteAllById(Iterable<Integer> ids) {
		scholarshipFormRepository.deleteAllById(ids);
	}
	
	public void deleteAll(Iterable<ScholarshipForm> entities) {
		scholarshipFormRepository.deleteAll(entities);
	}
	
	public void deleteAll() {
		scholarshipFormRepository.deleteAll();
	}
	
}
