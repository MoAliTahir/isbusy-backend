package com.isbusy.restapi.isbusyrestapi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;
import com.isbusy.restapi.isbusyrestapi.repositories.EmplacementRepository;
import com.isbusy.restapi.isbusyrestapi.repositories.EvaluationRepository;

@Service
public class EmplacementService {
	/**
	 * Evaluation Service
	 */
	@Autowired
	EvaluationService evaluationService;
	@Autowired
	private EvaluationRepository evaluationRepository;
	@Autowired
	private EmplacementRepository emplacementRepository;

	public List<Emplacement> getAllEmplacements() {
		List<Emplacement> emplacements = new ArrayList<>();
		emplacementRepository.findAll().forEach(emplacements::add);
		return emplacements;
	}

	public Emplacement getEmplacement(String id) {
		return emplacementRepository.findById(id).get();
	}

	public void addEmplacement(Emplacement e) {
		emplacementRepository.save(e);
	}

	// check
	public void deleteEmplacement(String id) {
		emplacementRepository.deleteById(id);
	}

	// check
	public void updateEmplacement(String id, Emplacement e) {
		emplacementRepository.save(e);
	}

	// Check if Emplacement exists By ID
	public boolean emplacementExists(String id) {
		return emplacementRepository.existsById(id);
	}

	// Check if Emplacement exists byName
	public boolean emplacementExistsByName(String name) {
		return emplacementRepository.existsByNomEmplacement(name);
	}

	// Get Emplacement By Name
	public ArrayList<Emplacement> getEmplacementsByName(String name) {
		ArrayList<Emplacement> emplacements = new ArrayList<>();
		emplacements.addAll(emplacementRepository.findAllByNomEmplacement(name));
		return emplacements;
	}

	// Get Emplacement by Categorie
	public ArrayList<Emplacement> getEmplacementsByCategorie(String categorieId) {
		ArrayList<Emplacement> emplacements = new ArrayList<>();
		emplacements.addAll(emplacementRepository.findAllByCategorie(categorieId));
		return emplacements;
	}

	// Get Inactive Emplacements
	public ArrayList<Emplacement> getInactiveEmplacements() {
		ArrayList<Emplacement> places = new ArrayList<Emplacement>();
		places.addAll(emplacementRepository.findAllByStatus(Emplacement.PENDING_STATUS));
		return places;
	}

	// Approve pending Emplacement (Status from 0 to 2)
	public boolean approveEmplacement(String id) {
		Emplacement emplacement = getEmplacement(id);
		if (emplacement.getStatus() != Emplacement.PENDING_STATUS)
			return false;
		emplacement.setStatus(Emplacement.APPROVED_STATUS);
		emplacementRepository.save(emplacement);
		return true;
	}

	// Ignore Emplacement
	public boolean ignoreEmplacement(String id) {
		Emplacement emplacement = getEmplacement(id);
		if (emplacement.getStatus() != Emplacement.PENDING_STATUS)
			return false;
		emplacement.setStatus(Emplacement.IGNORED_STATUS);
		emplacementRepository.save(emplacement);
		return true;
	}

	// Get Emplacement Stat
	public HashMap<Integer, Integer> getEmplacementStat(String id, String jour) {
		List<Evaluation> evals = evaluationService.findAllByIdEmPlacementAndJour(id, jour);
		// HashMap to be returned
		HashMap<Integer, Integer> stats = new HashMap<Integer, Integer>();
		for (int i = 0; i < 24; i++) {
			stats.put(i, weight(id, jour, i));// Init all elements with 0
		}
		return stats;
	}

	public double computeSpecificWeight(double w, String id, String jour, int heure, int vote) {
		int countEvals = evaluationRepository.getCountByJourAndHeure(id, jour, heure);
		int totalEvalsByVote = evaluationRepository.getCountByJourAndHeureAndVote(id, jour, heure, vote);
		return w * totalEvalsByVote / countEvals;
	}

	public int weight(String id, String jour, int heure) {
		double[] weights = new double[4];
		weights[0] = computeSpecificWeight(Evaluation.WEIGHT_VIDE, id, jour, heure, 0);
		weights[1] = computeSpecificWeight(Evaluation.WEIGHT_MOYEN, id, jour, heure, 1);
		weights[2] = computeSpecificWeight(Evaluation.WEIGHT_PLEIN, id, jour, heure, 2);
		weights[3] = computeSpecificWeight(Evaluation.WEIGHT_FERME, id, jour, heure, 3);
		double max = 0;
		int index = 0;
		for (int i = 0; i < weights.length; i++) {
			if (weights[i] > max) {
				max = weights[i];
				index = i;
			}
		}
		return index;
	}

}