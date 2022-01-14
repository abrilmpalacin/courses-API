package com.ada.finalproject.courses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.Admin;
import com.ada.finalproject.courses.repositories.AdminRepository;

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;
	
	public Admin save(Admin entity) {
		return adminRepository.save(entity);
	}
	
	public Iterable<Admin> saveAll(Iterable<Admin> entities) {
		return adminRepository.saveAll(entities);
	}
	
	public Optional<Admin> findById(int id) {
		return adminRepository.findById(id);
	}
	
	public boolean existsById(int id) {
		return adminRepository.existsById(id);
	}
	
	public Iterable<Admin> findAll() {
		return adminRepository.findAll();
	}
	
	public Iterable<Admin> findAllById(Iterable<Integer> ids) {
		return adminRepository.findAllById(ids);
	}
	
	public long count() {
		return adminRepository.count();
	}
	
	public void deleteById(int id) {
		adminRepository.deleteById(id);
	}
	
	public void delete(Admin entity) {
		adminRepository.delete(entity);
	}
	
	public void deleteAllById(Iterable<Integer> ids) {
		adminRepository.deleteAllById(ids);
	}
	
	public void deleteAll(Iterable<Admin> entities) {
		adminRepository.deleteAll(entities);
	}
	
	public void deleteAll() {
		adminRepository.deleteAll();
	}
	
	public Optional<Admin> findByEmail(String email) {
		return adminRepository.findByEmail(email);
	}
	
}
