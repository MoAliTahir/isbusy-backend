package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;
import com.isbusy.restapi.isbusyrestapi.services.EvaluationService;
import com.isbusy.restapi.isbusyrestapi.services.UserService;
import com.isbusy.restapi.isbusyrestapi.responses.EvaluationResponse;
import com.isbusy.restapi.isbusyrestapi.responses.StatsResponse;

@RestController
public class EvaluationController {
	// injecting the EvaluationService singleton
	@Autowired
	private EvaluationService evaluationService;

	@Autowired
	private UserService userService;

	// index
	// @RequestMapping(method = RequestMethod.GET, value =
	// "/emplacements/{id}/evaluations")
	// by default, it is a default request, if we need to use an other methode we
	// have specify it !
	public List<Evaluation> getAllEvaluations(@PathVariable String id) {
		System.out.println("index");
		// as we have annotated this as a RestController the list returned
		// is automatically converted to JSon, daaaaamn !
		return evaluationService.getAllEvaluations(id);
	}

	// show
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/{emplacementId}/evaluations/{id}")
	public Evaluation getEvaluation(@PathVariable long id) {
		return evaluationService.getEvaluation(id);
	}

	// create
	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/create/{emplacementId}/evaluations")
	public void addEvaluation(@RequestBody Evaluation evaluation, @PathVariable String emplacementId) {
		evaluation.setEmplacement(new Emplacement(emplacementId, "", "", 0, 0, 0));
		evaluationService.addEvaluation(evaluation);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/emplacements/delete/{emplacementId}/evaluations/{id}")
	public void deleteCourse(@PathVariable long id) {
		System.out.println("DELETE");
		evaluationService.deleteEvaluation(id);
	}

	/**
	 * Evaluation Response Entity used to handle HTTP response headers and body
	 * 
	 * @param
	 */
	public static ResponseEntity<EvaluationResponse> handleResponse(Evaluation evaluation,
			ArrayList<Evaluation> evaluations, String message, HttpStatus statusCode) {
		HttpHeaders headers = new HttpHeaders();
		int status = statusCode.value();
		headers.add("status", String.valueOf(statusCode));
		headers.add("message", message);
		return ResponseEntity.status(status).headers(headers)
				.body(new EvaluationResponse(evaluation, evaluations, message, status));
	}


	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/admin/stats")
	public ResponseEntity<StatsResponse> statistics() {
		int votes = evaluationService.countVotes();
		ArrayList<?> empCat = evaluationService.countEmplacementsByCategorie();
		int users = userService.countUsers();


		HttpHeaders headers = new HttpHeaders();
		headers.add("status", "200");
		headers.add("message", "OK");
		return ResponseEntity.ok().headers(headers).body(new StatsResponse(users, votes, "Les statistiques", 200, empCat));
	}
	

}
