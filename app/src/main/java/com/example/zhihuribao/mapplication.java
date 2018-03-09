package com.example.zhihuribao;

/**
 * Created by 67698 on 2018/3/3.
 */
import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

//配置图片下载缓存跟引用库一起使用
public class mapplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);
    }
    private void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageLoader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).diskCache(new UnlimitedDiskCache(cacheDir)).writeDebugLogs().imageDownloader(new BaseImageDownloader(context, 30 * 1000, 30 * 1000)).diskCacheFileCount(100).build();
        ImageLoader.getInstance().init(config);
    }

}
