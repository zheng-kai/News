package com.example.a.news.Details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.a.news.Data.detailData;
import com.example.a.news.R;

import es.dmoral.toasty.Toasty;

public class WebActivity extends AppCompatActivity implements WebContract.WebUIView {
    WebView webView;
    int id;
    WebPresenterImpl presenter = new WebPresenterImpl(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        webView = findViewById(R.id.web);
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setSupportZoom(true);
        presenter.getData();
    }

    @Override
    public void onError() {
        Toasty.error(this,"数据加载失败，请检查网络",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateWeb(detailData detData) {
        String body = detData.getBody();
        body = body.replace("<img", "<img style='max-width:100%;height:auto;'");
        webView.loadDataWithBaseURL(null, "<html><body>" + body + "</body></html", "text/html", "utf-8", null);

    }

    @Override
    public void onNUll() {
        Toast.makeText(this,"没有相应数据",Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getID() {
        return id;
    }
}
