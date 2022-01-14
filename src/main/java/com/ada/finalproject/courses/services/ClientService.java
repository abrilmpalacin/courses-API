package com.ada.finalproject.courses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.finalproject.courses.models.Client;
import com.ada.finalproject.courses.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	public Client save(Client entity) {
		return clientRepository.save(entity);
	}
	
	public Iterable<Client> saveAll(Iterable<Client> entities) {
		return clientRepository.saveAll(entities);
	}
	
	public Optional<Client> findById(int id) {
		return clientRepository.findById(id);
	}
	
	public Optional<Client> findByEmail(String email) {
		return clientRepository.findByEmail(email);
	}
	
	public boolean existsById(int id) {
		return clientRepository.existsById(id);
	}
	
	public Iterable<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Iterable<Client> findAllById(Iterable<Integer> ids) {
		return clientRepository.findAllById(ids);
	}
	
	public long count() {
		return clientRepository.count();
	}
	
	public void deleteById(int id) {
		clientRepository.deleteById(id);
	}
	
	public void delete(Client entity) {
		clientRepository.delete(entity);
	}
	
	public void deleteAllById(Iterable<Integer> ids) {
		clientRepository.deleteAllById(ids);
	}
	
	public void deleteAll(Iterable<Client> entities) {
		clientRepository.deleteAll(entities);
	}
	
	public void deleteAll() {
		clientRepository.deleteAll();
	}
	
}
