package com.example.hcc;

public class Grade_Item {
    private int id;
    private String subject;
    private String prof;
    private String prelim;
    private String midterm;
    private String finals;
    private String final_grade;
    private String average;
    private String status;
    private String schoolyear;
    private String semester;

    public Grade_Item(int id, String subject, String prof, String prelim, String midterm, String finals, String final_grade, String average, String status, String schoolyear, String semester) {
        this.id = id;
        this.subject = subject;
        this.prof = prof;
        this.prelim = prelim;
        this.midterm = midterm;
        this.finals = finals;
        this.final_grade = final_grade;
        this.average = average;
        this.status = status;
        this.schoolyear = schoolyear;
        this.semester = semester;
    }

    public String getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(String schoolyear) {
        this.schoolyear = schoolyear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public void setPrelim(String prelim) {
        this.prelim = prelim;
    }

    public void setMidterm(String midterm) {
        this.midterm = midterm;
    }

    public void setFinals(String finals) {
        this.finals = finals;
    }

    public void setFinal_grade(String final_grade) {
        this.final_grade = final_grade;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getProf() {
        return prof;
    }

    public String getPrelim() {
        return prelim;
    }

    public String getMidterm() {
        return midterm;
    }

    public String getFinals() {
        return finals;
    }

    public String getFinal_grade() {
        return final_grade;
    }

    public String getAverage() {
        return average;
    }

    public String getStatus() {
        return status;
    }
}
