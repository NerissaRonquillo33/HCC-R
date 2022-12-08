package com.example.hcc.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "announcements")
public class Announcements {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "eventid")
    private int eventid;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "caption")
    private String caption;
    @ColumnInfo(name = "duration")
    private String duration;

    public Announcements(int eventid, String title, String caption, String duration) {
        this.eventid = eventid;
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

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
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
