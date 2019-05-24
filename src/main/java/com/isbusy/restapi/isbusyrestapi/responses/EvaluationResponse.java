package com.isbusy.restapi.isbusyrestapi.responses;

import java.util.ArrayList;
import com.isbusy.restapi.isbusyrestapi.entities.Evaluation;

public class EvaluationResponse {
    private Evaluation evaluation;
    private ArrayList<Evaluation> evaluations;
    private String message;
    private int status;

    public EvaluationResponse(Evaluation evaluation, ArrayList<Evaluation> evaluations, String message, int status) {
        setEvaluation(evaluation);
        setEvaluations(evaluations);
        setMessage(message);
        setStatus(status);
    }

    // Getters
    public Evaluation getEvaluation() {
        return evaluation;
    }

    public ArrayList<Evaluation> getEvaluations() {
        return evaluations;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    // Setters
    public void setEvaluation(Evaluation e) {
        evaluation = e;
    }

    public void setEvaluations(ArrayList<Evaluation> es) {
        evaluations = es;
    }

    
    public void setMessage(String m) {
        message = m;
    }

    public void setStatus(int s) {
        status = s;
    }
}