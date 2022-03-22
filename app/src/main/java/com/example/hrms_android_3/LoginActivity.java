package com.example.hrms_android_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.classes.RetrofitClient;
import com.example.hrms_android_3.model.LoginModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnlogin;
    private PreferenceHelper preferenceHelper;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.loadingProgress);
        preferenceHelper = new PreferenceHelper(this);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.buttonLogin);
        btnlogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void loginUser() {
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        Call<LoginModel> call = RetrofitClient.getInstance().getApi().userLogin(email, password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful() && response.code()==200) {
                    LoginModel loginModel = response.body();
                    if (response.body() != null && loginModel.isStatus()) {
                        Log.i("onSuccess", loginModel.getName());
                        parseLoginData(loginModel);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Email or Password is Incorrected",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                String data = t.toString();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();

            }
        });
    }

    private void parseLoginData(LoginModel response) {

            if (response.isStatus()) {
                saveInfo(response);
                Intent intent = new Intent(LoginActivity.this,NavActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
    }

    private void saveInfo(LoginModel response) {
        preferenceHelper.putIsLogin(true);
            if (response.isStatus()) {
                preferenceHelper.putName(response.getName());
                preferenceHelper.putToken(response.getToken());
                preferenceHelper.putTokenType(response.getToken_type());
                preferenceHelper.putEmail(response.getEmail());
                preferenceHelper.putPictureUrl(response.getPictureUrl());
            }
    }


}