package edu.utsa.threadly.Outfit;

import android.content.Intent;
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

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Closet;

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

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outfitPushed();
            }


        });


    }

    public void outfitPushed() {
        String outfitName = outfitNameInput.getText().toString().trim();
        if (outfitName.isEmpty()) {
            Toast.makeText(AddOutfitActivity.this, "Enter a Outfit name", Toast.LENGTH_SHORT).show();
            return;
        }


        Toast.makeText(AddOutfitActivity.this, "Closet added!", Toast.LENGTH_SHORT).show();
        // Finish and go back to ClosetActivity
        finish();

    }}