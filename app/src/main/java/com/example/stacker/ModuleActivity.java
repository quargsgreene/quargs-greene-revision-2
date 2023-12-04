package com.example.stacker; // Add this package statement

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModuleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        // Find the button by its ID
        Button startGameButton = (Button) findViewById(R.id.buttonStartGame);

        // Set a click listener on that button
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use an Intent to start the MainActivity
                Intent intent = new Intent(ModuleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
