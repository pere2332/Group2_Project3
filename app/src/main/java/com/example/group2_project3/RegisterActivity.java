package com.example.group2_project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class  RegisterActivity extends AppCompatActivity {

    private Button createAccountBtn;
    private EditText nameEditText, phoneEditText, passwordEditText;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountBtn = (Button) findViewById(R.id.register_btn);
        nameEditText = (EditText) findViewById(R.id.register_username_input);
        phoneEditText = (EditText) findViewById(R.id.register_phone_input);
        passwordEditText = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }


    private void CreateAccount() {
        String username = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please enter your Username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                            }
                        }
                    });
            // [END create_user_with_email]


            ValidUser(username, phone, password);
        }
    }

    private void ValidUser(final String username, final String phone, final String password) {
        // Starting point for all Database operations
        final DatabaseReference RootRefer;
        // Gets DatabaseReference for the database root node.
        RootRefer = FirebaseDatabase.getInstance().getReference();

        RootRefer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check database, if username not exists then create new account
                if(!(snapshot.child("Users").child(username).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("username", username);
                    userDataMap.put("phone", phone);
                    userDataMap.put("password", password);

                    // Update data map
                    RootRefer.child("Users").child(username).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Create Account Successfully!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                // Take user to login page
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Failed to create account, please try later!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Username ( " + username + " ) already Exists!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try another Username!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}