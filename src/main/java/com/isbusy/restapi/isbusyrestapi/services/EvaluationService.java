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

	public ArrayList<Evaluation> getAllEvaluations(String id) {
		ArrayList<Evaluation> evaluations = new ArrayList<>();
		evaluations.addAll(evaluationRepository.findByEmplacementId(id));
		return evaluations;
	}

	public Evaluation getEvaluation(long id) {
		return evaluationRepository.findById(id).get();
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
