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
    private List<String> themeStringList;
    public final static String acthemeurl="{\"limit\":1000,\"subscribed\":[],\"others\":[{\"color\":15007,\"thumbnail\":\"http:\\/\\/pic3.zhimg.com\\/0e71e90fd6be47630399d63c58beebfc.jpg\",\"description\":\"了解自己和别人，了解彼此的欲望和局限。\",\"id\":13,\"name\":\"日常心理学\"},{\"color\":8307764,\"thumbnail\":\"http:\\/\\/pic4.zhimg.com\\/2c38a96e84b5cc8331a901920a87ea71.jpg\",\"description\":\"内容由知乎用户推荐，海纳主题百万，趣味上天入地\",\"id\":12,\"name\":\"用户推荐日报\"},{\"color\":14483535,\"thumbnail\":\"http:\\/\\/pic3.zhimg.com\\/00eba01080138a5ac861d581a64ff9bd.jpg\",\"description\":\"除了经典和新片，我们还关注技术和产业\",\"id\":3,\"name\":\"电影日报\"},{\"color\":8307764,\"thumbnail\":\"http:\\/\\/pic4.zhimg.com\\/4aa8400ba46d3d46e34a9836744ea232.jpg\",\"description\":\"为你发现最有趣的新鲜事，建议在 WiFi 下查看\",\"id\":11,\"name\":\"不许无聊\"},{\"color\":62140,\"thumbnail\":\"http:\\/\\/p1.zhimg.com\\/d3\\/7b\\/d37b38d5c82b4345ccd7e50c4ae997da.jpg\",\"description\":\"好设计需要打磨和研习，我们分享灵感和路径\",\"id\":4,\"name\":\"设计日报\"},{\"color\":1615359,\"thumbnail\":\"http:\\/\\/pic4.zhimg.com\\/aa94e197491fb9c44d384c4747773810.jpg\",\"description\":\"商业世界变化越来越快，就是这些家伙干的\",\"id\":5,\"name\":\"大公司日报\"},{\"color\":16031744,\"thumbnail\":\"http:\\/\\/pic2.zhimg.com\\/f2e97ff073e5cf9e79c7ed498727ebd6.jpg\",\"description\":\"从业者推荐的财经金融资讯\",\"id\":6,\"name\":\"财经日报\"},{\"color\":9699556,\"thumbnail\":\"http:\\/\\/pic2.zhimg.com\\/98d7b4f8169c596efb6ee8487a30c8ee.jpg\",\"description\":\"把黑客知识科普到你的面前\",\"id\":10,\"name\":\"互联网安全\"},{\"color\":59647,\"thumbnail\":\"http:\\/\\/pic3.zhimg.com\\/2f214a4ca51855670668530f7d333fd8.jpg\",\"description\":\"如果你喜欢游戏，就从这里开始\",\"id\":2,\"name\":\"开始游戏\"},{\"color\":1564695,\"thumbnail\":\"http:\\/\\/pic4.zhimg.com\\/eac535117ed895983bd2721f35d6e8dc.jpg\",\"description\":\"有音乐就很好\",\"id\":7,\"name\":\"音乐日报\"},{\"color\":6123007,\"thumbnail\":\"http:\\/\\/pic1.zhimg.com\\/a0f97c523c64e749c700b2ddc96cfd7c.jpg\",\"description\":\"用技术的眼睛仔细看懂每一部动画和漫画\",\"id\":9,\"name\":\"动漫日报\"},{\"color\":16046124,\"thumbnail\":\"http:\\/\\/pic1.zhimg.com\\/bcf7d594f126e5ceb22691be32b2650a.jpg\",\"description\":\"关注体育，不吵架。\",\"id\":8,\"name\":\"体育日报\"}]}";
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menufragment, container, false);
        homePage = (TextView) view.findViewById(R.id.homepage);
        themesListView = (ListView) view.findViewById(R.id.themeList);
        return view;
    }
    @Override
    protected void initData() {
        Theme theme = JSON.parseObject(acthemeurl, Theme.class);
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
                adapter.notifyDataSetChanged();//刷新页面
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

