package com.example.geoffit;

import android.content.Intent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import com.squareup.seismic.ShakeDetector;

import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements ShakeDetector.Listener {

    int scored;
    TextView theText;
    private static int highScores = 0;
    String[] tasks = {"tap", "shake", "geoff"};
    int score = 0;
    Timer taskTimer;
    Timer listenTimer;
    boolean shouldShake;
    MediaRecorder recorder = null;
    MediaPlayer good = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final TextView countdownLabel = findViewById(R.id.countdown);
        countdownLabel.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        scored = intent.getIntExtra("lastScore", 0);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownLabel.setText("" + (millisUntilFinished / 1000 + 1));
            }

            public void onFinish() {
                countdownLabel.setVisibility(View.GONE);
                round();
            }
        }.start();
        good = MediaPlayer.create(this, R.raw.good);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);

    }

    private void newTask() {
        findViewById(R.id.tapLayout).setVisibility(View.GONE);
        findViewById(R.id.shakeLayout).setVisibility(View.GONE);
        findViewById(R.id.geoffLayout).setVisibility(View.GONE);

        shouldShake = false;

        String task = tasks[(int) (Math.random()*tasks.length)];

        if (task.equals("tap")) {
            findViewById(R.id.tapLayout).setVisibility(View.VISIBLE);
        } else if (task.equals("shake")) {
            findViewById(R.id.shakeLayout).setVisibility(View.VISIBLE);
            shouldShake = true;
        } else if (task.equals("geoff")) {
            findViewById(R.id.geoffLayout).setVisibility(View.VISIBLE);
            startRecording();
            listenTimer = new Timer();
            listenTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    listen();
                }
            }, 0, 10);
        }
    }

    public void tapped(View view) {
        success();
    }

    private void success() {
        good.start();
        taskTimer.cancel();
        score++;
        round();
    }

    private void round() {
        final TextView scoreDisplay = findViewById(R.id.score);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreDisplay.setText("" + score);
                newTask();
            }
        });
        taskTimer = new Timer();
        taskTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                lose();
            }
        }, 1000);

    }
    private void lose() {
        Intent loseIntent = new Intent(this, LoseActivity.class);
        if (scored > score) {
            loseIntent.putExtra("score", score);
            loseIntent.putExtra("lastScore", scored);
        } else {
            loseIntent.putExtra("score", score);
            loseIntent.putExtra("lastScore", score);
        }
        startActivity(loseIntent);
    }
    @Override
    public void hearShake() {
        if (shouldShake) {
            success();
        }
    }

    private void listen() {
        if (recorder.getMaxAmplitude() > 20000) {
            System.out.println("geoffed");
            stopRecording();
            listenTimer.cancel();
            success();
        }
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile("/dev/null");
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e("whoopsie", "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            listenTimer.cancel();
            recorder.release();
            recorder = null;
        }
    }
}

