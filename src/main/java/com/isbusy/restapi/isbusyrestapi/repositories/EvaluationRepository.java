package com.isbusy.restapi.isbusyrestapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;

	public interface EvaluationRepository extends  CrudRepository<Evaluation,Long>{
		List<Evaluation> findByEmplacementId(long id);
}
