package com.example.a.demo_news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("latest")
    Call<MyData> getTodayData();
    @GET("before/{data}")
    Call<MyData> getBeforeData(@Path("data") String data);
    @GET("{id}")
    Call<detailData> getDetailData(@Path("id") String id);
}
