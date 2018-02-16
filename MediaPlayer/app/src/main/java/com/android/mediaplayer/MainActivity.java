package com.android.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    public static final int MEDIA_RES_ID = R.raw.audio;
    public static final int MEDIA_RES_ID_2 = R.raw.dreamatorium;

    private int currentResource;
    private LocalTime intervalStart, intervalEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intervalStart = LocalTime.parse("11:00:00");
        intervalEnd = LocalTime.parse("16:00:00");

        loadMedia(MEDIA_RES_ID);
        Button playButton = (Button) findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentResource = getCurrentResource();
                loadMedia(currentResource);
                mediaPlayer.start();
            }
        });
        Button pauseButton = (Button) findViewById(R.id.pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });
        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.reset();
                currentResource = getCurrentResource();
                loadMedia(MEDIA_RES_ID);
            }
        });
    }

    public int getCurrentResource() {
        int cr;
        LocalTime currentTime = TimeMachine.now().toLocalTime();
        if(currentTime.isAfter(intervalStart) && currentTime.isBefore(intervalEnd)) {
            cr = MEDIA_RES_ID;
        }
        else {
            cr = MEDIA_RES_ID_2;
        }
        return cr;
    }

    public void loadMedia(int resourceId) {
        if(mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        AssetFileDescriptor assetFileDescriptor = this.getResources().openRawResourceFd(resourceId);
        try {
            mediaPlayer.setDataSource(assetFileDescriptor);
            mediaPlayer.prepareAsync();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isChangingConfigurations() && mediaPlayer.isPlaying()) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
