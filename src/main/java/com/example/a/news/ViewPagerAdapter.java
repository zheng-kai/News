package com.example.a.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a.news.Data.TodayData;
import com.example.a.news.Details.WebActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<TodayData.TopStoriesBean> topStoriesBeans = new ArrayList<>();

    ViewPagerAdapter(Context context) {
        this.context = context;
    }
    public void addItem(List<TodayData.TopStoriesBean> topStoriesBeans){
        this.topStoriesBeans.addAll(topStoriesBeans);
    }
    public void clear(){
        topStoriesBeans.clear();
    }

    @Override
    public int getCount() {
        return topStoriesBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, container, false);
        ImageView imageView = view.findViewById(R.id.banner_image);
        TextView textView = view.findViewById(R.id.banner_title);
        textView.setText(topStoriesBeans.get(position).getTitle());
        Picasso.with(context).load(topStoriesBeans.get(position).getImage())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.timg)
                .error(R.drawable.error)
                .into(imageView);
        final int id = topStoriesBeans.get(position).getId();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
