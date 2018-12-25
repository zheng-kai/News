package com.example.a.news;


import com.example.a.news.Data.detailData;
import com.example.a.news.Data.TodayData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("latest")
    Call<TodayData> getTodayData ();
    @GET("before/{data}")
    Call<TodayData> getBeforeData(@Path("data") String data);
    @GET("{id}")
    Call<detailData> getDetailData(@Path("id") int id);
}
