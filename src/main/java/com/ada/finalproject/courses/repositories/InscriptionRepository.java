package com.ada.finalproject.courses.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ada.finalproject.courses.models.Inscription;

public interface InscriptionRepository extends CrudRepository<Inscription, Integer> {
	
}
