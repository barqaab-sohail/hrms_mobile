package com.example.hrms_android_3.hr.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_android_3.R;
import com.example.hrms_android_3.hr.holder.employeesViewHolder;
import com.example.hrms_android_3.model.hr.Employee;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class employeesAdapter extends RecyclerView.Adapter<employeesViewHolder> {

    ArrayList<Employee> data;

    public employeesAdapter(ArrayList<Employee> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public employeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.employees_card, parent,false);
        return new employeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull employeesViewHolder holder, int position) {
        Picasso.get().load(data.get(position).getPicture())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(holder.empPicture);
        holder.empName.setText(data.get(position).getFull_name());
        holder.empDesignation.setText(data.get(position).getDesignation());
        holder.empNo.setText("Employee No: "+ data.get(position).getEmployee_no());
        holder.empCnic.setText("CNIC: "+ data.get(position).getCnic());
        holder.empJoining.setText("Joining Date: "+ data.get(position).getDate_of_joining());
        holder.empBirthDate.setText("Date of Birth: "+ data.get(position).getDate_of_birth());
        holder.empMobile.setText("Mobile: "+ data.get(position).getMobile());
        holder.empStatus.setText("Status: "+ data.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
