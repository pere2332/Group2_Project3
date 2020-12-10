package com.example.group2_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group2_project3.Model.Vehicles;
import com.example.group2_project3.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    private Button addToCartBtn;
    private Button goToCartBtn;
    private ImageView vehicleImage;
    private TextView vehicleName, vehiclePrice, vehicleDescription;
    private String vehicleID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        addToCartBtn = findViewById(R.id.addto_cart_button);
        vehicleImage = findViewById(R.id.product_image);
        vehicleName = findViewById(R.id.product_name);
        vehiclePrice = findViewById(R.id.product_price);
        vehicleDescription = findViewById(R.id.product_description);

        vehicleID = getIntent().getStringExtra("vid");
        getVehicleDetails(vehicleID);

        //add to card activity
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addingToCartList();
            }
        });

        goToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DetailActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getVehicleDetails(String vehicleID) {
        DatabaseReference vehiclesRef = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        vehiclesRef.child(vehicleID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Vehicles vehicles = snapshot.getValue(Vehicles.class);
                    vehicleName.setText(vehicles.getvName());
                    vehiclePrice.setText(vehicles.getPrice());
                    vehicleDescription.setText(vehicles.getDescription());
                    Picasso.get().load(vehicles.getImage()).into(vehicleImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void addingToCartList()
    {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", vehicleID);
        cartMap.put("product_name", vehicleName.getText().toString());
        cartMap.put("product_image", vehicleImage);
        cartMap.put("vehiclePrice", vehiclePrice);
        cartMap.put("pdescription", vehicleDescription);

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(vehicleID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Products").child(vehicleID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            Toast.makeText(DetailActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(DetailActivity.this, ShoppingCartActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                        }
                    }
                });
    }
}
