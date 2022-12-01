package com.example.hcc.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grades")
public class Grades {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "subject")
    private String subject;
    @ColumnInfo(name = "faculty")
    private String faculty;
    @ColumnInfo(name = "prelim")
    private String prelim;
    @ColumnInfo(name = "midterm")
    private String midterm;
    @ColumnInfo(name = "finals")
    private String finals;
    @ColumnInfo(name = "finalgrades")
    private String finalgrades;
    @ColumnInfo(name = "average")
    private String average;
    @ColumnInfo(name = "status")
    private String status;

    public Grades(String username, String subject, String faculty, String prelim, String midterm, String finals, String finalgrades, String average, String status) {
        this.username = username;
        this.subject = subject;
        this.faculty = faculty;
        this.prelim = prelim;
        this.midterm = midterm;
        this.finals = finals;
        this.finalgrades = finalgrades;
        this.average = average;
        this.status = status;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPrelim() {
        return prelim;
    }

    public void setPrelim(String prelim) {
        this.prelim = prelim;
    }

    public String getMidterm() {
        return midterm;
    }

    public void setMidterm(String midterm) {
        this.midterm = midterm;
    }

    public String getFinals() {
        return finals;
    }

    public void setFinals(String finals) {
        this.finals = finals;
    }

    public String getFinalgrades() {
        return finalgrades;
    }

    public void setFinalgrades(String finalgrades) {
        this.finalgrades = finalgrades;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
