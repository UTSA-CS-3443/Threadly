package edu.utsa.threadly.module;

import static android.content.ContentValues.TAG;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import edu.utsa.threadly.Outfit.OutfitsActivity;

public class Closet {
    //helps the outfits determine which closet they are connected to
    //when generating a new closetId determine the largest current closetId in the file and make the new closet the next one
    private int closetId;
    private String name;
    private ArrayList<Outfit> outfits;


    public Closet(int id, String name) {
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

    public void addOutfit(Outfit outfit) {
        this.outfits.add(outfit);
    }

    public Outfit removeOutfit(int index) {
        return this.outfits.remove(index);
    }
    public Outfit grabOutfit(int index){return this.outfits.get(index);}

    public int amountOfOutfits(){
        return outfits.size();
    }

    public void loadOutfits(OutfitsActivity activity) {
        outfits.clear(); // Clear existing data
        AssetManager manager = activity.getAssets();
        String filename = "Outfits.csv";

        try (InputStream file = manager.open(filename);
             Scanner scan = new Scanner(file)) {

            if (scan.hasNextLine()) {
                scan.nextLine(); // Skip header
            }

            while (scan.hasNextLine()) {
                String[] enclosureCsv = scan.nextLine().split(",");

                try {
                    String name = enclosureCsv[0];
                    int closetId = Integer.parseInt(enclosureCsv[1]);
                    int outfitId = Integer.parseInt(enclosureCsv[2]);

                    if (this.closetId ==closetId) {
                        Outfit outfit = new Outfit(closetId, outfitId, name);
                        this.outfits.add(outfit);
                    }

                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error parsing enclosure ID: " + enclosureCsv[0], e);
                }

            }
            Log.d(TAG, "Loaded " + outfits.size() + " enclosures");
        } catch (IOException e) {
            Log.e(TAG, "Error loading enclosures file", e);
            throw new RuntimeException("Failed to load enclosures data", e);
        }
    }


    /**
     * Loads dinosaurs from file
     * and stores them into enclosure
     */
    /*
    public void loadOutfits(MainActivity activity) throws IOException {
        outfits.clear(); // Clear existing data
        AssetManager manager = activity.getAssets();
        String filename = "Outfits.csv";

        try (InputStream file = manager.open(filename);
             Scanner scan = new Scanner(file)) {

            if (scan.hasNextLine()) {
                scan.nextLine(); // Skip header
            }

            // Now iterate through each line in the file
            while(scan.hasNextLine()) {
                String line = scan.nextLine(); // Read next line

                String[] item = line.split(",");
                try {
                    String dinoName = item[0];
                    String dinoSpecies = item[2];
                    String dinoDiet = item[1];
                    int dinoAge = Integer.parseInt(item[3]);
                    int dinoId = Integer.parseInt(item[4]);

                    // Assuming `this.equalsTo(dinoId)` is checking the right condition
                    if (this.equalsTo(dinoId)) {
                        Dinosaur dinosaur = new Dinosaur(dinoName, dinoSpecies, dinoDiet, dinoAge, dinoId);
                        this.dinosaurs.add(dinosaur);
                    }

                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error parsing Dinosaur: " + item[0], e);
                }
            }

        } catch (IOException e) {
            Log.e(TAG, "Error loading enclosures file", e);
            throw new RuntimeException("Failed to load enclosures data", e);
        }
    }


*/













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
