package com.example.zhihuribao.date;
import com.example.zhihuribao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
/**
 * Created by 67698 on 2018/3/6.
 */
//终于接入接口了，哈哈哈(获取参数)
public class Constant {
    public final static String accontenturl = "http://news-at.zhihu.com/api/4/news/";
    public final static String acbeforeurl = "http://news.at.zhihu.com/api/4/news/before/";
    public final static String aclatesturl = "http://news-at.zhihu.com/api/4/news/latest";
    public final static String acthemeurl = "http://news-at.zhihu.com/api/4/themes";
    public final static String themecontenturl = "http://news-at.zhihu.com/api/4/theme/";
    public static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }
    //设置网络错误时显示
    public static DisplayImageOptions getDisplayImageOptions() {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.qie_bug).showImageForEmptyUri(R.drawable.qie_bug).showImageOnFail(R.drawable.qie_bug).displayer(new FadeInBitmapDisplayer(0)).cacheInMemory(true).cacheOnDisk(true).build();
    }
}