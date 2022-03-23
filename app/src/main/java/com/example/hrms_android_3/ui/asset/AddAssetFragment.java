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
    Spinner assetClassSpinner, assetSubClassSpinner;
    ArrayList<String> assetClassList = new ArrayList<>();
    ArrayList<String> assetSubClassList = new ArrayList<>();
    String assetClassName, assetSubClassName, token;
    boolean isAllFieldsChecked = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddAssetBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            assetClassSpinner=binding.asclass;
            assetSubClassSpinner = binding.assubclass;

            etdescription = binding.description;
            imageView = binding.imageView;
            selectImage = binding.selectimage;
            submit = binding.submitAsset;
            preferenceHelper = new PreferenceHelper(getContext());
            //token = "Bearer "+preferenceHelper.getToken();
            token = "Bearer 295|zT68d6qoE0DomczpNqzR3rvaARw9F2f6fJwndHJK";
            Call<List<AssetClassModel>> call = RetrofitClient.getInstance().getApi().getAssetClasses(token);
            call.enqueue(new Callback<List<AssetClassModel>>() {
                @Override
                public void onResponse(Call<List<AssetClassModel>> call, Response<List<AssetClassModel>> response) {
                    if (response.isSuccessful()) {

                        List<AssetClassModel>  assetClass = response.body();
                        assetClassList.add("Please Select Asset class");
                        for (int i=0; i<assetClass.size();i++){
                            assetClassList.add(assetClass.get(i).getName());
                        }
                        final ArrayAdapter assetClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,assetClassList);
                        assetClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        assetClassSpinner.setAdapter(assetClassAdapter);
                        assetClassSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                assetClassName =  assetClassSpinner.getSelectedItem().toString();
                                if(adapterView.getId() == R.id.asclass && assetClassName != "Please Select Asset class"){
                                    assetSubClassList.clear();
                                    String className = adapterView.getSelectedItem().toString();
                                    Call<List<AssetSubClassModel>> call = RetrofitClient.getInstance().getApi().getAssetSubClasses(token, className);
                                    call.enqueue(new Callback<List<AssetSubClassModel>>() {
                                        @Override
                                        public void onResponse(Call<List<AssetSubClassModel>> call, Response<List<AssetSubClassModel>> response) {
                                            List<AssetSubClassModel>  assetSubClass = response.body();
                                            if(response.isSuccessful()) {
                                                assetSubClassList.add("Please Select Sub Class");
                                                for (int i = 0; i < assetSubClass.size(); i++) {
                                                    assetSubClassList.add(assetSubClass.get(i).getName());
                                                }
                                                final ArrayAdapter assetSubClassAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, assetSubClassList);
                                                assetSubClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                assetSubClassSpinner.setAdapter(assetSubClassAdapter);
                                                assetSubClassSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                        assetSubClassName =  assetSubClassSpinner.getSelectedItem().toString();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imageView.setImageURI(uri);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void uploadImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        final String description = etdescription.getText().toString().trim();
        Call<String> call = RetrofitClient.getInstance().getApi().createAsset(token,assetSubClassName,description,encodeImage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String data = response.body();
                Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                assetClassSpinner.setSelection(0);
                assetSubClassSpinner.setSelection(0);
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

        if (errorTextAssetClass.getText() == "Please Select Asset class" || errorTextAssetClass.getText() == null){
            etdescription.setError("Asset Class is required");
            return false;
        }
        if (etdescription.length() == 0) {
            etdescription.setError("Description is required");
            return false;
        }
        if (errorTextAssetSubClass.getText() == "Please Select Sub Class" || errorTextAssetSubClass.getText() == null){
            etdescription.setError("Asset Sub Class is required");
            return false;
        }

        if(imageView.getDrawable()==null){
            etdescription.setError("Asset Image is required");
            return false;
        }






        // after all validation return true.
        return true;
    }
}