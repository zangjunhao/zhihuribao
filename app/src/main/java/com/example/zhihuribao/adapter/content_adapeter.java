package com.example.zhihuribao.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhihuribao.banner;
import com.example.zhihuribao.Listener;
import com.example.zhihuribao.R;
import com.example.zhihuribao.date.Constant;
import com.example.zhihuribao.date.Content;
import com.example.zhihuribao.date.actheme;
import com.example.zhihuribao.date.story;
import com.example.zhihuribao.mHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67698 on 2018/3/5.
 */

public class content_adapeter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<story> storiesList;
    private LayoutInflater inflater;
    private Context context;
    private Listener.acitemclick clickListener;
    public content_adapeter(Context context) {
        this.context = context;
        init();
    }
    public void init() {
        inflater = LayoutInflater.from(context);
        storiesList = new ArrayList<>();
        clickListener = new Listener.acitemclick() {
            @Override
            public void OnItemClickListener(int position) {
                int id = storiesList.get(position - 1).getId();
                Intent intent = new Intent(context, Content.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                intent.putExtras(bundle);
                context.startActivity(intent);}
        };
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType ==1) {
            view = inflater.inflate(R.layout.activity_main, parent, false);
            return new mHolder(view);
        } else {
            view = inflater.inflate(R.layout.tptheme, parent, false);
            return new ThemeArticleTopHolder(view);
        }
    }
    private actheme articleTheme;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ThemeArticleTopHolder themeArticleTopHolder = (ThemeArticleTopHolder) holder;
            String url = "";
            String description = "";
            if (articleTheme != null && articleTheme.getBackground() != null && articleTheme.getDescription() != null) {
                url = articleTheme.getBackground();
                description = articleTheme.getDescription();
            }
            Constant.getImageLoader().displayImage(url,
                    themeArticleTopHolder.themeImage, Constant.getDisplayImageOptions());
            themeArticleTopHolder.themeDescription.setText(description);
            return;
        }
        mHolder articleListHolder = (mHolder) holder;
        List<String> images = storiesList.get(position - 1).getImages();
        if (images != null && images.size() > 0) {
            Constant.getImageLoader().displayImage(images.get(0),
                    articleListHolder.articleImage, Constant.getDisplayImageOptions());
            articleListHolder.articleImage.setVisibility(View.VISIBLE);
        } else {
            articleListHolder.articleImage.setVisibility(View.GONE);
        }
        articleListHolder.articleTitle.setText(storiesList.get(position - 1).getTitle());
        articleListHolder.setItemClickListener(clickListener);
    }

    private class ThemeArticleTopHolder extends RecyclerView.ViewHolder {
        public ImageView themeImage;
        public TextView themeDescription;
        public ThemeArticleTopHolder(View itemView) {
            super(itemView);
            themeImage = (ImageView) itemView.findViewById(R.id.themeImage);
            themeDescription = (TextView) itemView.findViewById(R.id.themeDescription);
        }
    }
    @Override
    public int getItemCount() {
        return storiesList.size() + 1;
    }

    public void setData(actheme articleTheme) {
        storiesList.clear();
        this.articleTheme = articleTheme;
        storiesList.addAll(articleTheme.getStories());
    }

}
