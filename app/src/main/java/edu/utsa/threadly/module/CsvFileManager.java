package edu.utsa.threadly.module;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/*

How to use...

# loads the csv to local memory and into rows to be edited by the following functions
CsvFileManager name = loadCsvToLocal(activity,filename)

#edit however using the below functions
    addRow(String[] newRow)
    getRows()
    updateRow(String key, int columnIndex, String[] newRow)
    deleteRowsByValue(String key, int columnIndex)

# may be necessary since most of the above functions utilize String[]
    arrayListToArray(ArrayList<String> list)

#saves back to storage MAKE SURE TO DO THIS
   name.saveFile();




 */

/**
 * Used to manage local data saving and loading from it
 */
public class CsvFileManager {

    private static final String TAG = "CsvFileManager";
    private static final String PREFS_NAME = "CsvPrefs";

    private final Activity activity;
    private final String filename;
    private final String firstRunKey;
    private final ArrayList<String[]> rows;

    public CsvFileManager(Activity activity, String filename) {
        this.activity = activity;
        this.filename = filename;
        this.firstRunKey = "firstRun_" + filename;
        this.rows = new ArrayList<>();
    }

    public static CsvFileManager loadCsvToLocal(Activity activity,String filename){
        CsvFileManager file = new CsvFileManager(activity, filename);
        file.initializeFile();
        return file;

    }

    /**
     * copies the file from the assets to local data
     */
    private void initializeFile() {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean(firstRunKey, true);

        if (isFirstRun) {
            Log.i(TAG, "First run for " + filename + ": loading from assets.");
            try (InputStream assetStream = activity.getAssets().open(filename);
                 OutputStream out = activity.openFileOutput(filename, Context.MODE_PRIVATE)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = assetStream.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                prefs.edit().putBoolean(firstRunKey, false).apply();
            } catch (IOException e) {
                Log.e(TAG, "Error copying " + filename + " from assets to internal storage", e);
            }
        }

        try (InputStream in = activity.openFileInput(filename)) {
            loadCsv(in);
        } catch (IOException e) {
            Log.e(TAG, "Error reading " + filename + " from internal storage", e);
        }
    }

    /**
     *loads the csv into the the rows double array
     * @param in
     */
    private void loadCsv(InputStream in) {
        Scanner scan = new Scanner(in);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] tokens = line.split(",");
            rows.add(tokens);
        }
    }

    /**
     * saves anything in the rows of the csv object into local
     */
    public void saveFile() {
        try (OutputStream out = activity.openFileOutput(filename, Context.MODE_PRIVATE)) {
            for (String[] row : rows) {
                String line = String.join(", ", row) + "\n";
                out.write(line.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to save " + filename, e);
        }
    }

    /**
     * adds a new row to the csv object to be saved
     * @param newRow
     */
    public void addRow(String[] newRow) {
        rows.add(newRow);
    }

    /**
     * gets the rows from the object
     * @return
     */
    public ArrayList<String[]> getRows() {
        return rows;
    }

    /**
     * updates the row using
     * @param key the string you are comparing
     * @param columnIndex the column number example would be closets.csv with name = 0, closet.id =1
     * @param newRow the new row that you are going to use to replace the previous one
     */
    public void updateRow(String key, int columnIndex, String[] newRow) {
        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row.length > columnIndex && row[columnIndex].equals(key)) {
                rows.set(i, newRow);
                Log.i(TAG, "Updated row with key: " + key);
                return;
            }
        }
        Log.w(TAG, "No row found with key: " + key);
    }

    /**
     * deletes a row based on
     * @param key the string name you are comparing
     * @param columnIndex the column number example would be closets.csv with name = 0, closet.id =1
     */
    public void deleteRowsByValue(String key, int columnIndex) {
        rows.removeIf(row -> row.length > columnIndex && row[columnIndex].equals(key));
        Log.i(TAG, "Deleted rows with key: " + key);
    }

    /**
     * converts an arraylist into a String array for easier insertion into functions
     * @param list
     * @return
     */
    public String[] arrayListToArray(ArrayList<String> list){
        return list.toArray(new String[0]);

    }

    /**
     * grabs a row with a given name at a given index
     * @param key
     * @param columnIndex
     * @return
     */
    public String[] grabRow(String key, int columnIndex) {
        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row.length > columnIndex && row[columnIndex].equals(key)) {
                Log.i(TAG, "Found row with key: " + key);
                return rows.get(i);
            }
        }
        Log.w(TAG, "No row found with key: " + key);
        return null;
    }

    /**
     * gets the max id so that new closets or outfits can get new ids that don't conflict with previous ones
     * @param coumnIndex
     * @return
     */
    public int getMaxID(int coumnIndex) {
        int maxID = -1;
        Log.d(TAG, "coumnIndex: " + coumnIndex);
        if(coumnIndex <= 0){
            Log.w(TAG, "Column index must be greater than 0");
            return maxID;
        }
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row.length > 1) {
                try {
                    int id = Integer.parseInt(row[coumnIndex].trim());
                    if (id > maxID) {
                        maxID = id;
                    }
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Invalid ID format in CSV row: " + row[1], e);
                }
            }
        }
        return maxID;
    }


}

