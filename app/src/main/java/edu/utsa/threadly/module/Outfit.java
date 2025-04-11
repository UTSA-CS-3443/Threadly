package edu.utsa.threadly.module;

import java.util.ArrayList;

public class Outfit {

    private int id;
    private String name;

    private ArrayList<Garment> garments;

    Outfit(int id, String name){
        this.id = id;
        this.name = name;
        this.garments = new ArrayList<Garment>();
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

    public ArrayList<Garment> getGarments() {
        return garments;
    }

    public void setGarments(ArrayList<Garment> garments) {
        this.garments = garments;
    }

    public void addGarment(Garment garment){
        this.garments.add(garment);
    }

    public Garment removeGarment(int index){
        return this.garments.remove(index);
    }

}
