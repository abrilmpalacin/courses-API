package com.ada.finalproject.courses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.Representative;
import com.ada.finalproject.courses.repositories.RepresentativeRepository;

@Service
public class RepresentativeService {

	@Autowired
	RepresentativeRepository representativeRepository;
	
	public Representative save(Representative entity) {
		return representativeRepository.save(entity);
	}
	
	public Iterable<Representative> saveAll(Iterable<Representative> entities) {
		return representativeRepository.saveAll(entities);
	}
	
	public Optional<Representative> findById(int id) {
		return representativeRepository.findById(id);
	}
	
	public boolean existsById(int id) {
		return representativeRepository.existsById(id);
	}
	
	public Iterable<Representative> findAll() {
		return representativeRepository.findAll();
	}
	
	public Iterable<Representative> findAllById(Iterable<Integer> ids) {
		return representativeRepository.findAllById(ids);
	}
	
	public long count() {
		return representativeRepository.count();
	}
	
	public void deleteById(int id) {
		representativeRepository.deleteById(id);
	}
	
	public void delete(Representative entity) {
		representativeRepository.delete(entity);
	}
	
	public void deleteAllById(Iterable<Integer> ids) {
		representativeRepository.deleteAllById(ids);
	}
	
	public void deleteAll(Iterable<Representative> entities) {
		representativeRepository.deleteAll(entities);
	}
	
	public void deleteAll() {
		representativeRepository.deleteAll();
	}
	
	public Optional<Representative> findByEmail(String email) {
		return representativeRepository.findByEmail(email);
	}
	
}
