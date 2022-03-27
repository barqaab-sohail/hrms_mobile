package com.example.hrms_android_3.ui.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.classes.RetrofitClient;
import com.example.hrms_android_3.databinding.FragmentSearchAssetBinding;
import com.example.hrms_android_3.model.asset.AssetModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchAssetFragment extends Fragment {
    private FragmentSearchAssetBinding binding;
    private static PreferenceHelper preferenceHelper;

    public static EditText resultTextView;
    public static TextView asset_description, asset_code, asset_ownership, asset_allocation;
    public static ImageView asset_image;
    public static Button submit_btn;
    Button scan_btn;
    private static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        binding = FragmentSearchAssetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        resultTextView = binding.result;
        asset_description = binding.assetDescription;
        asset_allocation = binding.assetAllocation;
        asset_code = binding.assetCode;
        asset_ownership = binding.assetOwnership;
        asset_image = binding.assetImg;
        preferenceHelper = new PreferenceHelper(getContext());
        submit_btn = binding.btnSubmit;

        scan_btn = binding.btnScan;
        scan_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ScanCodeActivity.class));
            }
        });

        submit_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String assetCode = resultTextView.getText().toString().trim();
                assetResult(assetCode);

            }
        });


        // Inflate the layout for this fragment
        return root;
    }

    public static void assetResult(String assetCode){

        String token = preferenceHelper.getToken();
        Call<AssetModel> call = RetrofitClient.getInstance().getApi().getAsset("Bearer " +token, assetCode);
        call.enqueue(new Callback<AssetModel>() {
            @Override
            public void onResponse(Call<AssetModel> call, Response<AssetModel> response) {
                if(response.isSuccessful()) {
                    AssetModel data = response.body();
                    asset_description.setText("Description: " + data.getDescription());
                    asset_ownership.setText("Ownership: " + data.getOwnsership());
                    if(data.getLocation()==null){
                        asset_allocation.setText("Allocation to: " + data.getAllocation());
                    }else{
                        asset_allocation.setText("Location : " + data.getLocation());
                    }

                    asset_code.setText("Asset Code: " + data.getAsset_code());
                    Picasso.get().load(data.getAsset_image()).resize(150, 150).into(asset_image);
                }else{
                    Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AssetModel> call, Throwable t) {

            }
        });

    }
}