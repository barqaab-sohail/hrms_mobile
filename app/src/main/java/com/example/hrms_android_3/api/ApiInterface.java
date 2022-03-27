package com.example.hrms_android_3.api;

import com.example.hrms_android_3.model.LoginModel;
import com.example.hrms_android_3.model.asset.AssetClassModel;
import com.example.hrms_android_3.model.asset.AssetModel;
import com.example.hrms_android_3.model.asset.AssetSubClassModel;
import com.example.hrms_android_3.model.asset.ClientClassModel;
import com.example.hrms_android_3.model.charts.AgeChart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginModel> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );


    @Headers("Accept: application/json")
    @GET("user/employee")
    Call<String> getUser();

    @POST("user/logout")
    Call<String> userLogout(@Header("Authorization") String token);


    //Asset API
    @GET("asset/classes")
    Call<List<AssetClassModel>> getAssetClasses(@Header("Authorization") String token);

    @GET("asset/subClasses/{id}")
    Call<List<AssetSubClassModel>> getAssetSubClasses(@Header("Authorization") String token,
                                                    @Path("id") String id);

    @FormUrlEncoded
    @POST("asset/store")
    Call<String>createAsset(@Header("Authorization") String token,
                            @Field("as_sub_class_id") String as_sub_class_id,
                            @Field("description") String description,
                            @Field("client_id") String client_id,
                            @Field("En_Image") String encodedImage);

    @GET("asset/asset/{id}")
    Call<AssetModel>getAsset(@Header("Authorization") String token, @Path("id") String assetCode);

    @GET("clients")
    Call<List<ClientClassModel>> getClients(@Header("Authorization") String token);


    //Charts
    @GET("ageChart")
    Call<List<AgeChart>> getAgeChart(@Header("Authorization") String token);


}