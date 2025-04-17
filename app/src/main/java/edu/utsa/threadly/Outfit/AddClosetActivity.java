package edu.utsa.threadly.Outfit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.utsa.threadly.R;

public class AddClosetActivity extends Activity {

    private EditText closetNameInput;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_closet);

        closetNameInput = findViewById(R.id.closetNameInput);
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String closetName = closetNameInput.getText().toString().trim();

                if (closetName.isEmpty()) {
                    Toast.makeText(AddClosetActivity.this, "Enter a closet name", Toast.LENGTH_SHORT).show();
                    return;
                }


                Toast.makeText(AddClosetActivity.this, "Closet added!", Toast.LENGTH_SHORT).show();

                // Finish and go back to ClosetActivity
                finish();
            }
        });
    }
}