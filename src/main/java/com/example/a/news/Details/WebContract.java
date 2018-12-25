package com.example.a.news.Details;

import com.example.a.news.Data.detailData;

public interface WebContract {
    interface WebUIView{
        void onError();
        void updateWeb(detailData detData);
        void onNUll();
        int getID();
    }
    interface WebPresenter{
        void getData();
    }
}
