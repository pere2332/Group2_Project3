package com.example.group2_project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView newVehicle, usedVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        newVehicle = (ImageView) findViewById(R.id.newVehicle);
        usedVehicle = (ImageView) findViewById(R.id.usedVehicle);

        // New vehicle listener
        newVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "newVehicles");
                startActivity(intent);
            }
        });

        // Used vehicle listener
        usedVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "usedVehicles");
                startActivity(intent);
            }
        });
    }
}