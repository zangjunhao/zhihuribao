package com.example.zhihuribao.date;
import java.util.List;
/**
 * Created by 67698 on 2018/2/27.
 */
//一般文章
public class story {


    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getGa_prefix() {
        return ga_prefix;
    }
    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<String> getImages() {
        return images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }
}
