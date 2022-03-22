package com.example.hrms_android_3.ui.addasset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hrms_android_3.databinding.FragmentAddAssetBinding;


public class AddAssetFragment extends Fragment {

    private FragmentAddAssetBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddAssetBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            final TextView textView = binding.textAddAsset;
            textView.setText(" Welcome to HRMS");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}