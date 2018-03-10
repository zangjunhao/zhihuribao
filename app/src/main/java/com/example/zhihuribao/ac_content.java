package com.example.zhihuribao;
import android.support.v7.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.zhihuribao.date.Constant;
import com.example.zhihuribao.date.Content;

//webview显示文章的内容(这部分很多都不懂，许多都没有理解。。。)
public class ac_content extends AppCompatActivity {
    private Listener .accontentclick listener;
    private WebView webView;
    private String linkCss;
    private String html;
    private enum STATUS {ALREADY_LOAD, WAIT_RETRY, IN_LOAD}//枚举
    private STATUS status;
    private int id;
    private ImageView hintImage;
    private TextView hintText;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_content);
        init();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            id = bundle.getInt("ID");
            net.getArticleContent(id, listener);
            status = STATUS.IN_LOAD;
        }
    }
    private void init() {
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//使用缓存的数据
        webView.getSettings().setDomStorageEnabled(true);//开启本地储存
        webView.getSettings().setDatabaseEnabled(true);//开启离线缓存
        webView.getSettings().setAppCacheEnabled(true);//开启应用缓存
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("窃格瓦拉赐予你力量");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        hintImage = (ImageView) findViewById(R.id.hintImage);
        hintText = (TextView) findViewById(R.id.hintText);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listener = new Listener.accontentclick() {
            @Override
            public void onSuccess(Content content) {
                CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
                mCollapsingToolbarLayout.setTitle(content.getBody());
                webView.loadDataWithBaseURL("file:///android_assets/", content.getBody(), "text/html", "UTF-8", null);
                ImageView imageView = (ImageView) findViewById(R.id.headImage);
                Constant.getImageLoader().displayImage(content.getImage(), imageView, Constant.getDisplayImageOptions());
                stopAnimation(hintImage);
                hintImage.setVisibility(View.GONE);
                hintText.setVisibility(View.GONE);
                status = STATUS.ALREADY_LOAD;
            }
            @Override
            public void onFailure() {
                stopAnimation(hintImage);
                hintImage.setImageResource(R.drawable.chongxing);
                hintText.setText("你再点一下试试");
                status = STATUS.WAIT_RETRY;
                Snackbar snackbar;
                if (!net.isNetworkConnected(ac_content.this)) {
                    snackbar = Snackbar.make(webView, "窃格瓦拉叫你重现连接", Snackbar.LENGTH_SHORT);
                } else {
                    snackbar = Snackbar.make(webView, "好像偷电动车出了一点问题", Snackbar.LENGTH_SHORT);
                }
                snackbar.show();
            }
        };
        startAnimation(hintImage);
    }
    public void setSupportActionBar(Toolbar toolbar) {
    }
    @Override
    public void onBackPressed() {
        net.client.cancelAllRequests(true);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void hint(View view) {
        if (status == STATUS.WAIT_RETRY) {
            hintImage.setImageResource(R.drawable.load);
            hintText.setText("正在加载");
            startAnimation(hintImage);
            status = STATUS.IN_LOAD;
            net.getArticleContent(id, listener);
        }
    }
    //旋转动画(老实说不是很懂)
    public void startAnimation(View view) {
        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(this, R.layout.xuanzhuan);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        view.startAnimation(animation);
    }
    public void stopAnimation(View view) {
        view.clearAnimation();
    }
}
