package com.example.a.demo_news;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<MyData.TopStoriesBean> bannerList = new ArrayList<>();

    ViewPagerAdapter(Context context, List<MyData.TopStoriesBean> list) {
        bannerList.addAll(list);
        this.context = context;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final int[] id = new int[1];
        id[0] = bannerList.get(position).getId();
//        int id =bannerList.get(position).getId();
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_viewpager, container, false);

        TextView textView = view.findViewById(R.id.banner_title);
        ImageView imageView = view.findViewById(R.id.banner_image);
        textView.setText(bannerList.get(position).getTitle());
        Picasso.with(context).load(bannerList.get(position).getImage())
                .fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_plane)
                .error(R.mipmap.ic_settings).into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("id",id[0] );
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
