package com.example.hcc;

public class Announcement_Item {
    private int id;
    private String title;
    private String caption;
    private String duration;

    public Announcement_Item(int id, String title, String caption, String duration) {
        this.id = id;
        this.title = title;
        this.caption = caption;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
