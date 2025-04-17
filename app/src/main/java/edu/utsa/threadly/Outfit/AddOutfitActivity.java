package edu.utsa.threadly.Outfit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.CsvFileManager;

public class AddOutfitActivity extends AppCompatActivity {


    private EditText outfitNameInput;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_outfit);


        outfitNameInput = findViewById(R.id.outfitNameInput);
        confirmButton = findViewById(R.id.confirmButton);

        int closetId = getIntent().getIntExtra("closetId", -1);
        if (closetId == -1) {
            Toast.makeText(this, "Error: Invalid closet ID", Toast.LENGTH_SHORT).show();
            Log.e("AddOutfit", "Error: Invalid closet ID");
            finish();
            return;
        }

        confirmButton.setOnClickListener(v -> {
            String outfitName = outfitNameInput.getText().toString().trim();


            if (outfitName.isEmpty()) {
                Toast.makeText(AddOutfitActivity.this, "Enter an outfit name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Load the CSV file
            CsvFileManager csvFileManager = CsvFileManager.loadCsvToLocal(AddOutfitActivity.this, "Outfits.csv");

            // Generate a new unique ID (based on size of existing rows)
            int newId = csvFileManager.getMaxID(1);  // Caution: This assumes no deletions
            int outfit_id = csvFileManager.getRows().size()-1; // Caution: This assumes no deletions

            // Create and add the new row
            String[] newRow = { outfitName,String.valueOf(closetId),String.valueOf(outfit_id) };
            csvFileManager.addRow(newRow);
            csvFileManager.saveFile();
            Log.e("Rumber", " " + csvFileManager.getRows().size());



            Toast.makeText(AddOutfitActivity.this, "Outfit added!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            // Finish and go back to OutfitActivity
            finish();
        });
    }


}