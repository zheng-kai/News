package com.example.a.demo_news;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> urlList;

    ViewPagerAdapter(Context context, List<String> list) {
        urlList = list;
        this.context = context;

//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
//        ImageLoader.getInstance().displayImage(urlList.get(position), imageView);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        Picasso.with(context).load(urlList.get(position))
                .fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_plane)
                .error(R.mipmap.ic_settings).into(imageView);


        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
