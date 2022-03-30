package com.example.hrms_android_3.hr.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_android_3.R;

public class employeesViewHolder extends RecyclerView.ViewHolder {

    public ImageView empPicture;
    public TextView empName, empDesignation, empNo, empJoining, empCnic, empBirthDate, empMobile, empStatus;


    public employeesViewHolder(@NonNull View itemView) {
        super(itemView);
        empPicture = (ImageView)itemView.findViewById(R.id.emp_picture);
        empName = (TextView) itemView.findViewById(R.id.emp_name);
        empDesignation = (TextView)itemView.findViewById(R.id.emp_designation);
        empNo = (TextView)itemView.findViewById(R.id.emp_no);
        empJoining = (TextView)itemView.findViewById(R.id.emp_joining);
        empBirthDate = (TextView)itemView.findViewById(R.id.emp_birth_date);
        empCnic = (TextView)itemView.findViewById(R.id.emp_cnic);
        empMobile = (TextView)itemView.findViewById(R.id.emp_mobile);
        empStatus = (TextView)itemView.findViewById(R.id.emp_status);

    }
}
