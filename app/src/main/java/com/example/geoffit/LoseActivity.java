package com.example.geoffit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoseActivity extends AppCompatActivity {

    TextView NewText;
    TextView TheScore;
    int scored;

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
        textChange();
        Intent intent = getIntent();
        scored = intent.getIntExtra("score", 0);
    }
    private void startGame() {
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }
    private void goMain() {
        Intent gameIntent = new Intent(this, MainActivity.class);
        startActivity(gameIntent);
    }
    private void textChange() {
        NewText = (TextView)findViewById(R.id.insultBox);
        TheScore = (TextView)findViewById(R.id.theScore);
        TheScore.setText("" + scored);
        double x = (int)(Math.random()*((3-1)+1))+1;
        if (x == 1) {
            NewText.setText("Geoff would have scored at least " + (scored + 1));
        } else if (x == 2) {
            NewText.setText("Bruhhhh, that's really the best you can do???");
        } else {
            NewText.setText("This is why your parents don't love you");
        }
    }
}
