package com.example.calenderapp.ui.course_details;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.calenderapp.R;
import com.example.calenderapp.databinding.FragmentAddModuleBinding;
import com.example.calenderapp.db.DatabaseHelper;
import com.example.calenderapp.model.HelperClass;
import com.example.calenderapp.model.Module;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddModuleFragment extends Fragment {
    private int mYear, mMonth, mDay, mHour, mMinute;

    FragmentAddModuleBinding binding;
    DatabaseHelper helper;
    public AddModuleFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddModuleBinding.inflate(getLayoutInflater(),container,false);
        helper=new DatabaseHelper(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePicker(binding.etTime);
            }
        });

        binding.etType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showMenu(v);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=binding.etName.getText().toString();
                String time=binding.etTime.getText().toString();
                String type=binding.etType.getText().toString();

                if (title.isEmpty()){
                    Toast.makeText(requireContext(), "title is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (time.isEmpty()){
                    Toast.makeText(requireContext(), "Time is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type.isEmpty()){
                    Toast.makeText(requireContext(), "Type is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                Module module=new Module(formatTime(time), HelperClass.users.getUserId(), title,type);
                helper.addModule(module);
                findNavController(AddModuleFragment.this).navigateUp();
            }
        });

    }

    private void showMenu(View view){
        PowerMenu menu=new PowerMenu.Builder(requireActivity())
                .addItem(new PowerMenuItem("Formative"))
                .addItem(new PowerMenuItem("Summative"))
                .build();
        menu.setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
            @Override
            public void onItemClick(int position, PowerMenuItem item) {
                menu.dismiss();
                binding.etType.setText(item.getTitle());
            }
        });
        menu.showAsAnchorLeftBottom(view);
    }

    private void ShowDatePicker(EditText editText) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mDay = dayOfMonth;
                        mMonth = monthOfYear + 1;
                        mYear = year;
                        showTimePicker(editText);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showTimePicker(EditText editText) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        editText.setText(mDay + "/" + mMonth + "/" + mYear + " " + mHour + ":" + mMinute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private Long formatTime(String date) {
        long millis = 0;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            Date d = f.parse(date);
            millis = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }
}