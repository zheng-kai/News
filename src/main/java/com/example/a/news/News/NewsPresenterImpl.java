package com.example.a.news.News;

import android.support.annotation.NonNull;

import com.example.a.news.Data.TodayData;
import com.example.a.news.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenterImpl implements NewsContract.NewsPresenter {

    private NewsModel model = new NewsModel();
    private NewsContract.NewsUIView UIView;
    private String date = null;
    NewsPresenterImpl(NewsContract.NewsUIView UIView) {
        this.UIView = UIView;
    }

    @Override
    public void addData() {
        Call<TodayData> call;
        if(date == null){
            call = model.getTodayData();
        }else {
            call = model.getBeforeData(date);
        }
        call.enqueue(new Callback<TodayData>() {
            @Override
            public void onResponse(@NonNull Call<TodayData> call, @NonNull Response<TodayData> response) {
                TodayData data = response.body();
                assert data != null;
                if (data.getStories() == null) {
                    UIView.onNull();
                    L.d("TestStories:", "onNull");
                }
                else {
                    if(date == null){
                        UIView.updatePV(data.getTop_stories());
                    }
                    date = data.getDate();
                    UIView.updateRV(data.getStories(),date);
                    L.d("TestStories:", "update");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TodayData> call, @NonNull Throwable t) {
                UIView.onError();
                L.d("Stories:","onError");
            }
        });
    }

    @Override
    public void clear() {
        date = null;
    }
}
