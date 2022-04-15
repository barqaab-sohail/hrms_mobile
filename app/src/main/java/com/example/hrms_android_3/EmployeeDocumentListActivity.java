package com.example.hrms_android_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

public class EmployeeDocumentListActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_document_list);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar_doc);
        rcv = (RecyclerView)findViewById(R.id.rec_view_employee_documents);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }
}