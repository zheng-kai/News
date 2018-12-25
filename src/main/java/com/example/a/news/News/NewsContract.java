package com.example.a.news.News;


import com.example.a.news.Data.TodayData;
import com.example.a.news.Data.detailData;

import java.util.List;

public interface NewsContract {
    interface NewsUIView{
        void onError();
        void updateRV(List<TodayData.StoriesBean> list,String date);
        void updatePV(List<TodayData.TopStoriesBean> list);
        void refreshData();
        void onNull();

    }
    interface NewsPresenter{
        void addData();
        void clear();
    }
}
