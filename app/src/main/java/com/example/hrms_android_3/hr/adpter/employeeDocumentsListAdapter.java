package com.example.hrms_android_3.hr.adpter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_android_3.EmployeeDocumentListActivity;
import com.example.hrms_android_3.ImageViewActivity;
import com.example.hrms_android_3.PdfViewActivity;
import com.example.hrms_android_3.PdfWebviewActivity;
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
        View view = inflater.inflate(R.layout.employee_document_card, parent,false);
        return new employeeDocumentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull employeeDocumentsViewHolder holder, int position) {

            holder.docDescription.setText(data.get(position).getDescription());
            if(data.get(position).getExtension().equals("jpg")){
               holder.docType.setImageResource(R.drawable.jpg);
            }else{
                holder.docType.setImageResource(R.drawable.pdf);
            }

        holder.docType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(data.get(position).getExtension().equals("pdf")) {
                    Intent intent = new Intent(context, PdfWebviewActivity.class);
                    intent.putExtra("url", data.get(position).getUrl());
                    intent.putExtra("Title", data.get(position).getDescription());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if(data.get(position).getExtension().equals("jpg")) {
                    Intent intent = new Intent(context, ImageViewActivity.class);
                    intent.putExtra("url", data.get(position).getUrl());
                    intent.putExtra("Title", data.get(position).getDescription());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });


    }



    @Override
    public int getItemCount() {
        return data.size();
    }
}
