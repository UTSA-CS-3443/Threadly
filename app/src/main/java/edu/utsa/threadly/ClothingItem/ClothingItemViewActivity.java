package edu.utsa.threadly.ClothingItem;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.threadly.R;

public class ClothingItemViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clothing_item_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //TODO: Add clothing item image
        //TODO: finish implementing clothing item view activity

        TextView clothingItemName = findViewById(R.id.title);
        TextView clothingItemCategory = findViewById(R.id.category);
        ImageView clothingItemImage = findViewById(R.id.image);

        Log.d("clothing item category", "category: " + clothingItemCategory);

        String clothingItemNameString = getIntent().getStringExtra("clothingItemName");
        String clothingItemDescriptionString = getIntent().getStringExtra("clothingItemCategory");
        String clothingItemImageString = getIntent().getStringExtra("clothingItemImage");
        
        clothingItemName.setText(clothingItemNameString);
        clothingItemCategory.setText(clothingItemDescriptionString);

        try {
            if (clothingItemImageString != null && !clothingItemImageString.isEmpty()) {
                Uri imageUri = Uri.parse(clothingItemImageString);
                if (imageUri.getScheme() != null && !imageUri.getScheme().isEmpty()) {
                    clothingItemImage.setImageURI(imageUri);
                } else {
                    int resId = this.getResources().getIdentifier(
                            clothingItemImageString, "drawable", this.getPackageName());
                    clothingItemImage.setImageResource(resId != 0 ? resId : R.drawable.ic_launcher_background);
                }
            } else {
                clothingItemImage.setImageResource(R.drawable.ic_launcher_background);
            }
        } catch (Exception e) {
            Log.e("OutfitViewAdapter", "Image load failed", e);
            clothingItemImage.setImageResource(R.drawable.ic_launcher_background);
        }

    }
    
    
}