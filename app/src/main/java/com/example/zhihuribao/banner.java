package com.example.zhihuribao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhihuribao.date.Constant;
import com.example.zhihuribao.date.tpstory;
import java.util.ArrayList;
import java.util.List;

public class banner extends FrameLayout {
    private ViewPager viewPager;
    private BannerPagerAdater adapter;
    private TextView topStoriesTitle;
    private List<ImageView> imageViews;
    private List<tpstory> topStoriesList;
    private List<View> dotList;
    private Handler handler;
    private Runnable runnable;
    private Context context;
    private Listener.OnBannerClickListener onBannerClickListener;
    private int currentItem = 0;
    public banner(Context context) {
        this(context, null);
    }
    public banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    public void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_banner, this, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        dotList = new ArrayList<>();
        topStoriesList = getDefaultBannerList();
        handler = new Handler();
        topStoriesTitle = (TextView) view.findViewById(R.id.articleTitle);
        View dot0 = view.findViewById(R.id.v_dot0);
        View dot1 = view.findViewById(R.id.v_dot1);
        View dot2 = view.findViewById(R.id.v_dot2);
        View dot3 = view.findViewById(R.id.v_dot3);
        View dot4 = view.findViewById(R.id.v_dot4);
        dotList.add(dot0);
        dotList.add(dot1);
        dotList.add(dot2);
        dotList.add(dot3);
        dotList.add(dot4);
        onBannerClickListener = new Listener.OnBannerClickListener() {
            @Override
            public void onClick(int id) {
                id = topStoriesList.get(id).getId();
                Intent intent = new Intent(context, ac_content.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        };
        runnable = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(currentItem);
                currentItem = (currentItem + 1) % imageViews.size();
                handler.postDelayed(this, 2500);
            }
        };
        reset();
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view, layoutParams);
    }
    private void reset() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < topStoriesList.size(); i++) {
            if (i > 4) {
                break;
            }
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.qie_bug);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Constant.getImageLoader().displayImage(topStoriesList.get(i).getImage(),
                    imageView, Constant.getDisplayImageOptions());
            imageViews.add(imageView);
            dotList.get(i).setVisibility(View.VISIBLE);
        }
        adapter = new BannerPagerAdater(imageViews);
        adapter.setOnBannerClickListener(onBannerClickListener);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
    }
    public void update(List<tpstory> list) {
        topStoriesList.clear();
        topStoriesList = list;
        reset();
        adapter.notifyDataSetChanged();
        topStoriesTitle.setText(topStoriesList.get(0).getTitle());
    }
    public void startPlay() {
        cancelPlay();
        handler.postDelayed(runnable, 2500);
    }
    public void cancelPlay() {
        handler.removeCallbacks(runnable);
    }
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            tpstory topStories = topStoriesList.get(position);
            topStoriesTitle.setText(topStories.getTitle());
            dotList.get(oldPosition).setBackgroundResource(R.drawable.dot_blur);
            dotList.get(position).setBackgroundResource(R.drawable.dot_focus);
            oldPosition = position;
        }
    }
    private List<tpstory> getDefaultBannerList() {
        List<tpstory> topStoriesList = new ArrayList<>();
        tpstory topStories = new tpstory();
        topStories.setTitle("接受窃格瓦拉的洗礼吧");
        topStories.setImage("0");
        topStories.setId(0);
        topStoriesList.add(topStories);
        return topStoriesList;
    }
    public class BannerPagerAdater extends PagerAdapter {
        private List<ImageView> imageViews;
        private Listener.OnBannerClickListener onBannerClickListener;
        public BannerPagerAdater(List<ImageView> imageViews) {
            this.imageViews = imageViews;
        }
        @Override
        public int getCount() {
            return imageViews.size();
        }
        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            ImageView iv = imageViews.get(position);
            container.addView(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onClick(position);
                    }
                }
            });
            return iv;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        public void setOnBannerClickListener(Listener.OnBannerClickListener onBannerClickListener) {
            this.onBannerClickListener = onBannerClickListener;
        }
    }
}
