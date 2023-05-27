package com.example.calenderapp.ui.home;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.calenderapp.databinding.FragmentHomeBinding;
import com.example.calenderapp.db.DatabaseHelper;
import com.example.calenderapp.model.HelperClass;
import com.example.calenderapp.model.Module;
import com.example.calenderapp.ui.course_details.ModuleAdapter;
import com.skyhope.eventcalenderlibrary.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ModuleAdapter adapter;
    List<Module> list = new ArrayList<>();
    DatabaseHelper helper;
    List<Module> dayModules = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvNoData.setVisibility(View.GONE);

        helper = new DatabaseHelper(requireContext());
        list.clear();
        list.addAll(helper.getModule(String.valueOf(HelperClass.users.getUserId())));

        for (Module module : list) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(module.getTime());
            Event event = new Event(module.getTime(), "Module", Color.RED);
            binding.eventsCalendar.addEvent(event);
        }
        binding.eventsCalendar.initCalderItemClickCallback(dayContainerModel -> {
            Module details = null;
            dayModules.clear();
            for (Module appointment : list) {
                try {
                    //Parsing Dates in String
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(appointment.getTime());
                    Date date = calendar.getTime(); // current date and time
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                    String dateString = sdf.format(date);

                    //Comparing Dates from calendar and Db
                    if (dateString.contains(dayContainerModel.getDate())) {
                        details = appointment;
                        dayModules.add(details);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            if (dayModules.isEmpty()) {
                Toast.makeText(requireContext(), "No Data for Selected Data", Toast.LENGTH_SHORT).show();
                binding.tvNoData.setVisibility(View.VISIBLE);
                binding.rvModules.setVisibility(View.GONE);
            } else {
                binding.tvNoData.setVisibility(View.GONE);
                binding.rvModules.setVisibility(View.VISIBLE);

                setAdapter(dayModules);
            }
        });


    }

    public void setAdapter(List<Module> list) {
        binding.rvModules.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ModuleAdapter(list);
        binding.rvModules.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}