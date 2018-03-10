package com.example.zhihuribao;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zhihuribao.adapter.content_adapeter;
import com.example.zhihuribao.date.actheme;
import com.example.zhihuribao.fragment.Basefragment;
import com.example.zhihuribao.fragment.Menufragment;
import com.example.zhihuribao.fragment.mainfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout refreshLayout;
    private int number;
    public boolean zhuye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        number = -1;
        getTransition().add(R.id.rongnafl, new mainfragment(), "Fragment" + number).commit();
        zhuye = true;
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//动画
    }
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.sr);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        refreshLayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    mainfragment mainFragment = (mainfragment) getfragment("Fragment" + number);
                    mainFragment.getLatestArticleList();
            }
        });
        //进入页面自动刷新
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }
    private void onRefresh() {
        mainfragment mainFragment = (mainfragment) getfragment("Fragment" + number);
        mainFragment.getLatestArticleList();
    }
    public void getHomepage() {
        mainfragment mainFragment = (mainfragment) getfragment("Fragment" + "-1");
        FragmentTransaction transition = getTransition();
        if (mainFragment == null) {
            transition.add(R.id.rongnafl, new mainfragment(), "Fragment" + "-1").commit();
        } else {
            transition.show(mainFragment).commit();
        }
        number = -1;
        zhuye = true;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("接受窃格瓦拉的洗礼吧");
        }
    }
    private boolean isExit;
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawerLayout();
        return;}
        if (!zhuye) {getHomepage();
        return;}
        if (isExit) {
            refreshLayout.setRefreshing(false);net.client.cancelAllRequests(true);//关闭所有请求super.onBackPressed()
        } else {
            //多设置一次snackbar来提醒退出
            Snackbar snackbar = Snackbar.make(refreshLayout, "意思你就楞个走了哦", Snackbar.LENGTH_SHORT);
            snackbar.setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    isExit = false;
                }
                @Override
                public void onShown(Snackbar snackbar) {
                    isExit = true;
                }
            }).show();
        }
    }
    private FragmentTransaction getTransition() {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        return transition;
    }
    public Fragment getfragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
    private String date;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setRefresh(boolean flag) {
        refreshLayout.setRefreshing(flag);
    }
    public void closeDrawerLayout() {
        this.drawerLayout.closeDrawer(GravityCompat.START);
    }
}