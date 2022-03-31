package com.example.hrms_android_3.hr.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_android_3.R;
import com.example.hrms_android_3.hr.holder.employeesViewHolder;
import com.example.hrms_android_3.hr.models.Employee;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class employeesAdapter extends RecyclerView.Adapter<employeesViewHolder> implements Filterable {

    ArrayList<Employee> data;
    ArrayList<Employee> backup;
    Context context;

    public employeesAdapter(ArrayList<Employee> data, Context context) {

        this.data = data;
        this.context=context;
        backup=new ArrayList<>(data);
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

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter= new Filter() {
        @Override
        //background thread
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Employee> filtereddata=new ArrayList<>();

            if(keyword.toString().isEmpty())
                filtereddata.addAll(backup);
            else
            {
                for(Employee obj : backup)
                {
                    if(obj.toStringExceptPicture().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }

            FilterResults results=new FilterResults();
            results.values=filtereddata;
            return results;
        }

        @Override
        // main UI thread
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            data.clear();
            data.addAll((ArrayList<Employee>)results.values);
            notifyDataSetChanged();
        }
    };

//    Filter filter=new Filter() {
//        @Override
//        // background thread
//        protected FilterResults performFiltering(CharSequence keyword)
//        {
//            ArrayList<Employee> filtereddata=new ArrayList<>();
//
//            if(keyword.toString().isEmpty())
//                filtereddata.addAll(backup);
//            else
//            {
//                for(Employee obj : backup)
//                {
//                    if(obj.getFull_name().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
//                        filtereddata.add(obj);
//                }
//            }
//
//            FilterResults results=new FilterResults();
//            results.values=filtereddata;
//            return results;
//        }
//
//        @Override  // main UI thread
//        protected void publishResults(CharSequence constraint, FilterResults results)
//        {
//            data.clear();
//            data.addAll((ArrayList<Model>)results.values);
//            notifyDataSetChanged();
//        }
//    };

}
