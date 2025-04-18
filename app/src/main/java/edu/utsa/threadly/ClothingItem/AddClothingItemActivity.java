package edu.utsa.threadly.ClothingItem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import edu.utsa.threadly.R;

public class AddClothingItem extends AppCompatActivity {

    private EditText outfitNameInput;
    private Button confirmButton, cameraButton;
    private static ArrayList<String[]> outfitData = new ArrayList<>();
    private Uri imageUri; // To store the captured image URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_clothing_item);

        //TODO fix the names
        outfitNameInput = findViewById(R.id.outfitNameInput);
        Button confirmButton = findViewById(R.id.confirmButton);
        Button cameraButton = findViewById(R.id.add_image_button);

        int closetId = getIntent().getIntExtra("closetId", -1);
        if (closetId == -1) {
            Toast.makeText(this, "Error: Invalid closet ID", Toast.LENGTH_SHORT).show();
            Log.e("AddOutfit", "Error: Invalid closet ID");
            finish();
            return;
        }

        cameraButton.setOnClickListener(v -> openCamera());

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

            int outfitId = outfitData.size();
            String[] newRow = {outfitName, String.valueOf(closetId), String.valueOf(outfitId), imageUri.toString()};
            outfitData.add(newRow);

            Log.d("AddOutfitActivity", "Outfit added: " + outfitName + ", Image URI: " + imageUri);

            Toast.makeText(this, "Outfit added!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File imageFile = createImageFile();
                if (imageFile != null) {
                    imageUri = Uri.fromFile(imageFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, 100);
                }
            } catch (IOException e) {
                Log.e("AddOutfitActivity", "Error creating image file", e);
                Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Image captured successfully", Toast.LENGTH_SHORT).show();
        } else {
            imageUri = null;
            Toast.makeText(this, "Image capture failed", Toast.LENGTH_SHORT).show();
        }
    }
}