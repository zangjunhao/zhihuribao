package com.example.zhihuribao.date;
import com.example.zhihuribao.date.story;
import com.example.zhihuribao.date.tpstory;
import java.util.List;
/**
 * Created by 67698 on 2018/3/1.
 */
//新文章
public class Lastat {
    private String date;
    private List<story> stories;
    private List<tpstory> top_stories;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public List<story> getStories() {
        return stories;
    }
    public void setStories(List<story> stories) {
        this.stories = stories;
    }
    public List<tpstory> getTop_stories() {
        return top_stories;
    }
    public void setTop_stories(List<tpstory> top_stories) {
        this.top_stories = top_stories;
    }
}
