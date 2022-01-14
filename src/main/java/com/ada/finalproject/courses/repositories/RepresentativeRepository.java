package com.ada.finalproject.courses.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ada.finalproject.courses.models.Representative;

public interface RepresentativeRepository extends CrudRepository<Representative, Integer> {

	Optional<Representative> findByEmail(String email);

}
