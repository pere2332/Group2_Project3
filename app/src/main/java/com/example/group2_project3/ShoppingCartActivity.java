package com.example.group2_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group2_project3.Model.ShoppingCart;
import com.example.group2_project3.Prevalent.Prevalent;
import com.example.group2_project3.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShoppingCartActivity extends AppCompatActivity {

    private DatabaseReference cartRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount;

    private int overTotalPrice = 0;
    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        totalAmount = getIntent().getStringExtra("Total Price");
        cartRef = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    txtTotalAmount.setText("Total Price $" + String.valueOf(overTotalPrice));
                    Intent intent = new Intent(ShoppingCartActivity.this,
                            PaymentActivity.class);
                    intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                    startActivity(intent);
                    finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");
        FirebaseRecyclerOptions<ShoppingCart> options =
                new FirebaseRecyclerOptions.Builder<ShoppingCart> ()
                        .setQuery(cartListRef.child("User View")
                                .child(Prevalent.currentOnlineUser.getUsername())
                                .child("Vehicles"), ShoppingCart.class)
                                .build();

        FirebaseRecyclerAdapter<ShoppingCart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<ShoppingCart, CartViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int i, @NonNull ShoppingCart model)
            {
                holder.txtProductPrice.setText("Price $" + model.getPrice());
                holder.txtProductName.setText(model.getName());
                holder.txtProductDescription.setText("Description: " + model.getVdescription());
                overTotalPrice = overTotalPrice + Integer.valueOf(model.getPrice());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}