package com.example.group2_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    ImageView newButton;
    ImageView usedButton;
    Button logoutButton;
    Button videoBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        newButton = findViewById(R.id.newButton);
        usedButton = findViewById(R.id.usedButton);
        logoutButton = findViewById(R.id.logoutButton);
        videoBtn = findViewById(R.id.videoButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                finish();
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewCar();
            }
        });

        usedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUsedCar();
            }
        });

        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVideo();
            }
        });
    }

    private void goToUsedCar(){
        Intent intent = new Intent(this, UsedActivity.class);
        startActivity(intent);
    }

    private void goToNewCar(){
        Intent intent = new Intent(this, NewCarPage.class);
        startActivity(intent);
    }

    private void goToVideo(){
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }
}
