package edu.utsa.threadly.Outfit;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.ClothingItem;
import edu.utsa.threadly.module.CsvFileManager;
import com.google.android.material.carousel.CarouselLayoutManager;

public class OutfitViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_view);

        String outfitName = getIntent().getStringExtra("outfitName");
        int outfitId = getIntent().getIntExtra("outfitId", -1);

        ArrayList<ClothingItem> clothingItems = loadClothingItemsForOutfit(outfitId);

        // Filter items by type
        ArrayList<ClothingItem> tops = new ArrayList<>();
        ArrayList<ClothingItem> bottoms = new ArrayList<>();
        ArrayList<ClothingItem> footwear = new ArrayList<>();
        ArrayList<ClothingItem> other = new ArrayList<>();

        for (ClothingItem item : clothingItems) {
            switch (item.getType().toLowerCase()) {
                case "shirt":
                case "jacket":
                case "sweater":
                    tops.add(item);
                    Log.d("OutfitViewActivity", "Added top: " + item.getName());
                    break;
                case "pants":
                case "shorts":
                    bottoms.add(item);
                    Log.d("OutfitViewActivity", "Added bottom: " + item.getName());
                    break;
                case "shoes":
                    footwear.add(item);
                    Log.d("OutfitViewActivity", "Added footwear: " + item.getName());
                    break;
                default:
                    other.add(item);
                    Log.d("OutfitViewActivity", "Added other: " + item.getName());
                    break;
            }
        }

        Log.d("OutfitViewActivity", "Tops size: " + tops.size());
        Log.d("OutfitViewActivity", "Bottoms size: " + bottoms.size());
        Log.d("OutfitViewActivity", "Footwear size: " + footwear.size());
        Log.d("OutfitViewActivity", "Other size: " + other.size());

        // Set up RecyclerViews
        RecyclerView topsRecyclerView = findViewById(R.id.carousel_tops);
        RecyclerView bottomsRecyclerView = findViewById(R.id.carousel_Bottoms);
        RecyclerView footwearRecyclerView = findViewById(R.id.carousel_Footwear);
        RecyclerView otherRecyclerView = findViewById(R.id.carousel_Other);



        topsRecyclerView.setLayoutManager(new CarouselLayoutManager());
        bottomsRecyclerView.setLayoutManager(new CarouselLayoutManager());
        footwearRecyclerView.setLayoutManager(new CarouselLayoutManager());
        otherRecyclerView.setLayoutManager(new CarouselLayoutManager());

        topsRecyclerView.setAdapter(new OutfitViewAdapter(this, tops));
        bottomsRecyclerView.setAdapter(new OutfitViewAdapter(this, bottoms));
        footwearRecyclerView.setAdapter(new OutfitViewAdapter(this, footwear));
        otherRecyclerView.setAdapter(new OutfitViewAdapter(this, other));
    }

    private ArrayList<ClothingItem> loadClothingItemsForOutfit(int outfitId) {
        ArrayList<String[]> rows = CsvFileManager.loadCsvToLocal(this, "Clothing_Items.csv").getRows();
        ArrayList<ClothingItem> clothingItems = new ArrayList<>();

        for (int i = 1; i < rows.size(); i++) { // Skip header row
            String[] row = rows.get(i);
            Log.d("OutfitViewActivity", "Row: " + String.join(", ", row));
            if (row.length >= 4) { // Ensure the row has all required columns
                try {
                    ClothingItem item = ClothingItem.csvToItem(row);
                    if (item.getId() == outfitId) {
                        Log.d("OutfitViewActivity", "Adding item: " + item.getName());
                        clothingItems.add(item); // Add the ClothingItem object
                    }
                } catch (IllegalArgumentException e) {
                    Log.e("OutfitViewActivity", "Invalid row in CSV file: " + String.join(", ", row), e);
                }
            } else {
                Log.e("OutfitViewActivity", "Invalid row in CSV file: " + String.join(", ", row));
            }
        }
        return clothingItems;
    }
}