package edu.utsa.threadly.ClothingItem;

import static edu.utsa.threadly.module.CsvFileManager.loadCsvToLocal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.threadly.Outfit.AddClosetActivity;
import edu.utsa.threadly.R;
import edu.utsa.threadly.module.CsvFileManager;

public class AddClothingItemActivity extends AppCompatActivity {


    private EditText itemNameInput;
    private Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_clothing_item);

        itemNameInput = findViewById(R.id.itemNameInput);
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pushed();
            }


        });
    }
    public void pushed(){
        String closetName = itemNameInput.getText().toString().trim();
        if (closetName.isEmpty()) {
            Toast.makeText(AddClothingItemActivity.this, "Enter a closet name", Toast.LENGTH_SHORT).show();
            return;
        }


        Toast.makeText(AddClothingItemActivity.this, "Closet added!", Toast.LENGTH_SHORT).show();
        // Finish and go back to ClosetActivity
        finish();

    }
}