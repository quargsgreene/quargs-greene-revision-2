package com.example.stacker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements BlockView.EndGameListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set content view to the layout that contains BlockView if you're inflating from XML
        // setContentView(R.layout.activity_main); // Uncomment if you have a layout file

        // Since MainActivity is now implementing EndGameListener, you can pass 'this' as the listener
        BlockView blockView = new BlockView(this, this);
        setContentView(blockView); // This sets the entire content to BlockView
    }

    @Override
    public void onGameEnded() {
        // Transition to the exit screen
        // Assuming exit_screen.xml is a separate layout file
        setContentView(R.layout.exit_screen);
        // You may need additional logic here to handle the game over scenario
    }
}
