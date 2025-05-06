package edu.utsa.threadly.module;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import edu.utsa.threadly.MainActivity;

public class Outfit {

    //keeps track of which closet outfit is stored in
    private int closetId;
    // helps the clothing items determine what outfit they are connected to
    //when generating a new outfitId determine the largest current outfitId in the file and make the new closet the next one
    private int outfitId;
    private String name;

    private ArrayList<ClothingItem> clothingItems;

    /**
     *An outfit that stores clothing items
     * @param closetId
     * @param outfitId
     * @param name
     */
    public Outfit(int closetId, int outfitId, String name){
        this.closetId = closetId;
        this.outfitId = outfitId;
        this.name = name;
        this.clothingItems = new ArrayList<ClothingItem>();
    }

    /**
     * grabs the closet id
     * @return
     */
    public int getClosetId() {
        return closetId;
    }

    /**
     * sets the closet id
     * @param closetId
     */
    public void setClosetId(int closetId) {
        this.closetId = closetId;
    }

    /**
     * gets the outfit id
     * @return
     */
    public int getOutfitId() {
        return outfitId;
    }

    /**
     * sets the outfit id
     * @param outfitId
     */
    public void setOutfitId(int outfitId) {
        this.outfitId = outfitId;
    }

    /**
     * gets the name of the outfit
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     *sets the name of the outfit
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the arraylists of clothing items
     * @return
     */
    public ArrayList<ClothingItem> getGarments() {
        return clothingItems;
    }

    /**
     * sets the arraylist of clothing items
     * @param clothingItems
     */
    public void setGarments(ArrayList<ClothingItem> clothingItems) {
        this.clothingItems = clothingItems;
    }

    /**
     * replaces arraylist of the object
     * @param clothingItems
     */
    public void addGarment(ClothingItem clothingItems){
        this.clothingItems.add(clothingItems);
    }

    /**
     * removes the clothing item at a given index
     * @param index
     * @return
     */
    public ClothingItem removeGarment(int index){
        return this.clothingItems.remove(index);
    }

    /**
     * formats the outfit object into a string array
     * @return
     */
    public String[] toStringArray(){
        return new String[]{this.name,String.format("%d", this.closetId),String.format("%d", this.outfitId)};


    }

    /**
     * makes the inserted string array into an object
     * @param row
     * @return
     */
    public static Outfit csvToOutfit(String[] row) {
        if (row == null || row.length < 3) {
            throw new IllegalArgumentException(
                    String.format("Invalid CSV row for Closet. Expected 2+ columns, got %s",
                            row == null ? "null" : row.length)
            );
        }

        try {
            int closetId = Integer.parseInt(row[1].trim());
            int outfitId = Integer.parseInt(row[2].trim());
            String name = row[0].trim();
            // column 0: name

            return new Outfit(closetId,outfitId,name);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("Invalid ID format in CSV row: %s", Arrays.toString(row)), e);
        }
    }

    }


