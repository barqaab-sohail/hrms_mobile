package com.example.hrms_android_3.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.databinding.FragmentDashboardBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
PieChart pieChart;
    private FragmentDashboardBinding binding;
    private PreferenceHelper preferenceHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        pieChart= binding.piechart;
        final TextView textView = binding.textDashboard;
        preferenceHelper = new PreferenceHelper(getContext());
        String userName = preferenceHelper.getName();
        textView.setText(userName + " Welcome to HRMS");
        getAgeChart();
        return root;
    }

    private void getAgeChart() {
        ArrayList<PieEntry> records = new ArrayList<>();
        records.add(new PieEntry(32, "first"));
        records.add(new PieEntry(12, "second"));
        records.add(new PieEntry(22, "third"));
        records.add(new PieEntry(34, "forth"));
        PieDataSet dataSet = new PieDataSet(records, "Pie Chart Report");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(22f);
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription();
        pieChart.setCenterText("It is Demo");
        pieChart.animate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}