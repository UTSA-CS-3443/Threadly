package edu.utsa.threadly.module;

import java.util.ArrayList;

public class Outfit {

    //keeps track of which closet outfit is stored in
    private int closetId;
    // helps the clothing items determine what outfit they are connected to
    //when generating a new outfitId determine the largest current outfitId in the file and make the new closet the next one
    private int outfitId;
    private String name;

    private ArrayList<ClothingItem> clothingItems;

    Outfit(int closetId, int outfitId, String name){
        this.closetId = closetId;
        this.outfitId = outfitId;
        this.name = name;
        this.clothingItems = new ArrayList<ClothingItem>();
    }
    public int getClosetId() {
        return closetId;
    }

    public void setClosetId(int closetId) {
        this.closetId = closetId;
    }

    public int getOutfitId() {
        return outfitId;
    }

    public void setOutfitId(int outfitId) {
        this.outfitId = outfitId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ClothingItem> getGarments() {
        return clothingItems;
    }

    public void setGarments(ArrayList<ClothingItem> clothingItems) {
        this.clothingItems = clothingItems;
    }

    public void addGarment(ClothingItem clothingItems){
        this.clothingItems.add(clothingItems);
    }

    public ClothingItem removeGarment(int index){
        return this.clothingItems.remove(index);
    }

}
