package com.amkuds.app.utils;

import com.amkuds.app.model.BaseResponse;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @POST("user/login")
    Call<BaseResponse> postLogin(@Body JsonObject jsonRequest);

    @GET("logout")
    Call<BaseResponse> getLogout();

    @GET("karyawan/all")
    Call<BaseResponse> getListEmployee();

    @GET("karyawan/search")
    Call<BaseResponse> getSearch(@QueryMap Map<String, String> mapRequest);

    @GET("karyawan/single/{id}")
    Call<BaseResponse> getSingleList(@Path("id") int id);

    @POST("karyawan/add")
    Call<BaseResponse> postInputEmployee(@Body JsonObject jsonRequest);

    @POST("karyawan/edit")
    Call<BaseResponse> postEditEmployee(@Body JsonObject jsonRequest);
}
