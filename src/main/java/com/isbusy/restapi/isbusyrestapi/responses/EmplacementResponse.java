package com.isbusy.restapi.isbusyrestapi.responses;

import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;

public class EmplacementResponse {
    private Emplacement emplacement;
    private String message;
    private int status;

    public EmplacementResponse(Emplacement emplacement, String message, int status) {
        this.setEmplacement(emplacement);
        this.setMessage(message);
        this.setStatus(status);
    }

    public Emplacement getEmplacement() {
        return this.emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}