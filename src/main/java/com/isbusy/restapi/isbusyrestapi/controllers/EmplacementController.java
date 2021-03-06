package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.isbusy.restapi.isbusyrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.responses.EmplacementResponse;
import com.isbusy.restapi.isbusyrestapi.responses.EvaluationResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;
import com.isbusy.restapi.isbusyrestapi.entities.User;
import com.isbusy.restapi.isbusyrestapi.controllers.EvaluationController;
import com.isbusy.restapi.isbusyrestapi.Classes.GenericEmplacement;
import com.isbusy.restapi.isbusyrestapi.Foursquare.Request;
import com.isbusy.restapi.isbusyrestapi.JSON.JSONException;
import com.isbusy.restapi.isbusyrestapi.services.EmplacementService;
import com.isbusy.restapi.isbusyrestapi.services.EvaluationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class EmplacementController {
	/**
	 * TODO 1. Add ResponseEntity Method to this Controller 2. Configure Methods to
	 * return Response Entity
	 */

	@Autowired
	private UserService userService;

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
	 * Get Emplacement by ID from DB
	 * 
	 * @return Emplacement
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/{id}")
	public ResponseEntity<EmplacementResponse> getEmplacementById(@PathVariable String id) {
		if (emplacementService.emplacementExists(id))
			return handleResponse(emplacementService.getEmplacement(id), null, "Emplacement trouve !", HttpStatus.OK);
		return handleResponse(null, null, "Oops. Cet emplacement n'existe pas.", HttpStatus.NOT_FOUND);
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
	public ResponseEntity<EmplacementResponse> getEmplacementByName(@PathVariable String name,
			@PathVariable String radius, @PathVariable String position) {

		if (emplacementService.emplacementExistsByName(name)) {
			return handleResponse(null, emplacementService.getEmplacementsByName(name),
					"Emplacements trouves par nom: " + name + " et position: " + position + ".", HttpStatus.OK);
		}
		return handleResponse(null, handleAPICall("query", name, radius, position),
				"Emplacements trouves par nom: " + name + " et position: " + position + ".", HttpStatus.OK);
	}

	/**
	 * Get Emplacement by Categorie Id
	 * 
	 * @param
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/categorie/{categorieId}/{radius}/{position}")
	public ResponseEntity<EmplacementResponse> getEmplacementsByCategorie(@PathVariable String categorieId,
			@PathVariable String radius, @PathVariable String position) {
		return handleResponse(null, handleAPICall("category", categorieId, radius, position),
				"Emplacements trouves avec succes.", HttpStatus.OK);
	}

	/**
	 * Get all Emplacements from DB
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/near/{position}")
	public ResponseEntity<EmplacementResponse> getAllEmplacements(@PathVariable String position) {
		String[] tab = position.split(",");
		double latitude = Double.valueOf(tab[0]);
		double longitude = Double.valueOf(tab[1]);
		ArrayList<Emplacement> emplacements = new ArrayList<>();
		emplacements.addAll(emplacementService.getAllEmplacementsByLatitudeAndLongitude(latitude, longitude));
		return handleResponse(null, emplacements, "Liste de tous les emplacements", HttpStatus.OK);
	}

	/**
	 * Add new Emplacement
	 * 
	 * @param Emplacement
	 * @return Emplacement
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/add")
	public ResponseEntity<EmplacementResponse> addEmplacement(@RequestBody Emplacement emplacement) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();

		Random val = new Random();
		emplacement.setId(String.valueOf(val.nextLong()));
		ArrayList<User> users = new ArrayList<>();
		users.add(userService.getUser(currentUser.getId()));
		emplacement.setUsers(users);
		emplacementService.addEmplacement(emplacement);
		return handleResponse(emplacement, null,
				"Emplacement: " + emplacement.getNomEmplacement() + " ajoute avec succes !", HttpStatus.OK);
	}

	/**
	 * Get all Evaluations for an Emplacement
	 * 
	 * @param String Emplacement Id
	 * @return ArrayList<Evaluation> a table of Evaluations
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/{id}/evaluations")
	public ResponseEntity<EvaluationResponse> getEmplacementEvaluations(@PathVariable String id) {
		if (!emplacementService.emplacementExists(id))
			return EvaluationController.handleResponse(null, null,
					"Oops, cet emplacement n'existe pas. Veuillez verifier les champs inseres.", HttpStatus.NOT_FOUND);
		return EvaluationController.handleResponse(null, evaluationService.getAllEvaluations(id),
				"Evaluations de l'emplacement " + id, HttpStatus.OK);
	}

	/**
	 * Evaluate an Emplacement with its ID
	 * 
	 * @param String Emplacement id
	 * @return Evaluation
	 * 
	 * @param evaluation
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/{id}/evaluer")
	public ResponseEntity<EvaluationResponse> evaluateEmplacement(@RequestBody Evaluation evaluation,
			@PathVariable String id, HttpServletRequest request) {
		if (!emplacementService.emplacementExists(id))
			return EvaluationController.handleResponse(null, null,
					"Oops, cet emplacement n'existe pas. Veuillez verifier les champs inseres.", HttpStatus.NOT_FOUND);

		Emplacement emplacement = emplacementService.getEmplacement(id);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		evaluation.setIdUser(user.getId());
		evaluation.setEmplacement(emplacement);
		evaluationService.addEvaluation(evaluation);
		emplacement.setVote(evaluation.getVote());
		emplacementService.updateEmplacement(emplacement.getId(), emplacement);
		return EvaluationController.handleResponse(evaluation, evaluationService.getAllEvaluations(id),
				"Votre evaluation a ete ajoutee avec succes !", HttpStatus.OK);
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

			System.out.println("API SIZE====>"+placesFromAPI.size());

			for (int i = 0; i < placesFromAPI.size(); i++) {

				if (emplacementService.emplacementExists(placesFromAPI.get(i).getId())) {
					continue;
				}

				emplacementService.addEmplacement(new Emplacement(placesFromAPI.get(i)));
				emplacements.add(new Emplacement(placesFromAPI.get(i)));
			}
			System.out.println("======================> "+emplacements.size());
			if (emplacements.size() == 0)
			{
				for (int i=0; i<placesFromAPI.size(); i++){
					emplacements.add(emplacementService.getEmplacement(placesFromAPI.get(i).getId()));
				}
			}
			return emplacements;
		} catch (JSONException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Get all pending Emplacement Requires Admin Role Requires Admin Role
	 * 
	 * @return ArrayList<Emplacement>
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/pending")
	public ResponseEntity<EmplacementResponse> getInactiveEmplacements() {
		return handleResponse(null, emplacementService.getInactiveEmplacements(),
				"Liste des emplacement en attente de confirmation", HttpStatus.OK);
	}

	/**
	 * Approve a Pending Emplacement i.e Suggest it to Foursquare API (Switch status
	 * from 0 to 2) Requires Admin Role
	 * 
	 * @param String Emplacement id to approve
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/{id}/approve")
	public ResponseEntity<EmplacementResponse> approvePendingEmplacement(@PathVariable String id) {
		if (!emplacementService.emplacementExists(id))
			return handleResponse(null, null, "Oops. Cet emplacement n'existe pas.", HttpStatus.NOT_FOUND);
		boolean updated = emplacementService.approveEmplacement(id);
		if (!updated)
			return handleResponse(null, null, "Oops. Il y a eu un probleme. Veuillez reessayer plus tard.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		return handleResponse(emplacementService.getEmplacement(id), null, "L'emplacement: " + id + " a ete confirme !",
				HttpStatus.OK);
	}

	/**
	 * Ignore an Emplacement (Switch status from 0 to 1) Requires Admin Role
	 * 
	 * @param String Emplacement id to approve
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/{id}/ignore")
	public ResponseEntity<EmplacementResponse> ignorePendingEmplacement(@PathVariable String id) {
		if (!emplacementService.emplacementExists(id))
			return handleResponse(null, null, "Oops. Cet emplacement n'existe pas.", HttpStatus.NOT_FOUND);
		boolean ignored = emplacementService.ignoreEmplacement(id);
		if (!ignored)
			return handleResponse(null, null, "Oops. Erreur du systeme. Veillez reessayer plus tard.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		return handleResponse(emplacementService.getEmplacement(id), null, "L'emplacement: " + id + " a ete ignore !",
				HttpStatus.OK);
	}

	/**
	 * Get An Emplacement statistic in a specific day (All computations are done in
	 * emplacement service)
	 * 
	 * @param String Emplacement id
	 * @param String day
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/{id}/{jour}")
	public ResponseEntity<EmplacementResponse> getEmplacementStat(@PathVariable String id, @PathVariable String jour) {
		if (!emplacementService.emplacementExists(id))
			return handleResponse(null, null, "Oops. Cet emplacement n'existe pas.", HttpStatus.NOT_FOUND);
		Emplacement emplacement = emplacementService.getEmplacement(id);
		return handleStatResponse(emplacement, emplacementService.getEmplacementStat(id, jour),
				"Statistiques de: " + emplacement.getId() + " du jour " + jour + ".", HttpStatus.OK);
	}


	// Favoris
	@RequestMapping(method = RequestMethod.GET, value = "/favories")
	public ResponseEntity<EmplacementResponse> getAllFavoris() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		long currentId = currentUser.getId();
		return handleResponse(null, userService.getAllFavoris(currentId),"Liste des favories de "+currentUser.getUsername(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/favories/add/{id}")
	public ResponseEntity<EmplacementResponse> addFavoris(@PathVariable String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		long currentId = currentUser.getId();
		userService.addFavoris(id, currentId);
		return handleResponse(emplacementService.getEmplacement(id),null, "Emplacement ajouté aux favories avec succes", HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/favories/delete/{id}")
	public ResponseEntity<EmplacementResponse> deleteFavoris(@PathVariable String id) {
		userService.deleteFavoris(id);
		return handleResponse(null, null, "Emplacement supprimé de votre liste de favories ", HttpStatus.OK);
	}





	/**
	 * Response Entity used to handle HTTP response headers and body when
	 * emplacement or list of emplacements are requested
	 * 
	 * @param Emplacement            to show
	 * @param ArrayList<Emplacement> list of emplacement if the user requests more
	 *                               than one
	 * @param String                 response body message
	 * @param int                    response status code
	 * @return ResponseEntoty<EmplacementResponse> the emplacement resource template
	 */
	public static ResponseEntity<EmplacementResponse> handleResponse(Emplacement e, ArrayList<Emplacement> emplacements,
			String message, HttpStatus statusCode) {
		HttpHeaders headers = new HttpHeaders();
		int status = statusCode.value();
		headers.add("status", String.valueOf(statusCode));
		headers.add("message", message);
		return ResponseEntity.status(status).headers(headers)
				.body(new EmplacementResponse(e, emplacements, message, status));
	}

	/**
	 * Response Entity used to handle HTTP response headers and body when statistics
	 * are requested
	 * 
	 * @param Emplacement      to show
	 * @param HashMap<Integer, Integer> keys: hours, values : weight (Ferme, Plein,
	 *                         Moyen, Vide)
	 * @param String           response body message
	 * @param int              response status code
	 * @return ResponseEntoty<EmplacementResponse> the emplacement resource template
	 */
	public static ResponseEntity<EmplacementResponse> handleStatResponse(Emplacement e, HashMap<Integer, Integer> stats,
			String message, HttpStatus statusCode) {
		HttpHeaders headers = new HttpHeaders();
		int status = statusCode.value();
		headers.add("status", String.valueOf(statusCode));
		headers.add("message", message);
		return ResponseEntity.status(status).headers(headers).body(new EmplacementResponse(e, stats, message, status));
	}
}
