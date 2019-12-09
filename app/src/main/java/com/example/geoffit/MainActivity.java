package com.example.geoffit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView theText;
    private static int highScores = 0;
    int scored;
    int lastScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame();
            }
        });
        Intent intent = getIntent();
        scored = intent.getIntExtra("lastScore", 0);
        updateScore();
    }
    private void startGame() {
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }
    public void updateScore() {
        theText = (TextView)findViewById(R.id.highScore);
        if (scored > highScores) {
            highScores = scored;
            theText.setText("" + highScores);
        }
        theText.setText("" + highScores);
    }
}
