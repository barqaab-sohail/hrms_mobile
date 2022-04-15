package com.example.hrms_android_3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EmployeeDocumentsActivity extends AppCompatActivity {

    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_documents);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        name=findViewById(R.id.name);
        name.setText(getIntent().getStringExtra("name"));

        setTitle(getIntent().getStringExtra("name"));

    }
}