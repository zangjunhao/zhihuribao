package com.example.zhihuribao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import com.example.zhihuribao.Listener;
import com.example.zhihuribao.MainActivity;
import com.example.zhihuribao.R;
import com.example.zhihuribao.adapter.readapter;
import com.example.zhihuribao.date.Beforeat;
import com.example.zhihuribao.date.Lastat;
import com.example.zhihuribao.date.tpstory;
import com.example.zhihuribao.net;
public class mainfragment extends Basefragment {
    private RecyclerView recyclerView;
    private readapter adapter;
    private Listener.aclastclick latestListener;
    private Listener.acbeforeclick beforeListener;
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
        adapter = new readapter(mActivity);
        recyclerView.setAdapter(adapter);
        latestListener = new Listener.aclastclick() {
            @Override
            public void onSuccess(Lastat articleLatest) {
                adapter.setData(articleLatest);
                getRootActivity().setDate(articleLatest.getDate());
                List<tpstory> topStoriesList = articleLatest.getTop_stories();
                if (adapter.loadTopArticleListener != null) {
                    adapter.loadTopArticleListener.onSuccess(topStoriesList);}
                stopRefresh();
                if (!flag) {flag = true;}
                else
                {hint(recyclerView, "再刷新电动车都被偷了", Color.parseColor("#0099CC"));}
                getBeforeArticleList();}
            @Override
            public void onFailure() {
                if (mActivity != null) {
                    hint(recyclerView, "加载有问题", Color.parseColor("#0099CC"));
                }
                stopRefresh();
            }
        };
        beforeListener = new Listener.acbeforeclick() {
            @Override
            public void onSuccess(Beforeat articleBefore) {
                adapter.addData(articleBefore.getStories());
                adapter.notifyDataSetChanged();
                getRootActivity().setDate(articleBefore.getDate());
            }
            @Override
            public void onFailure() {
                if (mActivity != null) {
                    hint(recyclerView, "文章好像被吃了", Color.parseColor("#0099CC"));
                }
            }
        };
        Listener.slbuttom slideListener = new Listener.slbuttom() {
            @Override
            public void onSlideToTheBottom() {
                getBeforeArticleList();
            }
        };
        adapter.setSlideToTheBottomListener(slideListener);
        getLatestArticleList();
    }
    public void getLatestArticleList() {
        if (!net.isNetworkConnected(mActivity)) {
            hint(recyclerView, "大哥你怕是网络有问题哦？", Color.parseColor("#0099CC"));
            stopRefresh();
            return;
        }
        net.getLatestArticleList(latestListener);
    }
    public void getBeforeArticleList() {
        if (!net.isNetworkConnected(mActivity)) {
            hint(recyclerView, "电动车的电路好像出现了问题？", Color.parseColor("#0099CC"));
            return;
        }
        net.getBeforeArticleList(getRootActivity().getDate(), beforeListener);
    }
    public void stopRefresh() {
        if (getRootActivity() != null) {
            getRootActivity().setRefresh(false);
        }
    }
    }
