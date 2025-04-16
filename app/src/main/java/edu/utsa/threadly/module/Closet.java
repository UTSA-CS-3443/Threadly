package edu.utsa.threadly.module;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;

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


    public String[] toStringArray(){
        return new String[]{this.name,String.format("%d", this.closetId)};


    }

    public static Closet csvToCloset(String[] row) {
        if (row == null || row.length < 2) {
            throw new IllegalArgumentException(
                    String.format("Invalid CSV row for Closet. Expected 2+ columns, got %s",
                            row == null ? "null" : row.length)
            );
        }

        try {
            int id = Integer.parseInt(row[1].trim());  // column 1: id (trim whitespace)
            String name = row[0].trim();              // column 0: name

            return new Closet(id, name);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("Invalid ID format in CSV row: %s", Arrays.toString(row)), e);
        }
    }


}
