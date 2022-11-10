package com.example.hcc;

public class Course_Item {
    private int id;
    private String code;
    private String description;
    private String username;
    private int unit;
    private int semester;
    private int year;
    private int thumbnail;

    public Course_Item() {}

    public Course_Item(int id, String code, String description, String username, int unit, int semester, int year, int thumbnail) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.unit = unit;
        this.semester = semester;
        this.year = year;
        this.thumbnail = thumbnail;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getUnit() {
        return unit;
    }

    public int getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
