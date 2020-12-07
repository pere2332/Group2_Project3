package com.example.group2_project3;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        video = findViewById(R.id.videoView);


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.carrace);
        video.setMediaController(mediaController);
        video.setVideoURI(uri);

        video.start();

    }
}