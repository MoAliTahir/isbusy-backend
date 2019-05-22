package com.isbusy.restapi.isbusyrestapi.Classes;

public class GenericEmplacement {
    private String id;
    private String nomGenericEmplacement;
    private String categorie;
    private double latitude;
    private double longitude;
    private int status;

    public GenericEmplacement(String idGenericEmplacement, String nomGenericEmplacement, String categorie,
            float latitude, float longitude, int status) {
        this.id = idGenericEmplacement;
        this.nomGenericEmplacement = nomGenericEmplacement;
        this.categorie = categorie;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Place))
            return false;
        if (!super.equals(object))
            return false;
        GenericEmplacement GenericEmplacement = (GenericEmplacement) object;
        return java.util.Objects.equals(getId(), GenericEmplacement.getId())
                && java.util.Objects.equals(getLongitude(), GenericEmplacement.getLongitude())
                && java.util.Objects.equals(getLatitude(), GenericEmplacement.getLatitude())
                && java.util.Objects.equals(getName(), GenericEmplacement.getName())
                && java.util.Objects.equals(getCategory(), GenericEmplacement.getCategory());
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getLongitude(), getLatitude(), getName(), getCategory());
    }

    public GenericEmplacement(String id, double longitude, double latitude, String name, String category) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.nomGenericEmplacement = name;
        this.categorie = category;
    }

    public GenericEmplacement() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNomGenericEmplacement(String nomGenericEmplacement) {
        this.nomGenericEmplacement = nomGenericEmplacement;
    }

    public String getCategorie() {
        return this.categorie;

    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
