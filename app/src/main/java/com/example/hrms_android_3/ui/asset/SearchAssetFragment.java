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

import com.example.hrms_android_3.databinding.FragmentSearchAssetBinding;


public class SearchAssetFragment extends Fragment {
    private FragmentSearchAssetBinding binding;



    public static EditText resultTextView;
    Button scan_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchAssetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        resultTextView = binding.result;
        scan_btn = binding.btnScan;
        scan_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ScanCodeActivity.class));
            }
        });


        // Inflate the layout for this fragment
        return root;
    }
}