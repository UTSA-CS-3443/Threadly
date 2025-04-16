package edu.utsa.threadly.Closet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.utsa.threadly.Outfit.OutfitsActivity;
import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Closet;
import edu.utsa.threadly.module.CsvFileManager;

public class ClosetActivity extends AppCompatActivity {

    private static final int ADD_CLOSET_REQUEST = 1;

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

        for (String[] row : rows) {
            if (row.length >= 2) {
                String name = row[0].trim();
                int id;
                try {
                    id = Integer.parseInt(row[1].trim());
                    closetList.add(new Closet(id, name));
                } catch (NumberFormatException ignored) {}
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
            closetButton.setTextSize(18);
            closetButton.setPadding(20, 20, 20, 20);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
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
        for (String[] row : rows) {
            if (row.length >= 2) {
                closetList.add(new Closet(Integer.parseInt(row[1]), row[0]));
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
            }
        }
    }
}