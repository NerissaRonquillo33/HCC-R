package com.example.hcc;

public class Course_Item {
    private int id;
    private String course_description;
    private String course;
    private String days;
    private String time;
    private String room;

    public Course_Item(int id,String course_description, String course, String days, String time, String room) {
        this.id = id;
        this.course_description = course_description;
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

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
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
