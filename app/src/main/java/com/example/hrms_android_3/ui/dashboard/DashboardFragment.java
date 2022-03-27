package com.example.hrms_android_3.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.classes.RetrofitClient;
import com.example.hrms_android_3.databinding.FragmentDashboardBinding;
import com.example.hrms_android_3.model.charts.AgeChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {
    public PieChart pieChart;
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
        preferenceHelper = new PreferenceHelper(getContext());
        String userToken = preferenceHelper.getToken();
                //"Bearer 305|o5gy2Y0r5x5shoFq7EQAfveA5c2KNrB8tDNRYwj6";
        Call<List<AgeChart>> call = RetrofitClient.getInstance().getApi().getAgeChart("Bearer "+userToken);
        call.enqueue(new Callback<List<AgeChart>>() {
            @Override
            public void onResponse(Call<List<AgeChart>> call, Response<List<AgeChart>> response) {

               List<AgeChart> data = response.body();
               drawChart(data);
            }

            @Override
            public void onFailure(Call<List<AgeChart>> call, Throwable t) {
                String data = t.toString();
                Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void drawChart( List<AgeChart> data) {
        ArrayList<PieEntry> records = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            records.add(new PieEntry(data.get(i).getValue(), data.get(i).getLabel()));
        }

        pieChart.setVisibility(View.VISIBLE);

        PieDataSet dataSet = new PieDataSet(records, "Pie Chart Report");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(22f);
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription();
        pieChart.setCenterText("Employee Agewise Report");
        pieChart.animate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}