package com.isbusy.restapi.isbusyrestapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;

public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {
	List<Evaluation> findByEmplacementId(String id);

	// Get Emplacement Evaluation with day
	@Query(value = "SELECT * FROM evaluation WHERE emplacement_id = ?1 and jour = ?2 and heure = ?3", nativeQuery = true)
	List<Evaluation> findAllByEmplacementIdAndDayAndHour(String emplacementId, String jour, int heure);

	@Query(value = "SELECT count(vote) FROM evaluation where emplacement_id=?1 and jour=?2 and heure=?3", nativeQuery = true)
	int getCountByJourAndHeure(String id, String jour, int heure);

	@Query(value = "SELECT count(vote) FROM evaluation where emplacement_id=?1 and jour=?2 and heure=?3 and vote=?4", nativeQuery = true)
	int getCountByJourAndHeureAndVote(String id, String jour, int heure, int vote);

	// Get Emplacement Evaluation with day
	@Query(value = "SELECT * FROM evaluation WHERE emplacement_id = ?1 and jour = ?2", nativeQuery = true)
	List<Evaluation> findAllByIdEmPlacementAndJour(String emplecementId, String jour);

}
