package com.isbusy.restapi.isbusyrestapi.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;

public interface EmplacementRepository extends CrudRepository<Emplacement, String> {
    boolean existsByNomEmplacement(String nomEmplacement);

    List<Emplacement> findAllByNomEmplacement(String nomEmplacement);

    List<Emplacement> findAllByCategorie(String categorie);

}
