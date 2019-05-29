package com.isbusy.restapi.isbusyrestapi.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.servlet.FlashMapManager;

@Entity
@Table(name = "reclamation")
public class Reclamation {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name="id")
private int id;
@Column(name="titre")
private String titre;
@Column(name="message")
private String message;
@Column(name="created_at")
@CreationTimestamp
private Date created_at;
@Column(name="id_user")
private int id_user;



    public Reclamation() {
    }
    

    public Reclamation(int id, String titre, String message, Date created_at, int id_user) {
        this.id = id;
        this.titre = titre;
        this.message = message;
        this.created_at = created_at;
        this.id_user = id_user;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getId_user() {
        return this.id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

}