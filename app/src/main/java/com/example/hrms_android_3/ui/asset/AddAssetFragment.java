package com.example.hrms_android_3.ui.asset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import com.example.hrms_android_3.R;
import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.classes.RetrofitClient;
import com.example.hrms_android_3.databinding.FragmentAddAssetBinding;
import com.example.hrms_android_3.model.asset.AssetClassModel;
import com.example.hrms_android_3.model.asset.AssetSubClassModel;
import com.example.hrms_android_3.model.asset.ClientClassModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

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
    Spinner assetClassSpinner, assetSubClassSpinner, assetOwnershipSpinner;
    ArrayList<String> assetClassList = new ArrayList<>();
    ArrayList<String> assetSubClassList = new ArrayList<>();
    ArrayList<String> clientList = new ArrayList<>();
    String assetClassId, assetSubClassId, clientId, token;
    boolean isAllFieldsChecked = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddAssetBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            assetClassSpinner=binding.asclass;
            assetSubClassSpinner = binding.assubclass;
            assetOwnershipSpinner = binding.ownership;
            progressBar = binding.progress;
            progressBar.setVisibility(View.VISIBLE);
            etdescription = binding.description;
            imageView = binding.imageView;
            selectImage = binding.selectimage;
            submit = binding.submitAsset;
            preferenceHelper = new PreferenceHelper(getContext());
            token = preferenceHelper.getToken();

            //token = "Bearer 303|0iRuCjdAoOjKKZ1WZOSDA7UmrVXOIW4i9gF7Am54";

            classAndSubClassSpinnger();
            ownershipSpinner();

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
                    uploadImage();
                }

            }
        });



        return root;
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

                            if(adapterView.getId() == R.id.ownership && clientClassModel.getName() != "Please Select Asset class"){
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
                    AssetClassModel item = new AssetClassModel("0", "Please Select Asset class");
                    assetClass.add(0,item);

                    final ArrayAdapter assetClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,assetClass);
                    assetClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    assetClassSpinner.setAdapter(assetClassAdapter);
                    assetClassSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            AssetClassModel assetClassModel = (AssetClassModel) assetClassSpinner.getSelectedItem();
                            assetClassId = assetClassModel.getId();

                            if(assetClassModel.getId()=="0"){
                                assetSubClassList.clear();
                                subClassSpinner();
                            }

                            if(adapterView.getId() == R.id.asclass && assetClassModel.getName() != "Please Select Asset class"){
                                assetSubClassList.clear();
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
                    AssetSubClassModel item = new AssetSubClassModel("0","Please Select Asset Sub Class");
                    assetSubClass.add(0,item);
                    

                    final ArrayAdapter assetSubClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, assetSubClass);
                    assetSubClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    assetSubClassSpinner.setAdapter(assetSubClassAdapter);
                    assetSubClassSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            AssetSubClassModel assetSubClassModel = (AssetSubClassModel) assetSubClassSpinner.getSelectedItem();

                            if(adapterView.getId() == R.id.assubclass && assetSubClassModel.getName() != "Please Select Asset Sub Class") {
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

    private void uploadImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        final String description = etdescription.getText().toString().trim();

        Call<String> call = RetrofitClient.getInstance().getApi().createAsset("Bearer "+token,assetSubClassId,description,clientId,encodeImage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String data = response.body();
                Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                assetClassSpinner.setSelection(0);
                assetSubClassSpinner.setSelection(0);
                assetOwnershipSpinner.setSelection(0);
                etdescription.setText("");
                imageView.setImageURI(null);
                etdescription.setError(null);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String data = t.toString();
                Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private boolean CheckAllFields() {

        TextView errorTextAssetClass = (TextView)assetClassSpinner.getSelectedView();
        TextView errorTextAssetSubClass = (TextView)assetSubClassSpinner.getSelectedView();
        TextView errorTextOwnership = (TextView)assetOwnershipSpinner.getSelectedView();

        if (errorTextAssetClass.getText() == "Please Select Asset class" || errorTextAssetClass.getText() == null){
            Toast.makeText(getContext(),"Asset Class is required",Toast.LENGTH_LONG).show();
            return false;
        }
        if (etdescription.length() == 0) {
            etdescription.setError("Description is required");
            return false;
        }
        if (errorTextAssetSubClass.getText() == "Please Select Asset Sub Class" || errorTextAssetSubClass.getText() == null){
            Toast.makeText(getContext(),"Asset Sub Class is required",Toast.LENGTH_LONG).show();
            return false;
        }

        if (errorTextOwnership.getText() == "Please Select Ownership" || errorTextOwnership.getText() == null){
            Toast.makeText(getContext(),"Ownership ie required",Toast.LENGTH_LONG).show();
            return false;
        }

        if(imageView.getDrawable()==null){
            Toast.makeText(getContext(),"Asset Image is required",Toast.LENGTH_LONG).show();
            return false;
        }






        // after all validation return true.
        return true;
    }
}