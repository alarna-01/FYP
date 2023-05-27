package com.example.calenderapp.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.calenderapp.R;
import com.example.calenderapp.auth.LoginActivity;
import com.example.calenderapp.auth.RegisterActivity;
import com.example.calenderapp.databinding.FragmentProfileBinding;
import com.example.calenderapp.db.DatabaseHelper;
import com.example.calenderapp.model.HelperClass;
import com.example.calenderapp.model.UsersModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private int PICK_IMAGE_GALLERY = 123;
    private DatabaseHelper helper;
    String imageUri="", fName, lName, courseTitle, email, year, password;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentProfileBinding.inflate(getLayoutInflater(), container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new DatabaseHelper(requireContext());
        imageUri = HelperClass.users.getImageUri();
        password = HelperClass.users.getPassword();
        Glide.with(binding.ivImage).load(Uri.parse(imageUri)).placeholder(R.drawable.iv_profile).into(binding.ivImage);
        binding.etFirstName.setText(HelperClass.users.getfName());
        binding.etLastName.setText(HelperClass.users.getlName());
        binding.etCourseTitle.setText(HelperClass.users.getCourseTitle());
        binding.etEmail.setText(HelperClass.users.getEmail());
        binding.etYearOfStudy.setText(HelperClass.users.getYear());

        binding.rlPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
            }
        });


        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callUpdateMethod();

            }
        });

    }

    private void callUpdateMethod(){

            fName = binding.etFirstName.getText().toString();
            lName = binding.etLastName.getText().toString();
            courseTitle = binding.etCourseTitle.getText().toString();
            email = binding.etEmail.getText().toString();
            year = binding.etYearOfStudy.getText().toString();

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

                UsersModel model = new UsersModel(HelperClass.users.getUserId(), imageUri, fName, lName, courseTitle, email, year, password);
                helper.updateUser(model);
                showToast("Successfully Saved");
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}