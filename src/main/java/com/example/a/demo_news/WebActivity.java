package com.example.a.demo_news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebActivity extends AppCompatActivity {
//    String baseUrl = "http://dayily.zhihu.com/story/";
    String baseUrl = "http://news-at.zhihu.com/api/4/news/";
    String body;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        final WebView webView = findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setSupportZoom(true);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        Service service = retrofit.create(Service.class);
        Call<detailData> call = service.getDetailData(id);
        call.enqueue(new Callback<detailData>() {
            @Override
            public void onResponse(Call<detailData> call, Response<detailData> response) {
                detailData data = response.body();
                assert data != null;
                body = data.getBody();
                body = body.replace("<img", "<img style='max-width:100%;height:auto;'");
                webView.loadDataWithBaseURL(null, "<html><body>" + body + "</body></html", "text/html", "utf-8", null);
                L.d("web:", "get");
            }

            @Override
            public void onFailure(Call<detailData> call, Throwable t) {
                L.d("Web:", "fail...");
            }
        });

//        webView.loadUrl(baseUrl + String.valueOf(id));

    }
}

