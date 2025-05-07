package edu.utsa.threadly.Closet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Closet;
import edu.utsa.threadly.module.CsvFileManager;
import edu.utsa.threadly.module.Outfit;

public class AddClosetActivity extends Activity {

    /**
     * screen to add a closet with text prompt for name
     */
    private EditText closetNameInput;
    private Button confirmButton;

    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_closet);

        closetNameInput = findViewById(R.id.closetNameInput);
        confirmButton = findViewById(R.id.confirmButton);
        deleteButton = findViewById(R.id.deleteButton);




        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String closetName = closetNameInput.getText().toString().trim();

                if (closetName.isEmpty()) {
                    Toast.makeText(AddClosetActivity.this, "Enter a closet name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Load the CSV file
                CsvFileManager closetManager = CsvFileManager.loadCsvToLocal(AddClosetActivity.this, "Closets.csv");
                CsvFileManager outfitManager = CsvFileManager.loadCsvToLocal(AddClosetActivity.this, "Outfits.csv");
                CsvFileManager itemManager = CsvFileManager.loadCsvToLocal(AddClosetActivity.this, "ClothingItems.csv");
                String[] closetRow = closetManager.grabRow(closetName,0);
                // If I used a hashmap this could likely be faster but right now works at O(N*M)

                for(int i = 0; i < outfitManager.getRows().size(); i++) {
                    String[] outfitRow = outfitManager.grabRow(closetRow[0],1);
                    for(int j = 0; j < itemManager.getRows().size(); j++){
                        itemManager.deleteRowsByValue(outfitRow[2],1);
                    }
                    outfitManager.deleteRowsByValue(closetRow[0],1);
                }



                closetManager.deleteRowsByValue(closetName,0);
                closetManager.saveFile();
                outfitManager.saveFile();
                itemManager.saveFile();

                Toast.makeText(AddClosetActivity.this, "Closet removed!", Toast.LENGTH_SHORT).show();

                // Finish and go back to ClosetActivity
                finish();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String closetName = closetNameInput.getText().toString().trim();

                if (closetName.isEmpty()) {
                    Toast.makeText(AddClosetActivity.this, "Enter a closet name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Load the CSV file
                CsvFileManager csvFileManager = CsvFileManager.loadCsvToLocal(AddClosetActivity.this, "Closets.csv");

                // Generate a new unique ID (based on size of existing rows)
                int newId = csvFileManager.getRows().size()-1;  // Caution: This assumes no deletions

                // Create and add the new row
                String[] newRow = { closetName, String.valueOf(newId) };
                csvFileManager.addRow(newRow);
                csvFileManager.saveFile();

                Toast.makeText(AddClosetActivity.this, "Closet added!", Toast.LENGTH_SHORT).show();

                // Finish and go back to ClosetActivity
                finish();
            }
        });
    }
}