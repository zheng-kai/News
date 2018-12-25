package com.example.a.news.Details;

import com.example.a.news.Data.detailData;
import com.example.a.news.RetrofitService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class WebModel {
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://news-at.zhihu.com/api/4/news/")
            .build();
    private RetrofitService service = retrofit.create(RetrofitService.class);
    Call<detailData> getDetailData(int id){
        return service.getDetailData(id);
    }
}

