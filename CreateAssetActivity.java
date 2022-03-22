package com.example.hrms_android_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hrms_android_1.api.AssetInterface;
import com.example.hrms_android_1.classes.PreferenceHelper;
import com.example.hrms_android_1.classes.RetrofitAssetClass;
import com.example.hrms_android_1.classes.RetrofitAssetSubClass;
import com.example.hrms_android_1.classes.RetrofitClientAsset;
import com.example.hrms_android_1.model.asset.AssetClass;
import com.example.hrms_android_1.model.asset.AssetSubClass;
import com.example.hrms_android_1.classes.PreferenceHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CreateAssetActivity extends AppCompatActivity {

    private EditText etdescription;
    private ImageView imageView;
    private Button submit, selectImage;
    private PreferenceHelper preferenceHelper;
    private int IMG_REQUEST = 21;
    private Bitmap bitmap;
    Spinner asclass, assubclass;
    ArrayList<String> assetClassList = new ArrayList<>();
    ArrayList<String> assetSubClassList = new ArrayList<>();
    String assetClassName, assetSubClassName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        asclass =findViewById(R.id.asclass);
        assubclass =findViewById(R.id.assubclass);

        preferenceHelper = new PreferenceHelper(this);
        etdescription= (EditText)findViewById(R.id.description);
        imageView = (ImageView)findViewById(R.id.imageView);
        selectImage = (Button)findViewById(R.id.selectimage);
        submit = (Button)findViewById(R.id.submitAsset);
        String userToken = "Bearer "+ "161|urJndu8IJ9yeWDmyhS8i5KenSQM87JAynemr0OiC";
        //Call<List<AssetClass>> call = RetrofitAssetClass.getInstance().getApi().getAssetClass(userToken);
        Call<List<AssetClass>> call = RetrofitAssetClass.getInstance().getApi().getAssetClass(userToken);


        call.enqueue(new Callback<List<AssetClass>>() {
            @Override
            public void onResponse(Call<List<AssetClass>> call, Response<List<AssetClass>> response) {
                if (response.isSuccessful()) {
                    List<AssetClass>  assetClass = response.body();
                    assetClassList.add("");
                    for (int i=0; i<assetClass.size();i++){
                        assetClassList.add(assetClass.get(i).getName());
                    }

                    final ArrayAdapter assetClassAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,assetClassList);
                    assetClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    asclass.setAdapter(assetClassAdapter);


                    asclass.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                            assetClassName =  asclass.getSelectedItem().toString();
                            if(parent.getId() == R.id.asclass){
                                assetSubClassList.clear();
                                String className = parent.getSelectedItem().toString();
                                Call<List<AssetSubClass>> call = RetrofitAssetSubClass.getInstance().getApi().getAssetSubClass(userToken, className);
                                call.enqueue(new Callback<List<AssetSubClass>>() {
                                    @Override
                                    public void onResponse(Call<List<AssetSubClass>> call, Response<List<AssetSubClass>> response) {
                                        List<AssetSubClass>  assetSubClass = response.body();
                                        if(response.isSuccessful()) {
                                            assetSubClassList.add("");
                                            for (int i = 0; i < assetSubClass.size(); i++) {
                                                assetSubClassList.add(assetSubClass.get(i).getName());
                                            }
                                            final ArrayAdapter assetSubClassAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, assetSubClassList);
                                            assetSubClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            assubclass.setAdapter(assetSubClassAdapter);
                                            assubclass.setOnItemSelectedListener(new OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                    assetSubClassName =  assubclass.getSelectedItem().toString();
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<List<AssetSubClass>> call, Throwable t) {
                                            String data = t.toString();
                                            Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                            //Toast.makeText(getApplicationContext(), "testing", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AssetClass>> call, Throwable t) {
                String data = t.toString();
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
            }
        });





        selectImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
               // storeAsset();
            }
        });

    }

    private void uploadImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        String userToken = "Bearer "+ "161|urJndu8IJ9yeWDmyhS8i5KenSQM87JAynemr0OiC";

        final String description = etdescription.getText().toString().trim();

        Call<String> call = RetrofitClientAsset.getInstance().getApi().createAsset(userToken,assetSubClassName,description,encodeImage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String data = response.body();
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String data = t.toString();
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void storeAsset() {
        final String description = etdescription.getText().toString().trim();
        final String asSubClass = "1";

        Map<String, String> fields = new HashMap<>();
        fields.put("description", description);
        fields.put("asSubClass", asSubClass);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AssetInterface.ASSETURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        AssetInterface api = retrofit.create(AssetInterface.class);

        String userToken = "Bearer "+ "160|g1c7BnAsdQi0qwUzW0OEsSAXA01akZDZs91mJlu4";

//        Call<String> call = api.createAsset(userToken,asSubClass,description);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                String data = response.body();
//                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                String data = t.toString();
//                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
//            }
//        });



    }
}