package com.ada.finalproject.courses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.Inscription;
import com.ada.finalproject.courses.repositories.InscriptionRepository;

@Service
public class InscriptionService {

	@Autowired
	InscriptionRepository inscriptionRepository;

	public Inscription save(Inscription entity) {
		return inscriptionRepository.save(entity);
	}
	
	public Iterable<Inscription> saveAll(Iterable<Inscription> entities) {
		return inscriptionRepository.saveAll(entities);
	}
	
	public Optional<Inscription> findById(int id) {
		return inscriptionRepository.findById(id);
	}
	
	public boolean existsById(int id) {
		return inscriptionRepository.existsById(id);
	}
	
	public Iterable<Inscription> findAll() {
		return inscriptionRepository.findAll();
	}
	
	public Iterable<Inscription> findAllById(Iterable<Integer> ids) {
		return inscriptionRepository.findAllById(ids);
	}

	public long count() {
		return inscriptionRepository.count();
	}
	
	public void deleteById(int id) {
		inscriptionRepository.deleteById(id);
	}
	
	public void delete(Inscription entity) {
		inscriptionRepository.delete(entity);
	}
	
	public void deleteAllById(Iterable<Integer> ids) {
		inscriptionRepository.deleteAllById(ids);
	}
	
	public void deleteAll(Iterable<Inscription> entities) {
		inscriptionRepository.deleteAll(entities);
	}
	
	public void deleteAll() {
		inscriptionRepository.deleteAll();
	}
	
}

