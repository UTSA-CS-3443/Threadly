package edu.utsa.threadly.module;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    public void loadClothingItems(MainActivity activity){
        AssetManager manager = activity.getAssets();
        String filename = "Clothing_Items.csv";

        try (InputStream file = manager.open(filename);
             Scanner scan = new Scanner(file)) {

            if (scan.hasNextLine()) {
                scan.nextLine(); // Skip header
            }

            while (scan.hasNextLine()) {
                String[] line = scan.nextLine().split(",");
                if (line.length >= 3) {
                    try {
                        String name = line[0].trim();
                        int id = Integer.parseInt(line[1].trim());
                        String picture = line[2].trim();
                        String type = line[3].trim();


                        ClothingItem item = new ClothingItem(id,name,picture);
                        this.clothingItems.add(item);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing sighting ID: " + line[0], e);
                    }
                }
            }
            Log.d(TAG, "Loaded " + this.clothingItems.size() + " sightings");
        } catch (IOException e) {
            Log.e(TAG, "Error loading sightings file", e);
            throw new RuntimeException("Failed to load sightings data", e);
        }
    }

    }


