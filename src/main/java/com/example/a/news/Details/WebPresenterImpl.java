package com.example.a.news.Details;

import com.example.a.news.Data.detailData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebPresenterImpl implements WebContract.WebPresenter {
    private WebModel model = new WebModel();
    private WebContract.WebUIView UIView;
    private int id;
    WebPresenterImpl(WebContract.WebUIView UIView) {
        this.UIView = UIView;
    }

    @Override
    public void getData() {
        id = UIView.getID();
        Call<detailData> call = model.getDetailData(id);
        call.enqueue(new Callback<detailData>() {
            @Override
            public void onResponse(Call<detailData> call, Response<detailData> response) {
                detailData data = response.body();
                if(data.getBody() == null){
                    UIView.onNUll();
                }
                else {
                    UIView.updateWeb(data);
                }
            }

            @Override
            public void onFailure(Call<detailData> call, Throwable t) {
                UIView.onError();
            }
        });
    }

}
