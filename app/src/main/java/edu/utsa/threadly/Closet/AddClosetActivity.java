package edu.utsa.threadly.Closet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.CsvFileManager;

public class AddClosetActivity extends Activity {

    private EditText closetNameInput;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_closet);

        closetNameInput = findViewById(R.id.closetNameInput);
        confirmButton = findViewById(R.id.confirmButton);

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
                int newId = csvFileManager.getRows().size();  // Caution: This assumes no deletions

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