package com.example.a.news;

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

import com.example.a.news.Data.TodayData;
import com.example.a.news.Details.WebActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<TodayData.StoriesBean> storiesBeans = new ArrayList<>();
    private ViewPagerAdapter adapter;
    private Map<Integer,String> positToDate = new HashMap<>();
    private int size = 1;
    public RecyclerAdapter(Context context) {
        this.context = context;
        adapter = new ViewPagerAdapter(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == 0) {
            L.d("TestRecycler", "viewPagerHolder created");
            view = LayoutInflater.from(context)
                    .inflate(R.layout.banner_recyclerview, viewGroup, false);
            return new viewPagerHolder(view);
        } else {
            L.d("TestRecycler", "itemViewHolder created");
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_recyclerview, viewGroup, false);
            return new itemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof itemViewHolder) {
            itemViewHolder item = (itemViewHolder) viewHolder;
            item.textView.setText(storiesBeans.get(i - 1).getTitle());
            Picasso.with(context).load(storiesBeans.get(i - 1).getImages().get(0))
                    .placeholder(R.drawable.timg)
                    .error(R.drawable.error)
                    .into(item.imageView);
            item.id = storiesBeans.get(i - 1).getId();
            L.d("Testitemdate","position:" + i);
            if(positToDate.containsKey(i)){
                L.d("Testitemdate","position// " + i);
                item.dateView.setVisibility(View.VISIBLE);
                if( i == 1){
                    item.dateView.setText("今日热闻");
                }else {
                    item.dateView.setText(getWeekday(String.valueOf(positToDate.get(i))));
                }
                L.d("Testitemdate","positToDate: " + String.valueOf(positToDate.get(i)));
            }else{
                item.dateView.setVisibility(View.GONE);
            }
            L.d("Recycler", "item bind");
        } else if (viewHolder instanceof viewPagerHolder) {
            ((viewPagerHolder) viewHolder).viewPager.setAdapter(adapter);
            L.d("TestRecycler", "viewPager bind");
        }

    }

    @Override
    public int getItemCount() {
        return storiesBeans.size() + 1;
    }

    public void addItems(List <TodayData.StoriesBean> stories,String date) {
//        if(size == 0){
//            positToDate.put(size,"今日热闻");
//        }else {
            positToDate.put(size,date);
            L.d("Testitemdate",date);
            L.d("Testitemdate",size + "");
            L.d("Testitemdate",positToDate.toString());
//        }
        storiesBeans.addAll(stories);
        size = storiesBeans.size() + 1;
        notifyItemChanged(size, size + stories.size());
        L.d("TestRecycler", "addStories. size:" + stories.size());

    }

    public void addVPData(List<TodayData.TopStoriesBean> topStoriesBeans) {
        adapter.addItem(topStoriesBeans);
        notifyDataSetChanged();
    }

    public void clear() {
        storiesBeans.clear();
        adapter.clear();
        size = 1;
        notifyDataSetChanged();
    }

    class itemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView dateView;
        int id ;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.recycler_date);
            textView = itemView.findViewById(R.id.stories_title);
            imageView = itemView.findViewById(R.id.stories_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });
        }
    }


    class viewPagerHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;

        viewPagerHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewpager);
        }
    }
    public String getWeekday(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yymmdd");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(d);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String str = "";
        switch (day) {
            case 1:
                str = "星期日";
                break;
            case 2:
                str = "星期一";
                break;
            case 3:
                str = "星期二";
                break;
            case 4:
                str = "星期三";
                break;
            case 5:
                str = "星期四";
                break;
            case 6:
                str = "星期五";
                break;
            case 7:
                str = "星期六";
                break;
            default:
                break;
        }
        return date.substring(4,6) + "月" + date.substring(6) + "日 " + str;
    }
}
