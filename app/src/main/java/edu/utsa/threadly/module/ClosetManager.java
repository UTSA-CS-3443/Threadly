package edu.utsa.threadly.module;

import static android.content.ContentValues.TAG;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import edu.utsa.threadly.Outfit.ClosetActivity;

public class ClosetManager {

    private ArrayList<Closet> closets;

    public ClosetManager(){
        this.closets = new ArrayList<Closet>();
    }

    public int amountOfClosets(){
        return closets.size();
    }
    public ArrayList<Closet> getClosets() {
        return this.closets;
    }

    public void setClosets(ArrayList<Closet> closets) {
        this.closets = closets;
    }

    public void addCloset(Closet closet){
        this.closets.add(closet);
    }

    public Closet removeCloset(int index){
        return this.closets.remove(index);
    }

    public Closet grabCloset(int index){return this.closets.get(index);}

    public void loadClosets(ClosetActivity activity) {
        closets.clear(); // Clear existing data
        AssetManager manager = activity.getAssets();
        String filename = "Closets.csv";

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


                    Closet closet = new Closet(closetId,name);
                    this.closets.add(closet);

                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error parsing enclosure ID: " + enclosureCsv[0], e);
                }

            }
            Log.d(TAG, "Loaded " + closets.size() + " enclosures");
        } catch (IOException e) {
            Log.e(TAG, "Error loading enclosures file", e);
            throw new RuntimeException("Failed to load enclosures data", e);
        }
    }

}


