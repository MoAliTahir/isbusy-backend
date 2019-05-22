package com.isbusy.restapi.isbusyrestapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.isbusy.restapi.isbusyrestapi.entities.Categorie;

public interface CategorieRepository extends CrudRepository<Categorie, String> {
    boolean existsById(String id);

    boolean existsByName(String name);

    Optional<Categorie> findById(String id);

    Optional<Categorie> findByName(String name);
}
