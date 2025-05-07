package edu.utsa.threadly.Outfit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.threadly.Closet.AddClosetActivity;
import edu.utsa.threadly.R;
import edu.utsa.threadly.module.CsvFileManager;

public class AddOutfitActivity extends AppCompatActivity {


    private EditText outfitNameInput;
    private Button confirmButton;

    private Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_outfit);

        deleteButton = findViewById(R.id.deleteButton);
        outfitNameInput = findViewById(R.id.outfitNameInput);
        confirmButton = findViewById(R.id.confirmButton);

        int closetId = getIntent().getIntExtra("closetId", -1);
        if (closetId == -1) {
            Toast.makeText(this, "Error: Invalid closet ID", Toast.LENGTH_SHORT).show();
            Log.e("AddOutfit", "Error: Invalid closet ID");
            finish();
            return;
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outfitName = outfitNameInput.getText().toString().trim();

                if (outfitName.isEmpty()) {
                    Toast.makeText(AddOutfitActivity.this, "Enter a closet name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Load the CSV file

                CsvFileManager outfitManager = CsvFileManager.loadCsvToLocal(AddOutfitActivity.this, "Outfits.csv");
                CsvFileManager itemManager = CsvFileManager.loadCsvToLocal(AddOutfitActivity.this, "ClothingItems.csv");

                    //Most likely a more efficient way to go about doing this, but most will probably be O(N) anyway so this should work fine
                    String[] outfitRow = outfitManager.grabRow(outfitName,0);
                    for(int j = 0; j < itemManager.getRows().size(); j++){
                        itemManager.deleteRowsByValue(outfitRow[2],1);
                    }

                    outfitManager.deleteRowsByValue(outfitName,0);



                outfitManager.saveFile();
                itemManager.saveFile();

                Toast.makeText(AddOutfitActivity.this, "Closet removed!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                // Finish and go back to ClosetActivity
                finish();
            }
        });

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
            String[] newRow = { outfitName,String.valueOf(closetId),String.valueOf(outfit_id)};
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