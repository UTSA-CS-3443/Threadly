package edu.utsa.threadly.Closet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import edu.utsa.threadly.Outfit.AddOutfitActivity;
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
                CsvFileManager itemManager = CsvFileManager.loadCsvToLocal(AddClosetActivity.this, "Clothing_Items.csv");

                //TO DO: don't have time to fix delete feature but works good enough. It unfortunately doesn't delete all aspects if the closet itself is deleted
                try {
                    closetManager.deleteRowsByValue(closetName, 0);
                   /* Log.d("AddClosetActivity", closetRow[0]+" "+closetRow[1]);
                    for (int i = 0; i < outfitManager.getRows().size(); i++) {
                        String[] outfitRow = outfitManager.grabRow(closetRow[1].trim(), 1);
                        for (int j = 0; j < itemManager.getRows().size(); j++) {
                            itemManager.deleteRowsByValue(outfitRow[2].trim(), 1);
                        }
                        outfitManager.deleteRowsByValue(outfitRow[2], 0);
                    */

                }catch (Exception e) {
                    Log.e("AddClosetActivity", "Delete closet Failed");
                    Toast.makeText(AddClosetActivity.this, "Delete Closet Failed", Toast.LENGTH_SHORT).show();
                }

                //outfitManager.saveFile();
                //itemManager.saveFile();
                closetManager.saveFile();


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
                ArrayList<String[]> list =csvFileManager.getRows();
                int max = 0;
                for(int i = 1; i<csvFileManager.getRows().size(); i++ ){
                    String[] row = list.get(i);
                    int num = Integer.parseInt(row[1].trim());
                    if(num > max){
                        max = num;
                    }
                }
                int newId = max+1;  // Caution: This assumes no deletions

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