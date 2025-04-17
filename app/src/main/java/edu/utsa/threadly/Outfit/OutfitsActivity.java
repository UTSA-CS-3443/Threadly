package edu.utsa.threadly.Outfit;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Closet;
import edu.utsa.threadly.module.ClosetManager;
import edu.utsa.threadly.module.Outfit;

public class OutfitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outfits);

        int closetId = getIntent().getIntExtra("CLOSET_ID", -1);
        Closet outfitManager = new Closet(closetId,"Random");

        try {
            outfitManager.loadOutfits(this);
            Log.d(TAG, "Loaded successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error loading sightings", e);
            Toast.makeText(this, "Error loading animal data", Toast.LENGTH_LONG).show();
        }

        Outfit outfit;
        for (int i = 0; i < outfitManager.amountOfOutfits(); i++) {
            outfit = outfitManager.grabOutfit(i);

            outfitsSetupButton(outfit);  // Setup button for enclosure
        }
    }
    private void outfitsSetupButton(Outfit outfit) {
        // Create a layout object
        LinearLayout rootLayout = findViewById(R.id.OutfitContainer);
        MaterialButton myButton = new MaterialButton(this);
        myButton.setText(outfit.getName());
        myButton.setTextColor(Color.WHITE);
        myButton.setCornerRadius(100);
        myButton.setWidth(5);
        myButton.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
        myButton.setTag(outfit);
        rootLayout.addView(myButton);

        // Set the button's click listener
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Outfit selectedOutfit = (Outfit) view.getTag();
                openOutfitScreen(selectedOutfit);
            }
        });

    }

    public void openOutfitScreen(Outfit outfit) {
        Intent intent = new Intent(this,  ClothingItemViewActivity.class);
        intent.putExtra("OUTFIT_ID", outfit.getOutfitId());
        intent.putExtra("CLOSET_ID", outfit.getClosetId());
        startActivity(intent);


    }


}

