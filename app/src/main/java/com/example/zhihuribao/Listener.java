package com.example.zhihuribao;
import java.util.List;
import com.example.zhihuribao.date.Content;
import com.example.zhihuribao.date.Beforeat;
import com.example.zhihuribao.date.Lastat;
import com.example.zhihuribao.date.actheme;
import com.example.zhihuribao.date.tpstory;
import com.example.zhihuribao.date.xiangxi;

/**
 * Created by 67698 on 2018/3/2.
 */
//各部分的监听
public class Listener {
    public interface acitemclick {
        void OnItemClickListener(int position);
    }
    public interface accontentclick {
        void onSuccess(Content content);
        void onFailure();
    }
    public interface acbeforeclick {
        void onSuccess(Beforeat articleBefore);
        void onFailure();
    }
    public interface aclastclick {
        void onSuccess(Lastat articleLatest);
        void onFailure();
    }
    public interface OnBannerClickListener {

        void onClick(int id);
    }
    public interface actpstoryclick{
        void onSuccess(List<tpstory> topStoriesList);
        void onFailure();
    }
    public interface OnLoadThemeContentListener {

        void onSuccess(actheme articleTheme);

        void onFailure();
    }
    public interface OnLoadThemesListener {
        void onSuccess(List<xiangxi> othersList);
        void onFailure();
    }
    public interface slbuttom {

        void onSlideToTheBottom();
    }
}
