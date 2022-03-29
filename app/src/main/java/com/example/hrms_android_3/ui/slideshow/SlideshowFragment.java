package com.example.hrms_android_3.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hrms_android_3.R;
import com.example.hrms_android_3.databinding.FragmentSlideshowBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    TextInputLayout til_sub_class_asset;
    AutoCompleteTextView act_sub_class_asset;
    ArrayList<String> arrayList_subClassAsset;
    ArrayAdapter<String> arrayAdapter_subClassAsset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        til_sub_class_asset = binding.tilSubClassAsset;
        act_sub_class_asset=binding.actSubClassAsset;
        arrayList_subClassAsset = new ArrayList<>();
        arrayList_subClassAsset.add("one");
        arrayList_subClassAsset.add("tow");
        arrayList_subClassAsset.add("three");
        arrayList_subClassAsset.add("four");
        arrayList_subClassAsset.add("five");
        arrayList_subClassAsset.add("six");
        arrayList_subClassAsset.add("seven");

        arrayAdapter_subClassAsset  = new ArrayAdapter<>(getContext(),R.layout.spinner_entity,arrayList_subClassAsset);
        act_sub_class_asset.setAdapter(arrayAdapter_subClassAsset);
        act_sub_class_asset.setThreshold(1);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}