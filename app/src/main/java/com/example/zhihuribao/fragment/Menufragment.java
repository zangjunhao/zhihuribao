package com.example.zhihuribao.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.AdapterView;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.zhihuribao.date.Theme;
import com.alibaba.fastjson.JSON;
import com.example.zhihuribao.Listener;
import com.example.zhihuribao.MainActivity;
import com.example.zhihuribao.R;
import com.example.zhihuribao.date.Constant;
import com.example.zhihuribao.date.xiangxi;
import com.nostra13.universalimageloader.utils.L;
import com.example.zhihuribao.net;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67698 on 2018/3/2.
 */

public class Menufragment extends Basefragment{
    private ArticleThemeListAdaptr adapter;
    private TextView homePage;
    private ListView themesListView;
    private List<xiangxi> themeList;
    protected Activity mActivity;
    private List<String> themeStringList;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menufragment, container, false);
        homePage = (TextView) view.findViewById(R.id.homepage);
        themesListView = (ListView) view.findViewById(R.id.themeList);
        return view;
    }
    @Override
    protected void initData() {
        Theme theme = JSON.parseObject(Constant.bugdate, Theme.class);
        themeList = theme.getOthers();
        themeStringList = new ArrayList<>();
        for (int i = 0; i < themeList.size(); i++) {
            themeStringList.add(themeList.get(i).getName());
        }
        adapter = new ArticleThemeListAdaptr();
        themesListView.setAdapter(adapter);
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = getRootActivity();
                if (!mainActivity.isHomepage) {
                    mainActivity.getHomepage();
                }
                mainActivity.closeDrawerLayout();
            }
        });
       Listener.OnLoadThemesListener listener = new Listener.OnLoadThemesListener() {
            @Override
            public void onSuccess(List<xiangxi> othersList) {
                themeList.clear();
                themeList.addAll(othersList);
                themeStringList.clear();
                for (int i = 0; i < othersList.size(); i++) {
                    themeStringList.add(othersList.get(i).getName());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure() {

            }
        };
        themesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int themeId = themeList.get(position).getId();
                if (themeId != getRootActivity().getCurrentId()) {
                    String title = themeList.get(position).getName();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", themeId);
                    bundle.putString("Title", title);
                    getRootActivity().getThemeFragment(themeId, bundle);
                }
                getRootActivity().closeDrawerLayout();
            }
        });
        net.getThemes(listener);
    }

    public class ArticleThemeListAdaptr extends BaseAdapter {
        private List<String> theme=new ArrayList<String>();
        private Context context;
        @Override
        public int getCount() {
            return theme.size();
        }
        @Override
        public Object getItem(int position) {
            return theme.get(position);
        }
        @Override
        public long getItemId(int position) {
            return  position;
        }
        @Override
        public View getView(int position, View qieview, ViewGroup viewGroup) {
            if (qieview== null) {
                qieview = LayoutInflater.from(context).inflate(R.layout.theme_item, viewGroup, false);
            }
            TextView tv_item = (TextView) qieview.findViewById(R.id.tv_item);
            tv_item.setText(theme.get(position));
            return qieview;
        }
    }

}

