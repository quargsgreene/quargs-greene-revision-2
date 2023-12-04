package com.example.stacker;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.example.stacker.BlockView.EndGameListener;

public class GameActivity extends Activity implements EndGameListener {

    private BlockView blockView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blockView = new BlockView(this, this);
        setContentView(blockView);
    }

    @Override
    public void onGameEnded() {
        // Transition to the exit screen
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.exit_screen);
                // Delay the button visibility to simulate the 3-second pause
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.exitButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.restartButton).setVisibility(View.VISIBLE);
                    }
                }, 3000);
            }
        });
    }
}
