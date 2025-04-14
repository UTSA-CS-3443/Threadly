package edu.utsa.threadly.module;

public class ClothingItem {

    //helps connect the clothing item to its corresponding outfit
    private int outfitId;
    private String name;
    // picture id will just be the name but any spaces will be replaced with "_"
    private String picture;
    //type will be designated by the user of the app when inputting clothing details
    private String type;

    ClothingItem(int id, String name, String picture){
        this.outfitId = id;
        this.picture = picture;
        this.name = name;


    }
    public int getId() {
        return outfitId;
    }

    public void setId(int id) {
        this.outfitId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}


