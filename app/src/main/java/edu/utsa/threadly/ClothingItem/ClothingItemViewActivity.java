package edu.utsa.threadly.ClothingItem;

import android.os.Bundle;
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
        TextView clothingItemDescription = findViewById(R.id.category);
        ImageView clothingItemImage = findViewById(R.id.image);

        String clothingItemNameString = getIntent().getStringExtra("clothingItemName");
        String clothingItemDescriptionString = getIntent().getStringExtra("clothingItemCategory");
        String clothingItemImageString = getIntent().getStringExtra("clothingItemImage");

    }
}