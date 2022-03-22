package com.example.hrms_android_3.api;

import com.example.hrms_android_3.model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginModel> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );


    @Headers("Accept: application/json")
    @GET("employee")
    Call<String> getUser();

    @POST("logout")
    Call<String> userLogout(@Header("Authorization") String token);

}
