package com.isbusy.restapi.isbusyrestapi.responses;

import java.util.ArrayList;
import java.util.HashMap;
import com.isbusy.restapi.isbusyrestapi.entities.Emplacement;

public class EmplacementResponse {
    private Emplacement emplacement;
    private String message;
    private int status;
    private ArrayList<Emplacement> emplacements;
    private HashMap<Integer, Integer> stats;

    public EmplacementResponse(Emplacement emplacement, ArrayList<Emplacement> emplacements, String message,
            int status) {
        this.setEmplacement(emplacement);
        this.setMessage(message);
        this.setStatus(status);
        this.setEmplacements(emplacements);
    }

    public EmplacementResponse(Emplacement emplacement, HashMap<Integer, Integer> stats, String message, int status) {
        setEmplacement(emplacement);
        setStats(stats);
        setMessage(message);
        setStatus(status);
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

    public ArrayList<Emplacement> getEmplacements() {
        return emplacements;
    }

    public void setEmplacements(ArrayList<Emplacement> e) {
        emplacements = e;
    }

    public void setStats(HashMap<Integer, Integer> s) {
        stats = s;
    }

    public HashMap<Integer, Integer> getStats() {
        return stats;
    }
}