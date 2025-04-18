package edu.utsa.threadly.Outfit;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

import edu.utsa.threadly.ClothingItem.AddClothingItemActivity;
import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Closet;
import edu.utsa.threadly.module.ClothingItem;
import edu.utsa.threadly.module.Outfit;

public class ClothingItemViewActivity extends AppCompatActivity {
    private LinearLayout layout;
    private Outfit itemManager;
    private Button addItemButton;
    private int itemCounter = 0;
    private final ClothingItem[] hardcodedItems = {
            new ClothingItem(7, "Hat", "black_fedora", "hat"),
            new ClothingItem(7, "Shirt", "white_button_up", "shirt"),
            new ClothingItem(7, "Vest", "black_vest", "vest"),
            new ClothingItem(7, "Tie", "black_tie", "tie"),
            new ClothingItem(7, "Default", "funny_cat", "?")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clothing_item_view);

        layout = findViewById(R.id.items_container);
        addItemButton = findViewById(R.id.addClothingItem);

        int closetId = getIntent().getIntExtra("CLOSET_ID", -1);
        int outfitId = getIntent().getIntExtra("OUTFIT_ID", -1);

        itemManager = new Outfit(closetId, outfitId, "");

        try {
            itemManager.loadItems(this);
            Log.d(TAG, "Loaded successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error loading items", e);
            Toast.makeText(this, "Error loading item data", Toast.LENGTH_LONG).show();
        }

        // Load existing items
        for (int i = 0; i < itemManager.amountOfItems(); i++) {
            addItemCard(itemManager.grabItem(i));
        }

        addItemButton.setOnClickListener(v -> {

            Intent intent = new Intent(this, AddClothingItemActivity.class);
            startActivity(intent);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            if (itemCounter < hardcodedItems.length) {
                activateAddItem();
                itemCounter++;
            }

                }
            }, 600);
        });
    }

    public void activateAddItem() {
        if (itemCounter >= hardcodedItems.length) {
            itemCounter = 0; // Reset counter if we've gone through all items
        }


        ClothingItem item = hardcodedItems[itemCounter];
        itemManager.addGarment(item);
        addItemCard(item);

        //Toast.makeText(this, "Item added: " + item.getName(), Toast.LENGTH_SHORT).show();
    }

    private void addItemCard(ClothingItem item) {
        // Create card container
        MaterialCardView card = new MaterialCardView(this);
        card.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        card.setCardElevation(8);
        card.setRadius(16);
        card.setContentPadding(16, 16, 16, 16);
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));

        // Create horizontal layout
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setGravity(Gravity.CENTER_VERTICAL);

        // Add image
        ImageView imageView = new ImageView(this);
        String imageName = item.getPicture().toLowerCase();
        int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        imageView.setImageResource(imageResourceId != 0 ? imageResourceId : R.drawable.funny_car);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));

        // Add text details
        TextView textView = new TextView(this);
        textView.setText(String.format("%s\nType: %s", item.getName(), item.getType()));
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        // Add views to card
        itemLayout.addView(imageView);
        itemLayout.addView(textView);
        card.addView(itemLayout);
        layout.addView(card);
    }
}