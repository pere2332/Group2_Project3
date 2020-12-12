package com.example.group2_project3;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group2_project3.Model.Vehicles;
import com.example.group2_project3.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class NewActivity extends AppCompatActivity { // new working version :)


    private DatabaseReference reference;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used);

        reference = FirebaseDatabase.getInstance().getReference();


        mRecyclerView = findViewById(R.id.recycler_menu);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query = reference.child("Vehicles").orderByChild("category").equalTo("newVehicles");
        FirebaseRecyclerOptions<Vehicles> options = new FirebaseRecyclerOptions.Builder<Vehicles>()
                .setQuery(query, Vehicles.class).build();

        options.getSnapshots();

        FirebaseRecyclerAdapter<Vehicles, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Vehicles, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Vehicles model) {
                        holder.txtProductName.setText(model.getvName());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("price $ " + model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.mImageView);

                        // click on a car and be taken to the detail layout
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(NewActivity.this, DetailActivity.class);
                                intent.putExtra("vid", model.getVid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_row, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }


                };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}