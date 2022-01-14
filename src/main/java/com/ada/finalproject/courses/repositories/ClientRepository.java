package com.ada.finalproject.courses.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ada.finalproject.courses.models.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {

	Optional<Client> findByEmail(String email);

}
