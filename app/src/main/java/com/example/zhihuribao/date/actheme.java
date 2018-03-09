package com.example.zhihuribao.date;

import java.util.List;

/**
 * Created by 67698 on 2018/3/1.
 */

public class actheme {
    private String description;
    private String background;
    private int color;
    private String name;
    private String image;
    private String image_source;
    private List<story> stories;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getBackground() {
        return background;
    }
    public void setBackground(String background) {
        this.background = background;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImage_source() {
        return image_source;
    }
    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public List<story> getStories() {
        return stories;
    }
    public void setStories(List<story> stories) {
        this.stories = stories;
    }
}
