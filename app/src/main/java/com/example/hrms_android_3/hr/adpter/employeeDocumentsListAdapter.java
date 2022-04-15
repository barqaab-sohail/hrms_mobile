package com.example.hrms_android_3.hr.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_android_3.R;
import com.example.hrms_android_3.hr.holder.employeeDocumentsViewHolder;
import com.example.hrms_android_3.hr.holder.employeesViewHolder;
import com.example.hrms_android_3.hr.models.Employee;
import com.example.hrms_android_3.hr.models.EmployeeDocument;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class employeeDocumentsListAdapter extends RecyclerView.Adapter<employeeDocumentsViewHolder> {


    ArrayList<EmployeeDocument> data;
    ArrayList<EmployeeDocument> backup;
    Context context;

    public employeeDocumentsListAdapter(ArrayList<EmployeeDocument> data, Context context) {
        this.data = data;
        this.context=context;
        backup=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public employeeDocumentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.employees_card, parent,false);
        return new employeeDocumentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull employeeDocumentsViewHolder holder, int position) {
            holder.docDescription.setText(data.get(position).getDescription());
            if(data.get(position).getDocType()=="pdf"){

            }else{

            }
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
}
