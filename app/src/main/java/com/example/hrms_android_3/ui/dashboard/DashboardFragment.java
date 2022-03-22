package com.example.hrms_android_3.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.databinding.FragmentDashboardBinding;



public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private PreferenceHelper preferenceHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textDashboard;
        preferenceHelper = new PreferenceHelper(getContext());
        String userName = preferenceHelper.getName();
        textView.setText(userName + " Welcome to HRMS");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}