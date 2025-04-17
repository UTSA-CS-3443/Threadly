package edu.utsa.threadly.Outfit;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import edu.utsa.threadly.R;
import edu.utsa.threadly.module.Closet;
import edu.utsa.threadly.module.ClosetManager;
import edu.utsa.threadly.module.CsvFileManager;

public class ClosetActivity extends AppCompatActivity {

    private static final int ADD_CLOSET_REQUEST = 1;

    private LinearLayout closetContainer;
    private CsvFileManager csvFileManager;

    private ClosetManager closetManager;

    private Button addClosetButton;

    private final ArrayList<Closet> closetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);



        addClosetButton = findViewById(R.id.addClosetButton);

        closetManager = new ClosetManager();

        try {
            closetManager.loadClosets(this);
            Log.d(TAG, "Loaded successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error loading outfits", e);
            Toast.makeText(this, "Error loading outfits data", Toast.LENGTH_LONG).show();
        }

        Closet closet;
        for (int i = 0; i < closetManager.amountOfClosets(); i++) {
            closet = closetManager.grabCloset(i);

            closetsSetupButton(closet);  // Setup button for enclosure
        }

        addClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closetContainer = findViewById(R.id.closetContainer);
                closetContainer.setVisibility(View.GONE);



                // Add slight delay before adding new closet


                        activateAddCloset();

                closetContainer.setVisibility(View.VISIBLE);

            }
        });

    }

    public void activateAddCloset() {

        Intent intent = new Intent(this, AddClosetActivity.class);
        startActivity(intent); // Just for show, doesn't wait for result
        // Create hardcoded closet

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Closet closet = new Closet(3, "Chicago");
                closetManager.addCloset(closet);
                closetsSetupButton(closet);
            }
        }, 300);


        // Immediately create a button for the new closet


        // Optional: Show confirmation
        //Toast.makeText(this, "Closet added: " + closet.getName(), Toast.LENGTH_SHORT).show();

        // If you still want to open AddClosetActivity for UI purposes:

    }


    private void closetsSetupButton(Closet closet) {
        // Create a layout object
        LinearLayout rootLayout = findViewById(R.id.closetContainer);
        MaterialButton myButton = new MaterialButton(this);
        myButton.setText(closet.getName());
        myButton.setTextColor(Color.WHITE);
        myButton.setCornerRadius(100);
        myButton.setWidth(5);
        myButton.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
        myButton.setTag(closet);
        rootLayout.addView(myButton);

        // Set the button's click listener
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Closet selectedCloset = (Closet) view.getTag();
               openOutfitScreen(selectedCloset);
            }
        });



        }

        public void openOutfitScreen(Closet closet) {
            Intent intent = new Intent(this,  OutfitsActivity.class);
            intent.putExtra("CLOSET_ID", closet.getId());
            startActivity(intent);


        }


    }
