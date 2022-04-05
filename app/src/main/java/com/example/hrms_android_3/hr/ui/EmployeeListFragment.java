package com.example.hrms_android_3.hr.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrms_android_3.R;
import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.classes.RetrofitClient;
import com.example.hrms_android_3.databinding.FragmentEmployeeListBinding;
import com.example.hrms_android_3.hr.adpter.employeesAdapter;
import com.example.hrms_android_3.hr.models.Employee;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeListFragment extends Fragment {

    private FragmentEmployeeListBinding binding;
    private PreferenceHelper preferenceHelper;
    private ProgressBar progressBar;

    RecyclerView rcv;
    public employeesAdapter adapter;
    public ArrayList<Employee> holder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // It's important here
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_employee_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEmployeeListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        progressBar = (ProgressBar)binding.progressBar;
        rcv = (RecyclerView)binding.recViewEmployees;
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        preferenceHelper = new PreferenceHelper(getContext());
        String token = preferenceHelper.getToken();
        Call<ArrayList<Employee>> call = RetrofitClient.getInstance().getApi().getEmployees("Bearer "+token);
        call.enqueue(new Callback<ArrayList<Employee>>() {
            @Override
            public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                holder =new ArrayList<>();
                holder.addAll(response.body());
                adapter = new employeesAdapter(holder, getContext());
                rcv.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {

            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}