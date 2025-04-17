package edu.utsa.threadly.Outfit;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Closet;
import edu.utsa.threadly.module.ClothingItem;
import edu.utsa.threadly.module.Outfit;

public class ClothingItemViewActivity extends AppCompatActivity {
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clothing_item_view);

         layout = findViewById(R.id.items_container);

        int closetId = getIntent().getIntExtra("CLOSET_ID", -1);
        int outfitId =getIntent().getIntExtra("OUTFIT_ID", -1);
        Outfit itemManager = new Outfit(closetId,outfitId,"");
        try {
            itemManager.loadItems(this);
            Log.d(TAG, "Loaded successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error loading sightings", e);
            Toast.makeText(this, "Error loading item data", Toast.LENGTH_LONG).show();
        }

        ClothingItem item;
        for (int i = 0; i < itemManager.amountOfItems(); i++) {
            item = itemManager.grabItem(i);

            addItemCard(item);
        }
    }

    private void addItemCard(ClothingItem item) {
        // Inflate the card layout
        LinearLayout dinoDetailsLayout = new LinearLayout(this);
        dinoDetailsLayout.setOrientation(LinearLayout.HORIZONTAL); // Horizontal layout for name, diet, age, and image

        // Create an ImageView for the dinosaur image
        ImageView imageView = new ImageView(this);
        String imageName = item.getName().toLowerCase().replace(" ", "_"); // Format name for image (e.g., "T_Rex" -> "t_rex")
        int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        if (imageResourceId != 0) { // If the image exists
            imageView.setImageResource(imageResourceId);
        } else {
            imageView.setImageResource(R.drawable.funny_car); // Set a default image if none is found
        }
        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200)); // Set size for image

        // Create a TextView for the dinosaur details (name, diet, and age)
        TextView textView = new TextView(this);
        textView.setText("Name: " + item.getName() + "\nType: " + item.getType());
        textView.setPadding(16, 16, 16, 16); // Add some padding
        textView.setTextSize(16); // Set font size
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1)); // TextView should take remaining space

        // Add ImageView and TextView to the horizontal LinearLayout
        dinoDetailsLayout.addView(imageView);
        dinoDetailsLayout.addView(textView);

        // Add the LinearLayout (containing both the image and the text) to the main layout
        layout.addView(dinoDetailsLayout);
    }


}