package com.example.hrms_android_1.api;

import com.example.hrms_android_1.model.AssetModal;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AssetInterface {
    String ASSETURL = "http://192.168.1.10/hrms/public/api/asset/";

    @GET("asset/{id}")
    Call<AssetModal>getasset(@Header("Authorization") String token, @Path("id") String assetCode);

    @FormUrlEncoded
    @POST("store")
    Call<String>createAsset(@Header("Authorization") String token,
                                @Field("as_sub_class_name") String as_sub_class_name,
                                @Field("description") String description,
                                @Field("En_Image") String encodedImage);





}
