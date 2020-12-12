package com.example.group2_project3;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group2_project3.Model.Users;
import com.example.group2_project3.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private EditText usernameEditText, passwordEditText;
    private ProgressDialog loadingBar;
    private TextView userLogin, adminLogin;

    private String parentDbName = "Users"; 


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.login_btn);
        usernameEditText = (EditText) findViewById(R.id.login_username_input);
        passwordEditText = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);
        adminLogin = (TextView) findViewById(R.id.admin_link);
        userLogin = (TextView) findViewById(R.id.user_link);

        // Login button listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        // Admin login listener
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setText("ADMIN LOGIN");
                adminLogin.setVisibility(View.INVISIBLE);
                userLogin.setVisibility(View.VISIBLE);
                // parent DB Admins
                parentDbName = "Admins";
            }
        });

        // User login listener
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setText("USER LOGIN");
                userLogin.setVisibility(View.INVISIBLE);
                adminLogin.setVisibility(View.VISIBLE);
                // parent DB Users
                parentDbName = "Users";
            }
        });
    }
    private void LoginUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please enter your Username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowLogin(username, password);
        }
    }
    private void AllowLogin(final String username, final String password) {
// Starting point for all Database operations
        final DatabaseReference RootRefer;
// Gets DatabaseReference for the database root node.
        RootRefer = FirebaseDatabase.getInstance().getReference();
        RootRefer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
// check the username in the BD if exists
                if(snapshot.child(parentDbName).child(username).exists()){
// get users info from firebase and save to 'users'
                    Users usersD = snapshot.child(parentDbName).child(username).getValue(Users.class);
                    if(usersD.getUsername().equals(username)){
                        // check password is correct
                        if(usersD.getPassword().equals(password)){
                           if(parentDbName.equals("Admins")){
                               Toast.makeText(LoginActivity.this, "Welcome Admin: " + username + "!", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                               startActivity(intent);
                           }
                           else if(parentDbName.equals("Users")){
                               Toast.makeText(LoginActivity.this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                               Prevalent.currentOnlineUser = usersD;
                               startActivity(intent);
                           }

                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Username!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}