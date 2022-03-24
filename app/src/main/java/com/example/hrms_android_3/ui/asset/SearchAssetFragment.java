package com.example.hrms_android_3.ui.asset;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hrms_android_3.LoginActivity;
import com.example.hrms_android_3.MainActivity2;
import com.example.hrms_android_3.NavActivity;
import com.example.hrms_android_3.databinding.FragmentSearchAssetBinding;
import com.example.hrms_android_3.ui.dashboard.DashboardFragment;


public class SearchAssetFragment extends Fragment {
    private FragmentSearchAssetBinding binding;


    public static EditText resultTextView;
    public static Button submit_btn;
    Button scan_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchAssetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        resultTextView = binding.result;
        submit_btn = binding.btnSubmit;

        scan_btn = binding.btnScan;
        scan_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ScanCodeActivity.class));
            }
        });

        submit_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });





        // Inflate the layout for this fragment
        return root;
    }
}