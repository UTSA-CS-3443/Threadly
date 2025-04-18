package edu.utsa.threadly.ClothingItem;

import static edu.utsa.threadly.module.CsvFileManager.loadCsvToLocal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.CsvFileManager;

public class AddClothingItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_clothing_item);

        CsvFileManager clothingItems = loadCsvToLocal(this, "Clothing_Items.csv");
        int closetId = getIntent().getIntExtra("closetId", -1);


        Button addPhotoButton = findViewById(R.id.add_image_button);
        Button confirmButton = findViewById(R.id.confirmButton);

addPhotoButton.setOnClickListener(v -> {
    // Check for camera permissions
    if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
        return;
    }

    // Create an intent to open the camera
    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    // Create a file to save the image
    File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "clothing_item_" + System.currentTimeMillis() + ".jpg");
    Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);

    // Save the URI to the CsvFileManager
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
    startActivityForResult(cameraIntent, 101);

    // Add the URI to the CSV file
    clothingItems.addRow(new String[]{photoUri.toString()});
    clothingItems.saveFile();
});


    }
}