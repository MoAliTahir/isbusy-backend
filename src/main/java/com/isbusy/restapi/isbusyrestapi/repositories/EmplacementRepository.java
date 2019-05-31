package com.isbusy.restapi.isbusyrestapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;

public interface EmplacementRepository extends CrudRepository<Emplacement, String> {
    boolean existsByNomEmplacement(String nomEmplacement);

    List<Emplacement> findAllByNomEmplacement(String nomEmplacement);

    List<Emplacement> findAllByCategorie(String categorie);

    List<Emplacement> findAllByStatus(int status);

    @Query(value = "SELECT * FROM emplacement e INNER JOIN favoris f ON f.emplacement_id = e.id where f.user_id= ?1", nativeQuery = true)
    List<Emplacement> findFavoris(long id);

    @Modifying
    @Transactional
    @Query(value = "insert into favoris (emplacement_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
    void addFavoris(String emplacementId, long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from favoris  where emplacement_id = ?1", nativeQuery = true)
    void deleteFavorisByEmplacementId(String emplacementId);


    @Query(value = "SELECT * FROM emplacement WHERE (latitude BETWEEN ?1 - 0.01 and ?1 + 0.01) AND (longitude BETWEEN ?2 -0.01 AND ?2 +0.01)", nativeQuery = true)
    List<Emplacement> findAllByLatitudeAndLongitude(double latitude, double longitude);
}
