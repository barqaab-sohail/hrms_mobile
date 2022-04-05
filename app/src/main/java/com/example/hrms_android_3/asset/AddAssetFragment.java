package com.example.hrms_android_3.asset;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hrms_android_3.R;
import com.example.hrms_android_3.asset.models.AssetEmployeeModel;
import com.example.hrms_android_3.asset.models.AssetOfficeModel;
import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.classes.RetrofitClient;
import com.example.hrms_android_3.databinding.FragmentAddAssetBinding;
import com.example.hrms_android_3.asset.models.AssetClassModel;
import com.example.hrms_android_3.asset.models.AssetSubClassModel;
import com.example.hrms_android_3.asset.models.ClientClassModel;
import com.example.hrms_android_3.hr.models.Employee;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAssetFragment extends Fragment {

    private FragmentAddAssetBinding binding;

    private EditText etdescription;
    private ImageView imageView;
    private Button submit, selectImage;
    private PreferenceHelper preferenceHelper;
    private int IMG_REQUEST = 21;
    private Bitmap bitmap;
    private ProgressBar progressBar;
    SearchableSpinner assetClassSpinner, assetSubClassSpinner, assetOwnershipSpinner, employeeSpinner, officeSpinner;

    private String assetClassId, assetSubClassId, clientId, employeeId, officeId, token;
    boolean isAllFieldsChecked = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAddAssetBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            assetClassSpinner=binding.asclass;
            assetSubClassSpinner = binding.assubclass;
            assetOwnershipSpinner = binding.ownership;
            employeeSpinner = binding.employee;
            officeSpinner = binding.office;
            progressBar = binding.progress;
            progressBar.setVisibility(View.VISIBLE);
            etdescription = binding.description;

            imageView = binding.imageView;
            selectImage = binding.selectimage;
            submit = binding.submitAsset;
            preferenceHelper = new PreferenceHelper(getContext());
            token = preferenceHelper.getToken();

            //token = "58|zbb1kSHnHtCeH89fY05kmq3DYF61zjAseM1ldyjA";
            classAndSubClassSpinnger();
            ownershipSpinner();
            officeSpinner();
            employeeSpinner();

        selectImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddAssetFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    progressBar.setVisibility(View.VISIBLE);
                    saveAsset();
                }

            }
        });



        return root;
    }

    private void employeeSpinner() {
        Call<List<AssetEmployeeModel>> call = RetrofitClient.getInstance().getApi().getAssetEmployees("Bearer "+token);
        call.enqueue(new Callback<List<AssetEmployeeModel>>() {
            @Override
            public void onResponse(Call<List<AssetEmployeeModel>> call, Response<List<AssetEmployeeModel>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    List<AssetEmployeeModel>  employees = response.body();
                    AssetEmployeeModel item = new AssetEmployeeModel("0","Please Select Employee", "");
                    employees.add(0,item);

                    final ArrayAdapter employeeClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,employees);
                    employeeClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    employeeSpinner.setAdapter(employeeClassAdapter);
                    employeeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            AssetEmployeeModel assetEmployeeModel = (AssetEmployeeModel) employeeSpinner.getSelectedItem();

                            if(adapterView.getId() == R.id.employee){
                                employeeId = assetEmployeeModel.getId();

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<AssetEmployeeModel>> call, Throwable t) {

            }
        });
    }

    private void officeSpinner(){
        Call<List<AssetOfficeModel>> call = RetrofitClient.getInstance().getApi().getAssetOffices("Bearer "+token);
        call.enqueue(new Callback<List<AssetOfficeModel>>() {
            @Override
            public void onResponse(Call<List<AssetOfficeModel>> call, Response<List<AssetOfficeModel>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    List<AssetOfficeModel>  offices = response.body();
                    AssetOfficeModel item = new AssetOfficeModel("0","Please Select Office");
                        offices.add(0,item);

                    final ArrayAdapter officeClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,offices);
                    officeClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    officeSpinner.setAdapter(officeClassAdapter);
                    officeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            AssetOfficeModel assetOfficeModel = (AssetOfficeModel) officeSpinner.getSelectedItem();

                            if(adapterView.getId() == R.id.office ){
                                officeId = assetOfficeModel.getId();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<AssetOfficeModel>> call, Throwable t) {

            }
        });
    }



    private void ownershipSpinner() {
        Call<List<ClientClassModel>> callClients = RetrofitClient.getInstance().getApi().getClients("Bearer "+token);
        callClients.enqueue(new Callback<List<ClientClassModel>>() {
            @Override
            public void onResponse(Call<List<ClientClassModel>> call, Response<List<ClientClassModel>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    List<ClientClassModel>  clients = response.body();

                    ClientClassModel item = new ClientClassModel("0", "Please Select Ownership");
                        clients.add(0,item);


                    final ArrayAdapter clientClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,clients);
                    clientClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    assetOwnershipSpinner.setAdapter(clientClassAdapter);
                    assetOwnershipSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ClientClassModel clientClassModel = (ClientClassModel) assetOwnershipSpinner.getSelectedItem();

                            if(adapterView.getId() == R.id.ownership){
                                clientId = clientClassModel.getId();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<ClientClassModel>> call, Throwable t) {

            }
        });
    }

    private void classAndSubClassSpinnger(){
        Call<List<AssetClassModel>> call = RetrofitClient.getInstance().getApi().getAssetClasses("Bearer "+token);
        call.enqueue(new Callback<List<AssetClassModel>>() {
            @Override
            public void onResponse(Call<List<AssetClassModel>> call, Response<List<AssetClassModel>> response) {
                if (response.isSuccessful()) {

                    List<AssetClassModel>  assetClass = response.body();

                    AssetClassModel item = new AssetClassModel("0","Please Select Asset class");
                    assetClass.add(0,item);


                    final ArrayAdapter assetClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,assetClass);
                    assetClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    assetClassSpinner.setAdapter(assetClassAdapter);
                    assetClassSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            AssetClassModel assetClassModel = (AssetClassModel) assetClassSpinner.getSelectedItem();
                            assetClassId = assetClassModel.getId();
                            if(adapterView.getId() == R.id.asclass){
                                subClassSpinner();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<AssetClassModel>> call, Throwable t) {
                String data = t.toString();
                Toast.makeText(getContext(),data,Toast.LENGTH_LONG).show();

            }
        });
    }

    private void subClassSpinner() {
        Call<List<AssetSubClassModel>> call = RetrofitClient.getInstance().getApi().getAssetSubClasses("Bearer "+token, assetClassId);
        call.enqueue(new Callback<List<AssetSubClassModel>>() {
            @Override
            public void onResponse(Call<List<AssetSubClassModel>> call, Response<List<AssetSubClassModel>> response) {


                if(response.isSuccessful()) {
                    List<AssetSubClassModel>  assetSubClass = response.body();
                    AssetSubClassModel item = new AssetSubClassModel("0", "Please Select Asset Sub Class");
                    assetSubClass.add(0,item);


                    final ArrayAdapter assetSubClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, assetSubClass);
                    assetSubClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    assetSubClassSpinner.setAdapter(assetSubClassAdapter);
                    assetSubClassSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            AssetSubClassModel assetSubClassModel = (AssetSubClassModel) assetSubClassSpinner.getSelectedItem();

                            if(adapterView.getId() == R.id.assubclass) {
                                assetSubClassId = assetSubClassModel.getId();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<AssetSubClassModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        if (uri != null) {
            imageView.setImageURI(uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveAsset() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        final String description = etdescription.getText().toString().trim();
        //String data = "SubClass: "+assetSubClassId+" - Description: "+description+" - ClientId"+clientId+ " - OfficeId"+ officeId +"  - employeeId" +employeeId;
        //Toast.makeText(getContext(),data,Toast.LENGTH_LONG).show();
        Call<String> call = RetrofitClient.getInstance().getApi().createAsset("Bearer "+token,assetSubClassId,description,clientId,officeId, employeeId,encodeImage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    String data = response.body();
                    Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                    etdescription.setText("");
                    imageView.setImageURI(null);
                    etdescription.setError(null);
                    officeSpinner.setSelection(0);
                    assetOwnershipSpinner.setSelection(0);
                    assetClassSpinner.setSelection(0);
                    assetSubClassSpinner.setSelection(0);
                    employeeSpinner.setSelection(0);
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    Log.d("LOG","RESPONSE ==="+response.raw().toString());
                    Toast.makeText(getContext(),"Aseet is not saved",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String data = t.toString();
                Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private boolean CheckAllFields() {


        if (assetSubClassId == null || assetSubClassId == "0"){
            Toast.makeText(getContext(),"Asset Sub Class is Required",Toast.LENGTH_LONG).show();
            return false;
        }
        if (etdescription.length() == 0) {
            etdescription.setError("Description is Required");
            return false;
        }
        if (clientId == null || clientId =="0"){
            Toast.makeText(getContext(),"Ownership is Required",Toast.LENGTH_LONG).show();
            return false;
        }
        if (officeId == null && employeeId == null) {
            Toast.makeText(getContext(), "Office Or Employee is Required", Toast.LENGTH_LONG).show();
            return false;
        }
        if (officeId != null && employeeId != null && employeeId!="0" && officeId!="0") {
            Toast.makeText(getContext(), "Only One Required Office Or Employee", Toast.LENGTH_LONG).show();
            return false;
        }

        if (officeId == "0" && employeeId=="0") {
            Toast.makeText(getContext(), "Office Or Employee is Required", Toast.LENGTH_LONG).show();
            return false;
        }


        if(imageView.getDrawable()==null){
            Toast.makeText(getContext(),"Asset Image is Required",Toast.LENGTH_LONG).show();
            return false;
        }



        // after all validation return true.
        return true;
    }


}