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
import com.example.zhihuribao.fragment.ThemeFragment;
public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout refreshLayout;
    private String date;
    private boolean isExit;
    private int currentId;
    public boolean isHomepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        currentId = -1;
        getTransition().add(R.id.rongnafl, new mainfragment(), "Fragment" + currentId).commit();
        isHomepage = true;
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//动画
    }
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("接受窃格瓦拉的洗礼吧");
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.sr);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isHomepage) {
                    mainfragment mainFragment = (mainfragment) getFragmentByTag("Fragment" + currentId);
                    mainFragment.getLatestArticleList();
                } else {
                    ThemeFragment themeFragment = (ThemeFragment) getFragmentByTag("Fragment" + currentId);
                    themeFragment.refreshData();
                }
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
    private void onRefresh() {if (isHomepage) {
        mainfragment mainFragment = (mainfragment) getFragmentByTag("Fragment" + currentId);
        mainFragment.getLatestArticleList();
    } else {
        ThemeFragment themeFragment = (ThemeFragment) getFragmentByTag("Fragment" + currentId);
        themeFragment.refreshData();
    }
    }

    public void getHomepage() {
        mainfragment mainFragment = (mainfragment) getFragmentByTag("Fragment" + "-1");
        ThemeFragment themeFragment = (ThemeFragment) getFragmentByTag("Fragment" + currentId);
        FragmentTransaction transition = getTransition();
        transition.hide(themeFragment);
        if (mainFragment == null) {
            transition.add(R.id.rongnafl, new mainfragment(), "Fragment" + "-1").commit();
        } else {
            transition.show(mainFragment).commit();
        }
        currentId = -1;
        isHomepage = true;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("接受窃格瓦拉的洗礼吧");
        }
    }
    public void getThemeFragment(int id, Bundle bundle) {
        ThemeFragment toFragment = (ThemeFragment) getFragmentByTag("Fragment" + id);
        Basefragment nowFragment;
        if (isHomepage) {
            nowFragment = (mainfragment) getFragmentByTag("Fragment" + currentId);
        } else {
            nowFragment = (ThemeFragment) getFragmentByTag("Fragment" + currentId);
        }
        FragmentTransaction transition = getTransition();
        transition.hide(nowFragment);
        if (toFragment == null) {
            ThemeFragment themeFragment = new ThemeFragment();
            themeFragment.setArguments(bundle);
            transition.add(R.id.rongnafl, themeFragment, "Fragment" + id).commit();
        } else {
            transition.show(toFragment).commit();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(bundle.getString("Title"));
            }
        }
        currentId = id;
        isHomepage = false;
        setRefresh(false);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawerLayout();
            return;
        }
        if (!isHomepage) {
            getHomepage();
            return;
        }
        if (isExit) {
            refreshLayout.setRefreshing(false);
            net.client.cancelAllRequests(true);//取消请求
            super.onBackPressed();
        } else {
            hint();
        }
    }
    private void hint() {
        Snackbar snackbar = Snackbar.make(refreshLayout, "再按一次退出", Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.parseColor("#0099CC"));
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

    private FragmentTransaction getTransition() {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        return transition;
    }
    public Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
    public int getCurrentId() {
        return currentId;
    }
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