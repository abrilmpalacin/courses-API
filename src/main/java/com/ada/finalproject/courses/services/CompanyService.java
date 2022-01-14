package com.ada.finalproject.courses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.Company;
import com.ada.finalproject.courses.repositories.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	public Company save(Company entity) {
		return companyRepository.save(entity);
	}
	
	public Iterable<Company> saveAll(Iterable<Company> entities) {
		return companyRepository.saveAll(entities);
	}
	
	public Optional<Company> findById(int id) {
		return companyRepository.findById(id);
	}
	
	public boolean existsById(int id) {
		return companyRepository.existsById(id);
	}
	
	public Iterable<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Iterable<Company> findAllById(Iterable<Integer> ids) {
		return companyRepository.findAllById(ids);
	}
	
	public long count() {
		return companyRepository.count();
	}
	
	public void deleteById(int id) {
		companyRepository.deleteById(id);
	}
	
	public void delete(Company entity) {
		companyRepository.delete(entity);
	}
	
	public void deleteAllById(Iterable<Integer> ids) {
		companyRepository.deleteAllById(ids);
	}
	
	public void deleteAll(Iterable<Company> entities) {
		companyRepository.deleteAll(entities);
	}
	
	public void deleteAll() {
		companyRepository.deleteAll();
	}
	
}
