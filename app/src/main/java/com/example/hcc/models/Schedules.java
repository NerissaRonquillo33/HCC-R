package com.example.hcc.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedules")
public class Schedules {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "subject")
    private String subject;
    @ColumnInfo(name = "course")
    private String course;
    @ColumnInfo(name = "days")
    private String days;
    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "room")
    private String room;

    public Schedules(String username, String subject, String course, String days, String time, String room) {
        this.username = username;
        this.subject = subject;
        this.course = course;
        this.days = days;
        this.time = time;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
