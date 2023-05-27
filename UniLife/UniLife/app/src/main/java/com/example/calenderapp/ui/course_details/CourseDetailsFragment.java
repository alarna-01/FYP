package com.example.calenderapp.ui.course_details;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.calenderapp.R;
import com.example.calenderapp.databinding.FragmentCourseDetailsBinding;
import com.example.calenderapp.db.DatabaseHelper;
import com.example.calenderapp.model.HelperClass;
import com.example.calenderapp.model.Module;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsFragment extends Fragment {
    List<Module> list=new ArrayList<>();
    private FragmentCourseDetailsBinding binding;
    DatabaseHelper helper;
    ModuleAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourseDetailsBinding.inflate(inflater, container, false);
        helper=new DatabaseHelper(requireContext());
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvName.setText(HelperClass.users.getCourseTitle());
        binding.tvYear.setText(HelperClass.users.getYear());
        list.addAll(helper.getModule(String.valueOf(HelperClass.users.getUserId())));
        binding.rvModule.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter=new ModuleAdapter(list);
        binding.rvModule.setAdapter(adapter);
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(CourseDetailsFragment.this).navigate(R.id.action_nav_course_details_to_addModuleFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        list.addAll(helper.getModule(String.valueOf(HelperClass.users.getUserId())));
    }
}