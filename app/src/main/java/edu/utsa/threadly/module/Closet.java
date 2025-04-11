package edu.utsa.threadly.module;

import java.util.ArrayList;

public class Closet {
    private int id;
    private String name;
    private ArrayList<Outfit> outfits;


    Closet(int id, String name){
        this.id = id;
        this.name = name;
        this.outfits = new ArrayList<Outfit>();
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

    public ArrayList<Outfit> getOutfits() {
        return outfits;
    }

    public void setOutfits(ArrayList<Outfit> outfits) {
        outfits = outfits;
    }

    public void addOutfit(Outfit outfit){
        this.outfits.add(outfit);
    }

    public Outfit removeOutfit(int index){
        return this.outfits.remove(index);
    }

}
