package com.example.hrms_android_3.hr.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_android_3.R;

public class employeeDocumentsViewHolder extends RecyclerView.ViewHolder {
    public ImageView docType;
    public TextView docDescription;

    public employeeDocumentsViewHolder(@NonNull View itemView) {
        super(itemView);
        docType = (ImageView)itemView.findViewById(R.id.doc_extension);
        docDescription = (TextView) itemView.findViewById(R.id.emp_doc_description);
    }
}
