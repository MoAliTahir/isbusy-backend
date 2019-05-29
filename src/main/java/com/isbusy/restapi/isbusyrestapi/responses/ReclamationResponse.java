package com.isbusy.restapi.isbusyrestapi.responses;

import com.isbusy.restapi.isbusyrestapi.entities.Reclamation;

import java.util.List;

public class ReclamationResponse
{
    private Reclamation reclamation;
    private String message;
    private int status;
    private List<Reclamation> reclamations;

    public ReclamationResponse(Reclamation reclamation, String message, int status, List<Reclamation> reclamations) {
        this.reclamation = reclamation;
        this.message = message;
        this.status = status;
        this.reclamations = reclamations;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Reclamation> getReclamations() {
        return reclamations;
    }

    public void setReclamations(List<Reclamation> reclamations) {
        this.reclamations = reclamations;
    }
}