package com.isbusy.restapi.isbusyrestapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;
import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;
import com.isbusy.restapi.isbusyrestapi.services.EvaluationService;

@RestController
public class EvaluationController {
	// injecting the EvaluationService singleton
	@Autowired
	private EvaluationService evaluationService;

	// index
	@RequestMapping(method = RequestMethod.GET, value = "/emplacements/{id}/evaluations")
	// by default, it is a default request, if we need to use an other methode we
	// have specify it !
	public List<Evaluation> getAllEvaluations(@PathVariable long id) {
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/emplacements/delete/{emplacementId}/evaluations/{id}")
	public void deleteCourse(@PathVariable long id) {
		System.out.println("DELETE");
		evaluationService.deleteEvaluation(id);
	}

}
