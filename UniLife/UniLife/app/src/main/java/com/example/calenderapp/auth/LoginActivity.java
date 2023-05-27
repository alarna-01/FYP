package com.example.calenderapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.calenderapp.MainActivity;
import com.example.calenderapp.R;
import com.example.calenderapp.databinding.ActivityLoginBinding;
import com.example.calenderapp.db.DatabaseHelper;
import com.example.calenderapp.model.HelperClass;
import com.example.calenderapp.model.UsersModel;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private DatabaseHelper helper;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new DatabaseHelper(this);

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLoginMethod();
            }
        });
    }

    private void callLoginMethod() {

        email = binding.etEmail.getText().toString();
        password = binding.etPassword.getText().toString();

        if (email.isEmpty()) {
            showToast("Please enter email");
        } else if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showToast("Please enter email in correct format");
        } else if (password.isEmpty()) {
            showToast("Please enter password");
        } else {

            Boolean flag = false;
            List<UsersModel> list;
            list = helper.getAllUsers();

            for (UsersModel users : list) {
                if (email.equals(users.getEmail()) && password.equals(users.getPassword())) {
                    flag = true;
                    showToast("Login Successfully");
                    HelperClass.users = users;
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    break;
                } else {
                    flag = false;
                }
            }
            if (!flag) {

                showToast("Error check user or password");

            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}