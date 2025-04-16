package edu.utsa.threadly.Closet;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import edu.utsa.threadly.MainActivity;
import edu.utsa.threadly.R;
import edu.utsa.threadly.module.ClosetManager;
import edu.utsa.threadly.module.ClothingItem;
import edu.utsa.threadly.module.Closet;

public class ClosetActivity extends AppCompatActivity {
    private ClosetManager closetManager;
    public void loadClosets(MainActivity activity){
        AssetManager manager = activity.getAssets();
        String filename = "Closets.csv";

        try (InputStream file = manager.open(filename);
             Scanner scan = new Scanner(file)) {

            if (scan.hasNextLine()) {
                scan.nextLine(); // Skip header
            }

            while (scan.hasNextLine()) {
                /*String[] line = scan.nextLine().split(",");
                if (line.length >= 3) {
                    try {
                        String name = line[0].trim();
                        int id = Integer.parseInt(line[1].trim());
                        String picture = line[2].trim();
                        String type = line[3].trim();


                        ClothingItem item = new ClothingItem(id,name,picture);
                        this.clothingItems.add(item);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing sighting ID: " + line[0], e);
                    }
                }
            }
            Log.d(TAG, "");
        } catch (IOException e) {
            Log.e(TAG, "Error loading sightings file", e);
            throw new RuntimeException("Failed ", e);
        }*/
            }
        }
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_closet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
    }
}