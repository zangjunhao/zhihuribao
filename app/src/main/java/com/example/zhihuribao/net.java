package com.example.zhihuribao; /**
 * Created by 67698 on 2018/3/6.
 */
import com.example.zhihuribao.Listener;
import com.example.zhihuribao.date.Beforeat;
import com.example.zhihuribao.date.Constant;
import com.example.zhihuribao.date.Content;
import com.example.zhihuribao.date.Lastat;
import com.example.zhihuribao.date.Theme;
import com.example.zhihuribao.date.actheme;
import com.example.zhihuribao.date.xiangxi;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.alibaba.fastjson.JSON;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;
public class net//完整的asynchttp框架的使用
{
    public static AsyncHttpClient client = new AsyncHttpClient();//设置静态变量接收数据
    static {
        client.setConnectTimeout(1000 * 30);
        client.setTimeout(1000 * 30);
    }
    //判断网络连接情况
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    //接受最新文章
    public static void getLatestArticleList(final Listener.aclastclick listener) {
        client.get(Constant.aclatesturl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Lastat articleLatest = JSON.parseObject(responseString, Lastat.class);
                if (articleLatest != null && articleLatest.getStories() != null && articleLatest.getStories().size() > 0
                        && articleLatest.getTop_stories() != null && articleLatest.getTop_stories().size() > 0) {
                    if (listener != null) {
                        listener.onSuccess(articleLatest);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });}
        //接受过去的文章
    public static void getBeforeArticleList(final String date, final Listener.acbeforeclick listener) {
        client.get(Constant.acbeforeurl+ date, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener!= null) {
                    listener.onFailure();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
               Beforeat articleBefore = JSON.parseObject(responseString,Beforeat.class);
                if (articleBefore != null && articleBefore.getStories() != null && articleBefore.getStories().size() > 0) {
                    if (listener != null) {
                        listener.onSuccess(articleBefore);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }}
        });
    }
    //得到文章的内容
    public static void getArticleContent(int id, final Listener.accontentclick listener) {
        client.get(Constant.accontenturl + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
            @Override//接入数据html和css
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Content content = JSON.parseObject(responseString, Content.class);
                if (content != null && !TextUtils.isEmpty(content.getBody())) {
                    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/src.css\" type=\"text/css\">";
                    String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
                    html = html.replace("<div class=\"img-place-holder\">", "");
                    content.setBody(html);
                    if (listener != null) {
                        listener.onSuccess(content);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }

            }
        });
    }
    public static void getThemes(final Listener.OnLoadThemesListener listener) {
        client.get(Constant.acthemeurl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Theme theme = JSON.parseObject(responseString, Theme.class);
                List<xiangxi> othersList = theme.getOthers();
                if (theme != null && othersList != null && othersList.size() > 0) {
                    if (othersList != null && othersList.size() > 0) {
                        if (listener != null) {
                            listener.onSuccess(othersList);
                            return;
                        }
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }
    public static void getArticleListByTheme(int id, final Listener.OnLoadThemeContentListener listener) {
        client.get(Constant.themecontenturl + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                actheme articleTheme = JSON.parseObject(responseString, actheme.class);
                if (articleTheme != null && articleTheme.getStories() != null && articleTheme.getStories().size() > 0) {
                    if (listener != null) {
                        listener.onSuccess(articleTheme);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }
}
