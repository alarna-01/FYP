package com.example.calenderapp.ui.focus_keeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calenderapp.databinding.FragmentFocusKeeperBinding;

public class FocusKeeperFragment extends Fragment {

    private FragmentFocusKeeperBinding binding;
    int repeat = 0;
    CountDownTimer startBreakTimer;
    CountDownTimer startStudyTimer;
    boolean isBreakRunning = false;
    boolean isStudyRunning = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFocusKeeperBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Create a Timer object
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etStudyTime.getText().toString().isEmpty() || binding.etBreakTime.getText().toString().isEmpty()
                || binding.etRepeatTime.getText().toString().isEmpty()){
                    Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    int studyTime = Integer.parseInt(binding.etStudyTime.getText().toString());
                    int breakTime = Integer.parseInt(binding.etBreakTime.getText().toString());
                    int repeat = Integer.parseInt(binding.etRepeatTime.getText().toString());
                    FocusKeeperFragment.this.repeat = repeat;
                    startStudy(studyTime, breakTime, 1);
                }
            }
        });

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void startStudy(int studyTime, int breaks, int cycle) {
        if (repeat < cycle) {
            Toast.makeText(requireContext(), "Study Completed", Toast.LENGTH_SHORT).show();
        } else {
            // Define the total time in milliseconds
            long totalTimeInMillis = studyTime * 60 * 1000; // 5 minutes
            long breakTime = breaks * 60 * 1000;
            long minutes = breakTime / 1000 / 60;
            long seconds = breakTime / 1000 % 60;
            binding.tvBreak.setText("Break Time "+String.format("%02d:%02d", minutes, seconds));
            binding.tvIntervals.setText("Repeat Count " + repeat + " Current Count " + cycle);


           // Define a CountDownTimer object
            startStudyTimer = new CountDownTimer(totalTimeInMillis, 1000) {
                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void onTick(long millisUntilFinished) {
                    // Update the TextView with the remaining time
                    long minutes = millisUntilFinished / 1000 / 60;
                    long seconds = millisUntilFinished / 1000 % 60;
                    binding.tvCCMinutes.setText("Study Time " + String.format("%02d:%02d", minutes, seconds));
                    isStudyRunning = true;
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onFinish() {
                    //  the timer finished event
                    startBreak(studyTime, breaks, cycle + 1);
                    binding.tvCCMinutes.setText("Study Time finished");
                    isStudyRunning = false;
                }
            };

            // Start the timer
            startStudyTimer.start();
        }
    }

    public void startBreak(int studyCounter, int breaks, int cycle)  {
        long totalTimeInMillis = breaks * 60 * 1000; // 5 minutes

        // Define a CountDownTimer object
        startBreakTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the TextView with the remaining time
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = millisUntilFinished / 1000 % 60;
                binding.tvBreak.setText("Break Time " + String.format("%02d:%02d", minutes, seconds));
                binding.tvCCMinutes.setText("Study time will Restart after Break");
                isBreakRunning = true;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                //  the timer finished event
                binding.tvCCMinutes.setText("Break finished");
                startStudy(studyCounter, breaks, cycle);
                isBreakRunning = false;
            }
        };

        startBreakTimer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isStudyRunning == true){
            startStudyTimer.cancel();
        }

        if (isBreakRunning == true){
            startBreakTimer.cancel();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

