package edu.utsa.threadly.module;

import java.util.ArrayList;

public class Closet {
    //helps the outfits determine which closet they are connected to
    //when generating a new closetId determine the largest current closetId in the file and make the new closet the next one
    private int closetId;
    private String name;
    private ArrayList<Outfit> outfits;


    Closet(int id, String name){
        this.closetId = id;
        this.name = name;
        this.outfits = new ArrayList<Outfit>();
    }
    public int getId() {
        return closetId;
    }

    public void setId(int id) {
        this.closetId = id;
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
