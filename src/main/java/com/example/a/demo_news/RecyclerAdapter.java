package com.example.a.demo_news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MyData.TopStoriesBean> bannerData = new ArrayList<>();
    private List<MyData.StoriesBean> stories = new ArrayList<>();
    private int size = 0;
    private List<Integer> positions = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();

    RecyclerAdapter(Context context) {
        this.context = context;
        positions.add(0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        L.d("Recycler ", "onCreate");
        if (i == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.banner_recyclerview, viewGroup, false);
            return new viewPagerHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, viewGroup, false);
            return new itemHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        L.d("Recycler ", "onBind");
        if (viewHolder instanceof itemHolder) {
            itemHolder holder = (itemHolder) viewHolder;
            holder.textView.setText(stories.get(i - 1).getTitle());
            Picasso.with(context).load(stories.get(i - 1).getImages().get(0))
                    .placeholder(R.mipmap.ic_plane)
                    .error(R.mipmap.ic_settings)
                    .into(holder.imageView);
            holder.id = stories.get(i - 1).getId();
            holder.dateView.setVisibility(View.GONE);
            L.d("Holder", "GONE");
            for (int j = 0; j < positions.size(); j++) {
                if (i == 1 && i - 1 == positions.get(j)) {
                    holder.dateView.setText("今日热闻");
                    holder.dateView.setVisibility(View.VISIBLE);
                } else if (i - 1 == positions.get(j)) {
                    String weekday = getWeekday(dateList.get(j));
                    String formatDate = dateList.get(j).substring(4, 6) + "月"
                            + dateList.get(j).substring(6) + "日  " + weekday;
                    holder.dateView.setText(formatDate);
                    holder.dateView.setVisibility(View.VISIBLE);
                    L.d("Holder", "date");
                }
            }
            size = stories.size();

        }
        if (viewHolder instanceof viewPagerHolder) {
            if(bannerData == null){
                L.d("bannerData","bannerData is null");
            }else {
                L.d("bannerData",bannerData.toString());
            }
            viewPagerHolder holder = (viewPagerHolder) viewHolder;
            ViewPagerAdapter adapter = new ViewPagerAdapter(context, bannerData);
            holder.viewPager.setAdapter(adapter);
            L.d("Holder", "viewPager");
        }
    }

    @Override
    public int getItemCount() {
        L.d("Recycler ", "getCount");
        return (stories.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        L.d("Recycler ", "getType");
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void addData(MyData data) {
        stories.addAll(data.getStories());
        positions.add(stories.size());
        dateList.add(data.getDate());
        notifyItemRangeChanged(size + 1, stories.size() - size);
    }

    public void addViewPager(List<MyData.TopStoriesBean> topStoriesBeans) {
        bannerData.addAll(topStoriesBeans);
        notifyDataSetChanged();
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        stories.clear();
        dateList.clear();
        bannerData.clear();
        positions.clear();
        positions.add(0);
        notifyDataSetChanged();
    }


    public class itemHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView clickBackground;
        TextView dateView;
        int id;

        public itemHolder(@NonNull View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.recycler_date);
            textView = itemView.findViewById(R.id.recycler_text);
            imageView = itemView.findViewById(R.id.recycler_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, WebActivity.class);
                            intent.putExtra("id", id);
                            context.startActivity(intent);
                }
            });
        }
    }

    public class viewPagerHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;

        public viewPagerHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.bannerViewPager);
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
        return str;
    }
}
