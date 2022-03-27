package com.example.hrms_android_3.classes;

import com.example.hrms_android_3.api.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.10.3/hrms/public/api/";
    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;



    private RetrofitClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (retrofitClient== null){
            retrofitClient = new RetrofitClient();
        }

        return retrofitClient;
    }

    public ApiInterface getApi (){
        return retrofit.create(ApiInterface.class);
    }




}
