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

    /**
     * Represents a closet that stores the various outfits a user inputs
     * @param id
     * @param name
     */
    public Closet(int id, String name){
        this.closetId = id;
        this.name = name;
        this.outfits = new ArrayList<Outfit>();
    }

    /**
     * returns the id for the closet that connects it to its corresponding outfit
     * @return
     */
    public int getId() {
        return closetId;
    }

    /**
     * sets the id for the closet that connects it to its corresponding outfit
     * @param id
     */

    public void setId(int id) {
        this.closetId = id;
    }

    /**
     * returns the name for the closet
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * sets the name for the closet
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * retrieves the outfit array from the stored
     * @return
     */
    public ArrayList<Outfit> getOutfits() {
        return outfits;
    }

    /**
     * overwrites the outfit array
     * @param outfits
     */
    public void setOutfits(ArrayList<Outfit> outfits) {
        outfits = outfits;
    }

    /**
     * adds an outfit object to the array list
     * @param outfit
     */
    public void addOutfit(Outfit outfit){
        this.outfits.add(outfit);
    }

    /**
     * removes an outfit object from the array
     * @param index
     * @return
     */
    public Outfit removeOutfit(int index){
        return this.outfits.remove(index);
    }

    /**
     * transforms the closet object into a string array
     * @return
     */
    public String[] toStringArray(){
        return new String[]{this.name,String.format("%d", this.closetId)};


    }

    /**
     * Transforms the rows created by the CSV manager into closet objects
     * @param row
     * @return
     */
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
