package com.example.group2_project3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.validation.Validator;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName, vName, vYear, vMileage, vDescription, vPrice,
            saveCurrentDate, saveCurrentTime, vehicleRandomKey, downloadImageUrl;
    private ImageView inputVehicleImage;
    private Button addVehicleBtn;
    private EditText inputVehicleName, inputVehicleYear, inputVehicleMileage,
            inputVehicleDescription, inputVehiclePrice;
    private static final int galleryPick = 1;
    private Uri imageUri;
    private StorageReference vehicleImageRef;
    private DatabaseReference vehicleRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        // according to the category to new/used add vehicle activity
        categoryName = getIntent().getExtras().get("category").toString();
        vehicleImageRef = FirebaseStorage.getInstance().getReference().child("ProductViewHolder Images");
        vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        Toast.makeText(this, "Add " + categoryName, Toast.LENGTH_SHORT).show();

        inputVehicleImage = (ImageView) findViewById(R.id.select_vehicle_image);
        addVehicleBtn = (Button) findViewById(R.id.add_new_product);
        inputVehicleName = (EditText) findViewById(R.id.vehicle_name);
        inputVehicleYear = (EditText) findViewById(R.id.vehicle_year);
        inputVehicleMileage = (EditText) findViewById(R.id.vehicle_mileage);
        inputVehicleDescription = (EditText) findViewById(R.id.vehicle_description);
        inputVehiclePrice = (EditText) findViewById(R.id.vehicle_price);
        loadingBar = new ProgressDialog(this);

        inputVehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        addVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidVehicleData();
            }
        });
    }


    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==galleryPick && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
            inputVehicleImage.setImageURI(imageUri);
        }
    }

    private void ValidVehicleData(){
        vName = inputVehicleName.getText().toString();
        vYear = inputVehicleYear.getText().toString();
        vMileage = inputVehicleMileage.getText().toString();
        vDescription = inputVehicleDescription.getText().toString();
        vPrice = inputVehiclePrice.getText().toString();

        if(imageUri == null){
            Toast.makeText(this, "Image is Missing!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vName)) {
            Toast.makeText(this, "ProductViewHolder's Name is empty!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vYear)) {
            Toast.makeText(this, "ProductViewHolder's Year is empty!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vMileage)) {
            Toast.makeText(this, "ProductViewHolder's Mileage is empty!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vDescription)) {
            Toast.makeText(this, "ProductViewHolder's Description is empty!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vPrice)) {
            Toast.makeText(this, "ProductViewHolder's Price is empty!", Toast.LENGTH_SHORT).show();
        }else{
            StoreVehicleInfo();
        }
    }


    private void StoreVehicleInfo() {
        loadingBar.setTitle("Adding ProductViewHolder!");
        loadingBar.setMessage("Please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        vehicleRandomKey = saveCurrentDate + saveCurrentTime;

        // combining to a very unique random key for store the image
        final StorageReference filePath = vehicleImageRef.child(imageUri.getLastPathSegment() + vehicleRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        // image upload fail / success
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Image upload Successfully!", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Get Image Url Successfully!", Toast.LENGTH_SHORT).show();
                            // save vehicle information to database
                            saveVehicleInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveVehicleInfoToDatabase() {
        HashMap<String, Object> vehicleMap = new HashMap<>();
        vehicleMap.put("vid", vehicleRandomKey);
        vehicleMap.put("date", saveCurrentDate);
        vehicleMap.put("time", saveCurrentTime);
        vehicleMap.put("vName", vName);
        vehicleMap.put("year", vYear);
        vehicleMap.put("mileage", vMileage);
        vehicleMap.put("description", vDescription);
        vehicleMap.put("price", vPrice);
        vehicleMap.put("image", downloadImageUrl);
        vehicleMap.put("category", categoryName);

        vehicleRef.child(vehicleRandomKey).updateChildren(vehicleMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // jump to category activity
                    Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);

                    loadingBar.dismiss();
                    Toast.makeText(AdminAddNewProductActivity.this, "ProductViewHolder is Added to DB!", Toast.LENGTH_SHORT).show();
                }else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}