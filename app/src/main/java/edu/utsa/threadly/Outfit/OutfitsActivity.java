package edu.utsa.threadly.Outfit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.CsvFileManager;

public class OutfitsActivity extends AppCompatActivity {

    private static final String TAG = "OutfitsActivity";
    private CsvFileManager csvFileManager;
    private OutfitAdapter outfitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);

        RecyclerView outfitsRecyclerView = findViewById(R.id.outfitsRecyclerView);
        outfitsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the closetId from the Intent
        int closetId = getIntent().getIntExtra("closetId", -1);
        if (closetId == -1) {
            Toast.makeText(this, "Error: Invalid closet ID.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load outfits from CSV
        String filename = "Outfits.csv";
        csvFileManager = CsvFileManager.loadCsvToLocal(this, filename);
        ArrayList<String> outfits = loadOutfitsForCloset(closetId);

        // Set up the adapter
        outfitAdapter = new OutfitAdapter(this,outfits);
        outfitsRecyclerView.setAdapter(outfitAdapter);
    }

    private ArrayList<String> loadOutfitsForCloset(int closetId) {
        ArrayList<String[]> rows = csvFileManager.getRows();
        ArrayList<String> outfits = new ArrayList<>();

        for (String[] row : rows) {
            if (row.length >= 3) {
                try {
                    int rowClosetId = Integer.parseInt(row[1].trim());
                    if (rowClosetId == closetId) {
                        outfits.add(row[0].trim());
                    }
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Invalid closet ID format in CSV row: " + row[1], e);
                }
            } else {
                Log.e(TAG, "Invalid row in CSV file: " + String.join(", ", row));
            }
        }
        return outfits;
    }
}