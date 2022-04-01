package com.example.hrms_android_3.api;

import com.example.hrms_android_3.asset.models.AssetEmployeeModel;
import com.example.hrms_android_3.asset.models.AssetOfficeModel;
import com.example.hrms_android_3.login.LoginModel;
import com.example.hrms_android_3.asset.models.AssetClassModel;
import com.example.hrms_android_3.asset.models.AssetModel;
import com.example.hrms_android_3.asset.models.AssetSubClassModel;
import com.example.hrms_android_3.asset.models.ClientClassModel;
import com.example.hrms_android_3.dashboard.models.AgeChart;
import com.example.hrms_android_3.hr.models.Employee;

import java.util.ArrayList;
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

    @FormUrlEncoded
    @POST("user/logout")
    Call<Object> userLogout(@Header("Authorization") String token, @Field("email") String email);


    //Asset API
    @GET("asset/classes")
    Call<List<AssetClassModel>> getAssetClasses(@Header("Authorization") String token);

    @GET("asset/employees")
    Call<List<AssetEmployeeModel>> getAssetEmployees(@Header("Authorization") String token);

    @GET("asset/offices")
    Call<List<AssetOfficeModel>> getAssetOffices(@Header("Authorization") String token);

    @GET("asset/subClasses/{id}")
    Call<List<AssetSubClassModel>> getAssetSubClasses(@Header("Authorization") String token,
                                                    @Path("id") String id);

    @FormUrlEncoded
    @POST("asset/store")
    Call<String>createAsset(@Header("Authorization") String token,
                            @Field("as_sub_class_id") String as_sub_class_id,
                            @Field("description") String description,
                            @Field("client_id") String client_id,
                            @Field("office_id") String office_id,
                            @Field("employee_id") String employee_id,
                            @Field("En_Image") String encodedImage);

    @GET("asset/asset/{id}")
    Call<AssetModel>getAsset(@Header("Authorization") String token, @Path("id") String assetCode);

    @GET("clients")
    Call<List<ClientClassModel>> getClients(@Header("Authorization") String token);


    //Charts
    @GET("ageChart")
    Call<List<AgeChart>> getAgeChart(@Header("Authorization") String token);

    //HR
    @GET("hr/employees")
    Call<ArrayList<Employee>> getEmployees(@Header("Authorization") String token);


}