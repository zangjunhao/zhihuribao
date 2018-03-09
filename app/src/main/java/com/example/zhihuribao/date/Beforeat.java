package com.example.zhihuribao.date;
import java.util.List;
/**
 * Created by 67698 on 2018/3/1.
 */
//过去的文章
public class Beforeat {
    private String date;
    private List<story> stories;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {this.date = date;
    }
    public List<story> getStories() {
        return stories;
    }
    public void setStories(List<story> stories) {
        this.stories = stories;
    }
}
