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

	// Get Evaluations by Emplacement Id and Jour
	public List<Evaluation> findAllByIdEmPlacementAndJour(String emplecementId, String jour) {
		List<Evaluation> evals = evaluationRepository.findAllByIdEmPlacementAndJour(emplecementId, jour);
		return evals;
	}

	// Get Evaluations by Emplacement Id, heure and jour
	public List<Evaluation> findAllByEmplacementIdAndDayAndHour(String emplacementId, String jour, int heure) {
		return evaluationRepository.findAllByEmplacementIdAndDayAndHour(emplacementId, jour, heure);
	}

	// Get Evaluations by Emplacement Id
	public List<Evaluation> findAllByEmplacementId(String emplacementId) {
		return evaluationRepository.findByEmplacementId(emplacementId);
	}
}
