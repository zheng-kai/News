package com.example.a.news.News;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.a.news.Data.TodayData;
import com.example.a.news.L;
import com.example.a.news.R;
import com.example.a.news.RecyclerAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class NewsActivity extends AppCompatActivity implements NewsContract.NewsUIView {
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private boolean key = false;
    private NewsPresenterImpl presenter = new NewsPresenterImpl(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initID();
        initRecyclerAdapter();
        presenter.addData();
        initRefresh();
        initScroll();
    }
    void initID(){
        recyclerView = findViewById(R.id.recyclerview);
        refreshLayout = findViewById(R.id.refresh);
    }
    void initRecyclerAdapter(){
        recyclerAdapter = new RecyclerAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyclerAdapter);
    }
    void initRefresh(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                refreshLayout.setRefreshing(false);
            }
        });
    }
    void initScroll(){
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
                    presenter.addData();
                    L.d("TestScrolled","update");
                    key = false;
                }
            }
        });
    }
    @Override
    public void onError() {
        key = true;
        Toasty.error(this,"数据加载失败，请检查网络",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateRV(List<TodayData.StoriesBean> list,String date) {
        L.d("TestrecyclerView","add date: " + date);
        recyclerAdapter.addItems(list,date);
        key = true;
    }

    @Override
    public void updatePV(List<TodayData.TopStoriesBean> list) {
        L.d("Testpagerview","topStoriesBean:" + list.toString());
        recyclerAdapter.addVPData(list);
    }

    @Override
    public void refreshData() {
        recyclerAdapter.clear();
        presenter.clear();
        key = false;
        presenter.addData();
    }

    @Override
    public void onNull() {
        key = true;
        Toast.makeText(this,"没有相应数据",Toast.LENGTH_SHORT).show();
    }
}
