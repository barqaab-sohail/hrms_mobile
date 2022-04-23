package com.example.hrms_android_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.classes.RetrofitClient;
import com.example.hrms_android_3.hr.adpter.employeeDocumentsListAdapter;
import com.example.hrms_android_3.hr.adpter.employeesAdapter;
import com.example.hrms_android_3.hr.models.Employee;
import com.example.hrms_android_3.hr.models.EmployeeDocument;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDocumentListActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private PreferenceHelper preferenceHelper;
    public ArrayList<EmployeeDocument> holder;
    public employeeDocumentsListAdapter adapter;
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_document_list);
        setTitle(getIntent().getStringExtra("name")+", " +getIntent().getStringExtra("designation"));

        progressBar = (ProgressBar)findViewById(R.id.progress_bar_doc);
        rcv = (RecyclerView)findViewById(R.id.rec_view_employee_documents);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        preferenceHelper = new PreferenceHelper(getApplicationContext());
        String token = preferenceHelper.getToken();
        String employeeId = getIntent().getStringExtra("id");
        Call<ArrayList<EmployeeDocument>> call = RetrofitClient.getInstance().getApi().getEmployeeDocuments(token, employeeId);
        call.enqueue(new Callback<ArrayList<EmployeeDocument>>() {
            @Override
            public void onResponse(Call<ArrayList<EmployeeDocument>> call, Response<ArrayList<EmployeeDocument>> response) {

                holder =new ArrayList<>();
                holder.addAll(response.body());
                adapter = new employeeDocumentsListAdapter(holder, getApplicationContext());
                //Toast.makeText(getApplicationContext(), holder.toString(), Toast.LENGTH_SHORT).show();
                rcv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<EmployeeDocument>> call, Throwable t) {

            }
        });


    }
}