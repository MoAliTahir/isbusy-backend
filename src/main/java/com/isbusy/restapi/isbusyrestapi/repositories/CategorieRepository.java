package com.isbusy.restapi.isbusyrestapi.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.isbusy.restapi.isbusyrestapi.entities.Categorie;

public interface CategorieRepository extends CrudRepository<Categorie, String> {
    boolean existsById(String id);

    boolean existsByName(String nomEmplacement);

    List<Categorie> findByName(String name);

    List<Categorie> findAll();

}
