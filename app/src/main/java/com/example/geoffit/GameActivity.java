package com.example.geoffit;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final TextView countdownLabel = findViewById(R.id.countdown);
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownLabel.setText("" + (millisUntilFinished / 1000 + 1));
            }

            public void onFinish() {
                play();
            }
        }.start();
    }

    private void play() {

    }
}

