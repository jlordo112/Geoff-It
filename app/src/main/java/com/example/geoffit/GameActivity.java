package com.example.geoffit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    String[] tasks = {"tap", "shake", "geoff"};
    int score = 0;
    Timer taskTimer;
    boolean shouldShake;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final TextView countdownLabel = findViewById(R.id.countdown);
        countdownLabel.setVisibility(View.VISIBLE);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownLabel.setText("" + (millisUntilFinished / 1000 + 1));
            }

            public void onFinish() {
                countdownLabel.setVisibility(View.GONE);
                round();
            }
        }.start();
    }

    private void newTask() {
        findViewById(R.id.tapLayout).setVisibility(View.GONE);
        findViewById(R.id.shakeLayout).setVisibility(View.GONE);
        findViewById(R.id.geoffLayout).setVisibility(View.GONE);

        shouldShake = false;

        String task = tasks[(int) Math.random()*tasks.length];

        if (task.equals("tap")) {
            findViewById(R.id.tapLayout).setVisibility(View.VISIBLE);
        } else if (task.equals("shake")) {
            findViewById(R.id.shakeLayout).setVisibility(View.VISIBLE);
            shouldShake = true;
        } else if (task.equals("geoff")) {
            findViewById(R.id.geoffLayout).setVisibility(View.VISIBLE);;
        }
    }

    public void tapped(View view) {
        success();
    }

    private void success() {
        taskTimer.cancel();
        score++;
        round();
    }

    private void round() {
        TextView scoreDisplay = findViewById(R.id.score);
        scoreDisplay.setText("" + score);
        newTask();
        taskTimer = new Timer();
        taskTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                lose();
            }
        }, 2000);

    }
    private void lose() {
        Intent loseIntent = new Intent(this, LoseActivity.class);
        loseIntent.putExtra("score", score);
        startActivity(loseIntent);
    }
}

