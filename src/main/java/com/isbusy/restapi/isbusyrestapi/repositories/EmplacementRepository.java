package com.isbusy.restapi.isbusyrestapi.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;

public interface EmplacementRepository extends CrudRepository<Emplacement, String> {
    boolean existsByNomEmplacement(String nomEmplacement);

    List<Emplacement> findAllByNomEmplacement(String nomEmplacement);

    List<Emplacement> findAllByCategorie(String categorie);

    List<Emplacement> findAllByStatus(int status);

    @Query(value = "SELECT * FROM emplacement e INNER JOIN favoris f ON f.emplacement_id = e.id where f.user_id= ?1", nativeQuery = true)
    List<Emplacement> findFavoris(long id);
}
