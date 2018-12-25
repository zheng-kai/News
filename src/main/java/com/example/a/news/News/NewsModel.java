package com.example.a.news.News;


import com.example.a.news.Data.detailData;
import com.example.a.news.Data.TodayData;
import com.example.a.news.RetrofitService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsModel {
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://news-at.zhihu.com/api/4/news/")
            .build();
    private RetrofitService service = retrofit.create(RetrofitService.class);

    public Call<TodayData> getTodayData() {
        return service.getTodayData();
    }
    public Call<TodayData> getBeforeData(String date){
        return service.getBeforeData(date);
    }
}
