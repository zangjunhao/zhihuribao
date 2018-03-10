package com.example.zhihuribao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.zhihuribao.fragment.mainfragment;
import android.support.v7.widget.Toolbar;
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
}