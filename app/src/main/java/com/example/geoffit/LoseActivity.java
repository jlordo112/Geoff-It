package com.example.geoffit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoseActivity extends AppCompatActivity {

    int highScore;
    TextView newText;
    TextView theScore;
    int scored;
    int lastScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame();
            }
        });
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goMain();
            }
        });
        Intent intent = getIntent();
        scored = intent.getIntExtra("score", 0);
        lastScore = intent.getIntExtra("lastScore", 0);
        textChange();
    }
    private void startGame() {
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra("lastScore", lastScore);
        startActivity(gameIntent);
    }
    private void goMain() {
        Intent gameIntent = new Intent(this, MainActivity.class);
        gameIntent.putExtra("lastScore", lastScore);
        startActivity(gameIntent);
    }
    private void textChange() {
        newText = (TextView)findViewById(R.id.insultBox);
        theScore = (TextView)findViewById(R.id.theScore);
        theScore.setText("" + scored);
        double x = (int)(Math.random()*((3-1)+1))+1;
        if (x == 1) {
            newText.setText("Geoff would have scored at least " + (scored + 1));
        } else if (x == 2) {
            newText.setText("Bruhhhh, that's really the best you can do???");
        } else {
            newText.setText("This is why your parents don't love you");
        }
    }
}
