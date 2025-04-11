package edu.utsa.threadly;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.threadly.module.Database.Closet;
import edu.utsa.threadly.module.Database.ClosetDao;
import edu.utsa.threadly.module.Database.ClothingItem;
import edu.utsa.threadly.module.Database.DatabaseClient;
import edu.utsa.threadly.module.Database.Outfit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Closet closet = new Closet();
        closet.name = "Summer Closet";

        Outfit outfit = new Outfit();
        outfit.name = "Beach Vibes";
        outfit.closetOwnerId = 1;

        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            // Save URI as string in ClothingItem
                            String uriString = selectedImageUri.toString();

                            ClothingItem item = new ClothingItem();
                            item.name = "T-Shirt";
                            item.category = "Top";
                            item.imageUri = uriString;
                            item.outfitOwnerId = 1;

                            // Save item to database (using DAO or ViewModel)
                            new Thread(() -> {
                                ClosetDao dao = DatabaseClient.getInstance(this).getDatabase().closetDao();
                                dao.insertItem(item);
                            }).start();
                        }
                    }
                }
        );

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);

        new Thread(() -> {
            ClosetDao dao = DatabaseClient.getInstance(this).getDatabase().closetDao();
            dao.insertCloset(closet);
            dao.insertOutfit(outfit);
        }).start();
    }
}