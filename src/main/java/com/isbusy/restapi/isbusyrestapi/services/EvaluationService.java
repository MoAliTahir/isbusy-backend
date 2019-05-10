package com.isbusy.restapi.isbusyrestapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;
import com.isbusy.restapi.isbusyrestapi.repositories.EvaluationRepository;

@Service
public class EvaluationService {

	
	@Autowired
	private EvaluationRepository evaluationRepository; 

	public List<Evaluation> getAllEvaluations(String topicId) {
		List<Evaluation>evaluations = new ArrayList<>();
		evaluationRepository.findByEmplacementId(topicId).forEach(evaluations::add);
		return evaluations;
	}

	
	public Evaluation getEvaluation(long id) {
		return evaluationRepository.findById(id).get();//we can use .orElse(Null) or .orElse(new Topic);
	}

	public void addEvaluation(Evaluation e) {
		evaluationRepository.save(e);
	}

	public void updateEvaluation(Evaluation c) {
		evaluationRepository.save(c);
	}

	public void deleteEvaluation(long id) {
		evaluationRepository.deleteById(id);
	}
}
