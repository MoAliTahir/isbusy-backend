package com.isbusy.restapi.isbusyrestapi.responses;

import java.util.ArrayList;

public class StatsResponse {

    private int nombreUsers;
    private int nombreVotes;
    private String message;
    private int status;
    private ArrayList<?> emplacementsParCategorie;



    public StatsResponse(int nombreUsers, int nombreVotes, String message, int status, ArrayList<?> emplacementsParCategorie) {
        this.nombreUsers = nombreUsers;
        this.nombreVotes = nombreVotes;
        this.message = message;
        this.status = status;
        this.emplacementsParCategorie = emplacementsParCategorie;
    }

    public int getNombreUsers() {
        return this.nombreUsers;
    }

    public void setNombreUsers(int nombreUsers) {
        this.nombreUsers = nombreUsers;
    }

    public int getNombreVotes() {
        return this.nombreVotes;
    }

    public void setNombreVotes(int nombreVotes) {
        this.nombreVotes = nombreVotes;
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

    public ArrayList<?> getEmplacementsParCategorie() {
        return this.emplacementsParCategorie;
    }

    public void setEmplacementsParCategorie(ArrayList<?> emplacementsParCategorie) {
        this.emplacementsParCategorie = emplacementsParCategorie;
    }

}
