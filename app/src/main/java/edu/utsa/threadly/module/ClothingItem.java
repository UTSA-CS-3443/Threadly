package edu.utsa.threadly.module;

import java.util.Arrays;

public class ClothingItem {

    //helps connect the clothing item to its corresponding outfit
    private int outfitId;
    private String name;
    // picture id will just be the name but any spaces will be replaced with "_"
    private String picture;
    //type will be designated by the user of the app when inputting clothing details
    private String type;

    ClothingItem(int id, String name, String picture,String type){
        this.outfitId = id;
        this.picture = picture;
        this.name = name;
        this.type = type;


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

    public String[] toStringArray(){
        return new String[]{this.name,String.format("%d"),String.format("%d", this.outfitId),this.picture,this.type};


    }

    public static ClothingItem csvToItem(String[] row) {
        if (row == null || row.length < 4) {
            throw new IllegalArgumentException(
                    String.format("Invalid CSV row for Closet. Expected 2+ columns, got %s",
                            row == null ? "null" : row.length)
            );
        }

        try {
            int closetId = Integer.parseInt(row[1].trim());
            String name = row[0].trim();
            String picture = row[2];
            String type = row[3];

            return new ClothingItem(closetId,name,picture,type);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("Invalid ID format in CSV row: %s", Arrays.toString(row)), e);
        }
    }


}


