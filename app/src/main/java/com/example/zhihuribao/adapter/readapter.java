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

import java.util.List;
import java.util.ArrayList;

import com.example.zhihuribao.ac_content;
import com.example.zhihuribao.banner;
import com.example.zhihuribao.R;
import com.example.zhihuribao.date.Constant;
import com.example.zhihuribao.date.Lastat;
import com.example.zhihuribao.date.story;
import com.example.zhihuribao.Listener;
import com.example.zhihuribao.date.tpstory;
public  class readapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public readapter(Context context) {
        this.context = context;
        init();
    }
    private final int TYPE_FOOTER = 2;
    private LayoutInflater inflater;
    private List <story>storyls;
    private LayoutInflater layoutInflater;
    private Context context;
    public Listener.actpstoryclick loadTopArticleListener;
    private Listener.slbuttom slideListener;
    private Listener.acitemclick clickListener;
    private ArticleListTopHolder articleListTopHolder;
    private void init() {
        inflater = LayoutInflater.from(context);
        storyls = new ArrayList<>();
        //文章列表点击事件监听
        clickListener = new Listener.acitemclick() {
            @Override
            public void OnItemClickListener(int position) {
                int id = storyls.get(position - 1).getId();
                Intent intent = new Intent(context,ac_content.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        };
        loadTopArticleListener = new Listener.actpstoryclick() {
            @Override
            public void onSuccess(List<tpstory> topStoriesList) {
                if (articleListTopHolder != null) {
                    articleListTopHolder.banner.update(topStoriesList);
                    articleListTopHolder.banner.startPlay();
                    notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure() {
            }
        };
    }
    @Override//设置函数获取类型，获取没完成的时候就显示加载，获取到头部和下部文章后进行显示
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0 ;
        }
        if (position + 1 == getItemCount()) {
            return 2;
        }
        return 1;
    }

    public void addData(List<story> storiesList) {
        this.storyls.addAll(storiesList);
    }
    public void setData(Lastat articleLatest) {
        storyls.clear();
        storyls.addAll(articleLatest.getStories());
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {view = inflater.inflate(R.layout.aclistview_item, parent, false);
            return  new mHolder(view);
        } else if (viewType == 2) {view = inflater.inflate(R.layout.jiazhai, parent, false);
            return new ArticleListFooterHolder(view);
        }
        view = inflater.inflate(R.layout.tpac, parent, false);
        return new ArticleListTopHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1 :mHolder articleListHolder = (mHolder)holder;
                Constant.getImageLoader().displayImage(storyls.get(position - 1).getImages().get(0), articleListHolder.articleImage, Constant.getDisplayImageOptions());
                articleListHolder.articleTitle.setText(storyls.get(position - 1).getTitle());
                articleListHolder.setItemClickListener(clickListener);
                break;
            case 2:
                if (slideListener != null && storyls != null && storyls.size() > 0) {slideListener.onSlideToTheBottom();
                }break;
           case 0:articleListTopHolder = (ArticleListTopHolder) holder;
              break;
        }
    }
    @Override
    public int getItemCount() {
        return storyls.size() + 1;
    }

   static class ArticleListTopHolder extends RecyclerView.ViewHolder {
        public banner banner;
        public ArticleListTopHolder (View itemView) {
            super(itemView);
            banner = (banner) itemView.findViewById(R.id.banner);
        }
    }
   static class ArticleListFooterHolder extends RecyclerView.ViewHolder {
        public TextView footerTitle;
        public ArticleListFooterHolder(View itemView) {
            super(itemView);
            footerTitle = (TextView) itemView.findViewById(R.id.footerTitle);
        }
    }
   static  class  mHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView articleTitle;
        public ImageView articleImage;
        public Listener.acitemclick itemClickListener;
    public mHolder(View itemView) {
            super(itemView);
            articleTitle = (TextView) itemView.findViewById(R.id.actitle);
            articleImage = (ImageView) itemView.findViewById(R.id.acimage);
            articleTitle.setOnClickListener(this);
            articleImage.setOnClickListener(this);
        }
    public void setItemClickListener(Listener.acitemclick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClickListener(getAdapterPosition());
        }
    }

}


}