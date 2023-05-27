package com.example.calenderapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.calenderapp.R;
import com.example.calenderapp.databinding.ActivityRegisterBinding;
import com.example.calenderapp.db.DatabaseHelper;
import com.example.calenderapp.model.UsersModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private int PICK_IMAGE_GALLERY = 123;
    private DatabaseHelper helper;
    String imageUri="", fName, lName, courseTitle, email, year, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new DatabaseHelper(this);

        binding.rlPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRegisterMethod();
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
              finish();
            }
        });

    }

    private void callRegisterMethod(){

        fName = binding.etFirstName.getText().toString();
        lName = binding.etLastName.getText().toString();
        courseTitle = binding.etCourseTitle.getText().toString();
        email = binding.etEmail.getText().toString();
        year = binding.etYearOfStudy.getText().toString();
        password = binding.etPassword.getText().toString();

        if (imageUri.isEmpty()){
            showToast("Please select profile picture");
        }else if (fName.isEmpty()){
            showToast("Please enter first name");
        }else if (lName.isEmpty()){
            showToast("Please enter last name");
        }else if (courseTitle.isEmpty()){
            showToast("Please enter course title");
        }else if (email.isEmpty()){
            showToast("Please enter university institution email");
        } else if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showToast("Please enter email in correct format");
        }else if (year.isEmpty()){
            showToast("Please enter year of study");
        }else if (password.isEmpty()){
            showToast("Please enter password");
        }else{

            UsersModel model = new UsersModel(imageUri, fName, lName, courseTitle, email, year, password);
            helper.register(model);
            showToast("Successfully Registered");
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Getting Gallery Image uri
            Uri uriImage = data.getData();
            try {
                binding.ivImage.setImageURI(uriImage);
                imageUri = uriImage.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}