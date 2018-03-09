package com.example.zhihuribao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.zhihuribao.net;
import com.example.zhihuribao.Listener;
import com.example.zhihuribao.MainActivity;
import com.example.zhihuribao.R;
import com.example.zhihuribao.adapter.content_adapeter;
import com.example.zhihuribao.date.actheme;

/**
 * Created by 67698 on 2018/3/8.
 */
public  class ThemeFragment extends Basefragment {
    private RecyclerView recyclerView;
    private content_adapeter adapter;
    public Listener.OnLoadThemeContentListener listener;
    private int id;
    private boolean flag;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_mainfragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.relist);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        return view;
    }
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        String title = "窃格瓦拉照耀你";
        if (bundle != null) {
            id = bundle.getInt("ID", 1);
            title = bundle.getString("Title");
        }
        if (getRootActivity().getSupportActionBar() != null) {
            getRootActivity().getSupportActionBar().setTitle(title);
        }
        adapter = new content_adapeter(mActivity);
        recyclerView.setAdapter(adapter);
        ThemeFragment.themels listener = new ThemeFragment.themels() {
            @Override
            public void onSuccess(actheme articleTheme) {
                adapter.setData(articleTheme);
                adapter.notifyDataSetChanged();
                stopRefresh();
                if (!flag) {
                    flag = true;
                } else {
                    hint(recyclerView, "再刷新电瓶车就被偷了", Color.parseColor("#0099CC"));
                }
            }
            @Override
            public void onFailure() {
                if (mActivity != null) {
                    hint(recyclerView, "可能文章被吃了吧", Color.parseColor("#0099CC"));
                }
                stopRefresh();
            }
        };
         refreshData();
    }
    public void stopRefresh() {
        if (getRootActivity() != null) {
            getRootActivity().setRefresh(false);
        }
    }
    public interface themels {
        void onSuccess(actheme articleTheme);
        void onFailure();
    }
        public void refreshData() {
            if (!net.isNetworkConnected(mActivity)) {
                stopRefresh();
                hint(recyclerView, "网络问题很大", Color.parseColor("#0099CC"));
                return;
            }
            net.getArticleListByTheme(id,listener);
        }
}
