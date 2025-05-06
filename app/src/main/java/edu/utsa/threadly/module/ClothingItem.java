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


    /**
     * Represents a clothing item within the app with it's correspond name type and picture id which is used to track the picture uri.
     * @param id
     * @param name
     * @param picture
     * @param type
     */
    ClothingItem(int id, String name, String picture,String type){
        this.outfitId = id;
        this.picture = picture;
        this.name = name;
        this.type = type;


    }

    /**
     * Returns an id that links the singular clothing item to its corresponding outfit
     * @return
     */
    public int getId() {
        return outfitId;
    }

    /**
     * Sets the id that links  the singular clothing item to its corresponding outfit
     * @param id
     */

    public void setId(int id) {
        this.outfitId = id;
    }
    /**
     * returns the name of the clothing item
     */

    public String getName() {
        return name;
    }

    /**
     * sets the name of the clothing item
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the picture id or image uri of the clothing item
     * @return
     */
    public String getPicture() {
        return picture;
    }

    /**
     * sets the picture id or imgage uir of the clothing item
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * sets the type of clothing item. Choice between Top, Bottom, Shoes, Miscellaneous
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * sets the type of clothing item. Choice between Top, Bottom, Shoes, Miscellaneous
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Changes inseted row from csv into a clothing item object to be better utilized within program
     * @param row
     * @return
     */

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


