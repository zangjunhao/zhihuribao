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
    public final static String bugdate="{\"limit\":1000,\"subscribed\":[],\"others\":[{\"color\":15007,\"thumbnail\":\"http:\\/\\/pic3.zhimg.com\\/0e71e90fd6be47630399d63c58beebfc.jpg\",\"description\":\"了解自己和别人，了解彼此的欲望和局限。\",\"id\":13,\"name\":\"日常心理学\"},{\"color\":8307764,\"thumbnail\":\"http:\\/\\/pic4.zhimg.com\\/2c38a96e84b5cc8331a901920a87ea71.jpg\",\"description\":\"内容由知乎用户推荐，海纳主题百万，趣味上天入地\",\"id\":12,\"name\":\"用户推荐日报\"},{\"color\":14483535,\"thumbnail\":\"http:\\/\\/pic3.zhimg.com\\/00eba01080138a5ac861d581a64ff9bd.jpg\",\"description\":\"除了经典和新片，我们还关注技术和产业\",\"id\":3,\"name\":\"电影日报\"},{\"color\":8307764,\"thumbnail\":\"http:\\/\\/pic4.zhimg.com\\/4aa8400ba46d3d46e34a9836744ea232.jpg\",\"description\":\"为你发现最有趣的新鲜事，建议在 WiFi 下查看\",\"id\":11,\"name\":\"不许无聊\"},{\"color\":62140,\"thumbnail\":\"http:\\/\\/p1.zhimg.com\\/d3\\/7b\\/d37b38d5c82b4345ccd7e50c4ae997da.jpg\",\"description\":\"好设计需要打磨和研习，我们分享灵感和路径\",\"id\":4,\"name\":\"设计日报\"},{\"color\":1615359,\"thumbnail\":\"http:\\/\\/pic4.zhimg.com\\/aa94e197491fb9c44d384c4747773810.jpg\",\"description\":\"商业世界变化越来越快，就是这些家伙干的\",\"id\":5,\"name\":\"大公司日报\"},{\"color\":16031744,\"thumbnail\":\"http:\\/\\/pic2.zhimg.com\\/f2e97ff073e5cf9e79c7ed498727ebd6.jpg\",\"description\":\"从业者推荐的财经金融资讯\",\"id\":6,\"name\":\"财经日报\"},{\"color\":9699556,\"thumbnail\":\"http:\\/\\/pic2.zhimg.com\\/98d7b4f8169c596efb6ee8487a30c8ee.jpg\",\"description\":\"把黑客知识科普到你的面前\",\"id\":10,\"name\":\"互联网安全\"},{\"color\":59647,\"thumbnail\":\"http:\\/\\/pic3.zhimg.com\\/2f214a4ca51855670668530f7d333fd8.jpg\",\"description\":\"如果你喜欢游戏，就从这里开始\",\"id\":2,\"name\":\"开始游戏\"},{\"color\":1564695,\"thumbnail\":\"http:\\/\\/pic4.zhimg.com\\/eac535117ed895983bd2721f35d6e8dc.jpg\",\"description\":\"有音乐就很好\",\"id\":7,\"name\":\"音乐日报\"},{\"color\":6123007,\"thumbnail\":\"http:\\/\\/pic1.zhimg.com\\/a0f97c523c64e749c700b2ddc96cfd7c.jpg\",\"description\":\"用技术的眼睛仔细看懂每一部动画和漫画\",\"id\":9,\"name\":\"动漫日报\"},{\"color\":16046124,\"thumbnail\":\"http:\\/\\/pic1.zhimg.com\\/bcf7d594f126e5ceb22691be32b2650a.jpg\",\"description\":\"关注体育，不吵架。\",\"id\":8,\"name\":\"体育日报\"}]}";
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