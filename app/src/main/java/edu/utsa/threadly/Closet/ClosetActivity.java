package edu.utsa.threadly.Closet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.utsa.threadly.Outfit.OutfitsActivity;
import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Closet;
import edu.utsa.threadly.module.CsvFileManager;

public class ClosetActivity extends AppCompatActivity {

    private static final int ADD_CLOSET_REQUEST = 1;
    private static final String TAG = "ClosetActivity";

    private LinearLayout closetContainer;
    private CsvFileManager csvFileManager;

    private final ArrayList<Closet> closetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        closetContainer = findViewById(R.id.closetContainer);
        Button addClosetButton = findViewById(R.id.addClosetButton);

        // Load CSV
        String filename = "Closets.csv";
        csvFileManager = CsvFileManager.loadCsvToLocal(this, filename);
        loadClosetsFromCsv();
        displayClosets();

        addClosetButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddClosetActivity.class);
            startActivityForResult(intent, ADD_CLOSET_REQUEST);
        });
    }

    private void loadClosetsFromCsv() {
        closetList.clear();
        ArrayList<String[]> rows = csvFileManager.getRows();

        for(int i = 1; i < rows.size(); i++) {
            String[] s = rows.get(i);
            Log.d("CVS", "Row: " + s[0] + ", " + s[1]);
            if (s.length >= 2) {
                String name = s[0].trim();
                try {
                    int id = Integer.parseInt(s[1].trim());
                    closetList.add(new Closet(id, name));
                } catch (NumberFormatException e) {
                    Log.e("CSV", "Invalid ID format in CSV row: " + s[1], e);
                    Toast.makeText(this, "Error: Invalid ID format in CSV file.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error: Invalid row in CSV file.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayClosets() {
        closetContainer.removeAllViews();

        for (Closet closet : closetList) {
            Button closetButton = new Button(this);
            closetButton.setText(closet.getName());

            // Optional styling
            closetButton.setAllCaps(false);
            closetButton.setTextSize(20);
            closetButton.setPadding(20, 20, 20, 20);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dpToPx(100)
            );
            params.setMargins(20, 10, 20, 10);
            closetButton.setLayoutParams(params);

            // Handle button click
            closetButton.setOnClickListener(view -> {
                Intent intent = new Intent(this, OutfitsActivity.class);
                intent.putExtra("closetName", closet.getName());
                intent.putExtra("closetId", closet.getId());
                startActivity(intent);
            });

            closetContainer.addView(closetButton);
        }
    }
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClosets(); // reload from CSV and update UI
    }

    private void loadClosets() {
        closetList.clear();
        closetContainer.removeAllViews();

        CsvFileManager csvFileManager = CsvFileManager.loadCsvToLocal(this, "Closets.csv");
        ArrayList<String[]> rows = csvFileManager.getRows();
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row.length >= 2) {
                try {
                    closetList.add(new Closet(Integer.parseInt(row[1].trim()), row[0]));
                } catch (NumberFormatException e) {
                    Log.e("CSVC", "Invalid ID format in CSV row: " + row[1], e);
                    Toast.makeText(this, "Error: Invalid ID format in CSV file.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error: Invalid row in CSV file.", Toast.LENGTH_SHORT).show();
            }
        }

        displayClosets(); // repopulates the scroll view
    }

    private int getNextClosetId() {
        int maxId = -1;
        for (Closet c : closetList) {
            if (c.getId() > maxId) {
                maxId = c.getId();
            }
        }
        return maxId + 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CLOSET_REQUEST && resultCode == RESULT_OK) {
            String newClosetName = data.getStringExtra("closetName");
            if (newClosetName != null && !newClosetName.isEmpty()) {
                int newId = getNextClosetId();
                csvFileManager.addRow(new String[]{newClosetName, String.valueOf(newId)});
                csvFileManager.saveFile();

                closetList.add(new Closet(newId, newClosetName));
                displayClosets();
            } else {
                Toast.makeText(this, "Error: Closet name cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}