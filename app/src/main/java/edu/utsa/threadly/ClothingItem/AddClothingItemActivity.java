package edu.utsa.threadly.ClothingItem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.datatransport.backend.cct.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import edu.utsa.threadly.Outfit.AddOutfitActivity;
import edu.utsa.threadly.R;
import edu.utsa.threadly.module.CsvFileManager;

public class AddClothingItemActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;

    private EditText outfitNameInput;
    private Spinner outfitCategoryInput;
    private EditText outfitDescriptionInput;
    private Button confirmButton, cameraButton,deleteButton;
    private static ArrayList<String[]> outfitData = new ArrayList<>();
    private Uri imageUri;

    // ActivityResultLauncher for capturing images
    private ActivityResultLauncher<Uri> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_clothing_item);

        // Initialize views (no variable shadowing)
        outfitNameInput = findViewById(R.id.clothingItemNameInput);
        outfitDescriptionInput = findViewById(R.id.clothingItemDescriptionInput);
        confirmButton = findViewById(R.id.confirmButton);
        cameraButton = findViewById(R.id.add_image_button);
        deleteButton = findViewById(R.id.deleteButton);

        int outfitId = getIntent().getIntExtra("outfitId", -1);
        if (outfitId == -1) {
            Toast.makeText(this, "Error: Invalid closet ID", Toast.LENGTH_SHORT).show();
            Log.e("AddOutfitActivity", "Error: Invalid closet ID");
            finish();
            return;
        }

        // Initialize camera launcher
        setupCameraLauncher();

        cameraButton.setOnClickListener(v -> checkCameraPermissionAndOpen());

        confirmButton.setOnClickListener(v -> {
            String outfitName = outfitNameInput.getText().toString().trim();

            if (outfitName.isEmpty()) {
                Toast.makeText(this, "Enter an outfit name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (imageUri == null) {
                Toast.makeText(this, "Capture an image first", Toast.LENGTH_SHORT).show();
                return;
            }



            String category = getIntent().getStringExtra("category");
            CsvFileManager itemManager = CsvFileManager.loadCsvToLocal(AddClothingItemActivity.this, "ClothingItems.csv");

            String[] newRow = {outfitName, String.valueOf(outfitId), imageUri.toString(), category};
            outfitData.add(newRow);
            itemManager.addRow(newRow);
            itemManager.saveFile();



            Log.d("AddClothingItemActivity", "Outfit added: " + outfitName + ", Image URI: " + imageUri);



            Toast.makeText(this, "Outfit added!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            String clothingItemName = outfitNameInput.getText().toString().trim();

            if (clothingItemName.isEmpty()) {
                Toast.makeText(this, "Enter an outfit name", Toast.LENGTH_SHORT).show();
                return;
            }


            CsvFileManager itemManager = CsvFileManager.loadCsvToLocal(AddClothingItemActivity.this, "ClothingItems.csv");
            itemManager.deleteRowsByValue(clothingItemName,0);
            Log.d("AddClothingItemActivity", "Clothing deleted: " + clothingItemName + ", Image URI: " + imageUri);

            Toast.makeText(this, "Clothing item deleted!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });
    }



    private void setupCameraLauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean success) {
                        if (success != null && success) {
                            Toast.makeText(AddClothingItemActivity.this, "Image captured successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            imageUri = null;
                            Toast.makeText(AddClothingItemActivity.this, "Image capture failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to capture images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        try {
            File imageFile = createImageFile();
            if (imageFile != null) {
                imageUri = FileProvider.getUriForFile(
                        this,
                        "edu.utsa.threadly.fileprovider", // Correct authority
                        imageFile
                );
                cameraLauncher.launch(imageUri);
            } else {
                Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("AddClothingItemActivity", "Error creating image file", e);
            Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
}
