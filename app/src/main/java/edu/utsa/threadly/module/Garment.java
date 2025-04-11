package edu.utsa.threadly.module;

public class Garment {

    private int id;
    private String name;
    private String dateCreated;
    private String picture;

     Garment(int id, String name, String dateCreated, String picture){
        this.id = id;
        this.dateCreated = dateCreated;
        this.picture = picture;
        this.name = name;


    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
