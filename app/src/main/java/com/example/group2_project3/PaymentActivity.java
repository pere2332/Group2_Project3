package com.example.group2_project3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group2_project3.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {
    private EditText nameEditText, cardNumEditText, expDateEditText,
            addressEditText, stateEditText, cityEditText, zipEditText;
    private Button confirmOrderBtn;
    private String totalAmonut = "";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        confirmOrderBtn = (Button) findViewById(R.id.confirm_button);
        nameEditText = (EditText) findViewById(R.id.cardHolder);
        cardNumEditText = (EditText) findViewById(R.id.cardNumber);
        expDateEditText = (EditText) findViewById(R.id.expDate);
        addressEditText = (EditText) findViewById(R.id.address);
        cityEditText = (EditText) findViewById(R.id.city);
        stateEditText = (EditText) findViewById(R.id.state);
        zipEditText = (EditText) findViewById(R.id.zipCode);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOrder();
            }
        });
    }

    private void confirmOrder()
    {
        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getUsername());
        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmonut);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("cardNumber", cardNumEditText.getText().toString());
        ordersMap.put("expDate", expDateEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("State", stateEditText.getText().toString());
        ordersMap.put("zipCode", zipEditText.getText().toString());

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task)
        {
                if (task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getUsername())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(PaymentActivity.this, "", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PaymentActivity.this, UsedActivity.class);
                                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
