package com.example.a.demo_news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapter extends RecyclerView.Adapter {
    Context context;
    private List<String> imagesUrl = new ArrayList<>();
    private List<MyData.StoriesBean> stories = new ArrayList<>();

    //    private static int dateCount = 0;
//    int dataNumber;
//    String date;
    RecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.viewpager_recyclerview, viewGroup, false);
            return new viewPagerHolder(view);
        }
//        else if(i == 1){
////            view = LayoutInflater.from(context).inflate(R.layout.date_recyclerview, viewGroup,false);
////            return new dateHolder(view);
////        }
////        else if(i == 2){
////            view = LayoutInflater.from(context).inflate(R.layout.date_recyclerview, viewGroup,false);
////            return new dateHolder(view);
////        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, viewGroup, false);
            return new itemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof itemHolder) {
            itemHolder holder = (itemHolder) viewHolder;
            holder.textView.setText(stories.get(i - 1).getTitle());
            Picasso.with(context).load(stories.get(i - 1).getImages().get(0))
                    .placeholder(R.mipmap.ic_plane)
                    .error(R.mipmap.ic_settings)
                    .into(holder.imageView);
            holder.id = String.valueOf(stories.get(i - 1).getId());

        }
        if (viewHolder instanceof viewPagerHolder) {
            viewPagerHolder holder = (viewPagerHolder) viewHolder;
            ViewPagerAdapter adapter = new ViewPagerAdapter(context, imagesUrl);
            holder.viewPager.setAdapter(adapter);
        }
//        if(viewHolder instanceof dateTitleHolder){
//            dateTitleHolder holder = (dateTitleHolder) viewHolder;
//            holder.dataView.setText("今日热闻");
////        }
//        if(viewHolder instanceof dateHolder){
//            dateHolder holder = (dateHolder)viewHolder;
//            holder.dataView.setText(date);
//        }

    }

    @Override
    public int getItemCount() {
        return (stories.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
//        else if(position == 1){
//            return 1;
//        }else if(position == dataNumber){
//            return 2;
//        }
        else {
            return 3;
        }
    }

    public void addItem(List<MyData.StoriesBean> storiesData) {
        int size = stories.size();
        stories.addAll(storiesData);
        notifyItemRangeChanged(size + 1, stories.size() - size);
    }

    public void addViewPager(List<String> imagesUrlData) {
        imagesUrl.addAll(imagesUrlData);
        notifyDataSetChanged();
    }

    //    public void setDate(String date){
//        this.date = date;
//        dateCount ++;
//        dataNumber = dateCount + stories.size() + 1;
//    }
    public void clear() {
        stories.clear();
        imagesUrl.clear();
        notifyDataSetChanged();
    }

    public class itemHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        String id;
        public itemHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_text);
            imageView = itemView.findViewById(R.id.recycler_image);
            textView.setOnClickListener(v -> {
                Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            });
            imageView.setOnClickListener(v -> {
                Toast.makeText(context, "image was clicked.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("ID", id);
                context.startActivity(intent);
            });
        }
    }
    public class Date {
       String date;

    }
    public class Text {
        String text;
    }
    public class viewPagerHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;

        public viewPagerHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewpager);
        }
    }

//    public class bannerHolder extends RecyclerView.ViewHolder {
//        Banner banner;
//
//        public bannerHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//    public class dateHolder extends RecyclerView.ViewHolder{
//        TextView dataView;
//        int type;
//
//        public dateHolder(@NonNull View itemView) {
//            super(itemView);
//            dataView = itemView.findViewById(R.id.data_text);
//        }
//    }

//    public class dateTitleHolder extends RecyclerView.ViewHolder {
//        TextView dataView;
//        int type;
//        public dateTitleHolder(@NonNull View itemView) {
//            super(itemView);
//            dataView = itemView.findViewById(R.id.data_text);
//        }
//    }
}
