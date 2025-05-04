package edu.utsa.threadly.Outfit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.CsvFileManager;
import edu.utsa.threadly.module.Outfit;

public class OutfitsActivity extends AppCompatActivity {

    private static final String TAG = "OutfitsActivity";
    private CsvFileManager csvFileManager;
    private OutfitAdapter outfitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: OutfitsActivity");
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
        ArrayList<Outfit> outfits = loadOutfitsForCloset(closetId);

        // Set up the adapter
        outfitAdapter = new OutfitAdapter(this, outfits);
        outfitsRecyclerView.setAdapter(outfitAdapter);

        findViewById(R.id.addOutfitButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddOutfitActivity.class);
            intent.putExtra("closetId", closetId);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filename = "Outfits.csv";
        csvFileManager = CsvFileManager.loadCsvToLocal(this, filename);
        Log.e(TAG, "onActivityResult: OutfitsActivity");
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Reload outfits from the CSV file
            int closetId = getIntent().getIntExtra("closetId", -1);
            if (closetId == -1) {
                Log.e(TAG, "Error: Invalid closet ID.");
                Toast.makeText(this, "Error: Invalid closet ID.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            Log.d(TAG, "Reloading outfits for closet ID: " + closetId);
            ArrayList<Outfit> outfits = loadOutfitsForCloset(closetId);
            for (Outfit outfit : outfits) {
                Log.d("test", "Outfit: " + outfit);
            }

            // Update the adapter with the new data
            outfitAdapter.updateData(outfits);
        }
    }

    private ArrayList<Outfit> loadOutfitsForCloset(int closetId) {
        ArrayList<String[]> rows = csvFileManager.getRows();
        ArrayList<Outfit> outfits = new ArrayList<>();
        Log.d("OutfitActivity", "Rows size: " + rows.size());
        Log.d("OutfitActivity", "ClosetId: " + closetId);
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row.length >= 3) {
                try {
                    int rowClosetId = Integer.parseInt(row[1].trim());
                    Log.d("OutfitActivity", "Row closet ID: " + rowClosetId);
                    if (rowClosetId == closetId) {
                        String outfitName = row[0].trim();
                        int outfitId = Integer.parseInt(row[2].trim());
                        Log.d("OutfitActivity", "Adding outfit: " + outfitName);
                        outfits.add(new Outfit(closetId, outfitId, outfitName));
                    }
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Invalid format in CSV row: " + String.join(", ", row), e);
                }
            } else {
                Log.e(TAG, "Invalid row in CSV file: " + String.join(", ", row));
            }
        }
        return outfits;
    }
}