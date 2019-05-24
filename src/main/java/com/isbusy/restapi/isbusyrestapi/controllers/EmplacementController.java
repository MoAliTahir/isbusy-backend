package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;
import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.Classes.GenericEmplacement;
import com.isbusy.restapi.isbusyrestapi.Foursquare.Request;
import com.isbusy.restapi.isbusyrestapi.JSON.JSONException;
import com.isbusy.restapi.isbusyrestapi.services.EmplacementService;
import com.isbusy.restapi.isbusyrestapi.services.EvaluationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class EmplacementController {
	/**
	 * TODO 1. Add ResponseEntity Method to this Controller 2. Configure Methods to
	 * return Response Entity
	 */

	/**
	 * @var EvaluationService Singleton
	 */
	@Autowired
	private EvaluationService evaluationService;

	/**
	 * @var EmplacementService Singleton
	 */
	@Autowired
	private EmplacementService emplacementService;

	/**
	 * Get Emplacement by ID from DB TODO : Return 404 if not found (With Response
	 * Entity)
	 * 
	 * @return Emplacement
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/{id}")
	public Emplacement getEmplacementById(@PathVariable String id) {
		if (emplacementService.emplacementExists(id))
			return emplacementService.getEmplacement(id);
		return null;// TODO : This should be a ResponseEntity with 404 status code
	}

	/**
	 * Get emplacements by Emplacement Name if more than one if found
	 * 
	 * @param String Emplacement Name
	 * @param String radius
	 * @param String Position (lat,lon)
	 * @return ArrayList<Emplacement>
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/query/{name}/{radius}/{position}")
	public ArrayList<Emplacement> getEmplacementByName(@PathVariable String name, @PathVariable String radius,
			@PathVariable String position) {

		if (emplacementService.emplacementExistsByName(name)) {
			// Emplacements already found in DB, so just return them
			return emplacementService.getEmplacementsByName(name);
		}
		return handleAPICall("query", name, radius, position);

	}

	/**
	 * Get all Emplacements from DB TODO : Add Response Entity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/")
	public List<Emplacement> getAllEmplacements() {
		return emplacementService.getAllEmplacements();
	}

	/**
	 * Add new Emplacement TODO : Add ResponseEntity
	 * 
	 * @param Emplacement
	 * @return Emplacement
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/add")
	public Emplacement addEmplacement(@RequestBody Emplacement emplacement) {
		emplacementService.addEmplacement(emplacement);
		return emplacement;
	}

	/**
	 * Get all Evaluations for an Emplacement TODO : Implement ResponseEntity
	 * 
	 * @param String Emplacement Id
	 * @return ArrayList<Evaluation> a table of Evaluations
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/{id}/evaluations")
	public ArrayList<Evaluation> getEmplacementEvaluations(@PathVariable String id) {
		if (!emplacementService.emplacementExists(id))
			return new ArrayList<Evaluation>();
		return evaluationService.getAllEvaluations(id);
	}

	/**
	 * Evaluate an Emplacement with its ID
	 * 
	 * @param String Emplacement id
	 * @return Evaluation TODO : Change this to ResponseEntity
	 * 
	 * @param evaluation
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/{id}/evaluer")
	public Evaluation evaluateEmplacement(@RequestBody Evaluation evaluation, @PathVariable String id,
			HttpServletRequest request) {
		if (!emplacementService.emplacementExists(id))
			return new Evaluation(); // TODO : Should be a 404 with ResponseEntity

		Emplacement emplacement = emplacementService.getEmplacement(id);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		evaluation.setIdUser(user.getId());
		evaluation.setEmplacement(emplacement);
		evaluationService.addEvaluation(evaluation);
		return evaluation;// TODO : Should be a 404 with ResponseEntity
	}

	/**
	 * Get Emplacement by Categorie Id
	 * 
	 * @param
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/categorie/{categorieId}/{radius}/{position}")
	public ArrayList<Emplacement> getEmplacementsByCategorie(@PathVariable String categorieId,
			@PathVariable String radius, @PathVariable String position) {
		if (emplacementService.emplacementExistsByName(categorieId)) {
			return emplacementService.getEmplacementsByCategorie(categorieId);
		}
		return handleAPICall("category", categorieId, radius, position);
	}

	/**
	 * Handle API Call to Get Emplacement from either "query" or "category"
	 * 
	 * @param String keyword (query or categorie Id)
	 * @return ArrayList<Emplacement> an array of Emplacements based on Categorie
	 */
	public ArrayList<Emplacement> handleAPICall(String type, String keyword, String radius, String position) {
		Request request = new Request();
		try {
			ArrayList<GenericEmplacement> placesFromAPI = request.getNearbyGenericEmplacements(type, keyword, radius,
					position);
			ArrayList<Emplacement> emplacements = new ArrayList<>(); // Result Emplacement ArrayList

			for (int i = 0; i < placesFromAPI.size(); i++) {
				emplacements.add(new Emplacement(placesFromAPI.get(i)));
				emplacementService.addEmplacement(emplacements.get(i));
			}

			return emplacements;
		} catch (JSONException e) {
			return null;// TODO : return response entity with status code of 500
		} catch (IOException e) {
			return null;// TODO : return response entity with status code of 500
		}
	}

	/**
	 * Get all inactive Emplacement Requires Admin Role TODO : Add Response Entity
	 * Requires Admin Role
	 * 
	 * @return ArrayList<Emplacement>
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/pending")
	public ArrayList<Emplacement> getInactiveEmplacements() {
		return emplacementService.getInactiveEmplacements();
	}

	/**
	 * Approve a Pending Emplacement i.e Suggest it to Foursquare API (Switch status
	 * from 0 to 2) Requires Admin Role
	 * 
	 * @param String Emplacement id to approve
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.PATCH, value = "/emplacements/{id}/approve")
	public Emplacement approvePendingEmplacement(@PathVariable String id) {
		if (!emplacementService.emplacementExists(id))
			return new Emplacement();// TODO : Return Response Entity with not 404 found message
		boolean updated = emplacementService.approveEmplacement(id);
		if (!updated)
			return new Emplacement();// TODO: Return 500 Internal server error(Can also be 400 Bad request)
		return emplacementService.getEmplacement(id);// TODO return Response entity with 200 Status code
	}

	/**
	 * Ignore an Emplacement (Switch status from 0 to 1) Requires Admin Role
	 * 
	 * @param String Emplacement id to approve
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.PATCH, value = "/emplacements/{id}/ignore")
	public Emplacement ignorePendingEmplacement(@PathVariable String id) {
		if (!emplacementService.emplacementExists(id))
			return new Emplacement();// TODO : Return Response Entity with not 404 found message
		boolean ignored = emplacementService.ignoreEmplacement(id);
		if (!ignored)
			return new Emplacement();// TODO: Return 500 Internal server error(Can also be 400 Bad request)
		return emplacementService.getEmplacement(id);// Todo return Respons entity with 200 Status code
	}

	/**
	 * Get An Emplacement statistic in a specific day (All computations are done in
	 * emplacement service)
	 * 
	 * @param String Emplacement id
	 * @param String day
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/{id}/{jour}")
	public HashMap<Integer, Integer> getEmplacementStat(@PathVariable String id, @PathVariable String jour) {
		if (!emplacementService.emplacementExists(id))
			return null; // TODO : Response Entity 404
		return emplacementService.getEmplacementStat(id, jour);
	}
}
