package com.example.a.demo_news;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    final String url = "http://news-at.zhihu.com/api/4/news/";
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    String date;
    boolean key = true;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerAdapter = new RecyclerAdapter(this);

        initRecyclerView();
        getDataByRetrofit();

        initRefresh();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalItemCount;
            private int firstVisibleItem;
            private int visibleItemCount;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert manager != null;
                totalItemCount = manager.getItemCount();
                firstVisibleItem = manager.findFirstVisibleItemPosition();
                visibleItemCount = recyclerView.getChildCount();
                if (((totalItemCount - visibleItemCount) <= firstVisibleItem)&& key){
                    addDataByRetrofit();
                    size = recyclerAdapter.getSize();
                    key = false;
                }
                if(storiesBeansChanged()){
                    key = true;
                }
                L.d("MyTest: ", "onScrolled 数据加载了");
            }
        });

    }

    public boolean storiesBeansChanged() {
        return size < recyclerAdapter.getSize();
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(manager);
        L.d("MyTest: ", "setRecyclerView 执行了");
    }

    public void refreshData() {
        recyclerAdapter.clear();
        getDataByRetrofit();
        size = recyclerAdapter.getSize();
//        setViewPager();
        L.d("MyTest: ", "refreshData 执行了");
    }

    public void initRefresh() {
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh);
        L.d("MyTest: ", "setRefresh 执行了");
        refreshLayout.setOnRefreshListener(() -> {
            L.d("MyTest: ", "setRefresh 刷新了");
            refreshData();
            refreshLayout.setRefreshing(false);
        });
    }

    public void getDataByRetrofit() {
        key = false;
        L.d("MyTest: ", "getDataByRetrofit 执行了");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<MyData> call = service.getTodayData();
        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(@NonNull Call<MyData> call, @NonNull Response<MyData> response) {
                MyData data = response.body();
                recyclerAdapter.addData(data);
                recyclerAdapter.addViewPager(data.getTop_stories());
                date = data.getDate();
                key = true;
                L.d("MyTest: ","getDataByRetrofit 数据得到了");
            }

            @Override
            public void onFailure(@NonNull Call<MyData> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "getData请求失败", Toast.LENGTH_SHORT).show();
                L.d("MyTest: ","getDataByRetrofit 数据没得到");

            }
        });
    }

    public void addDataByRetrofit() {
        L.d("MyTest: ", "addDataByRetrofit 执行了");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<MyData> call = service.getBeforeData(date);
        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(@NonNull Call<MyData> call, @NonNull Response<MyData> response) {
                MyData data = response.body();
                recyclerAdapter.addData(data);
                date = data.getDate();
                L.d("MyTest: ", "addDataByRetrofit 数据得到了");
            }

            @Override
            public void onFailure(@NonNull Call<MyData> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "addData请求失败", Toast.LENGTH_SHORT).show();
                key = true;
                L.d("MyTest: ", "addDataByRetrofit 数据没得到");
            }
        });
    }
}
