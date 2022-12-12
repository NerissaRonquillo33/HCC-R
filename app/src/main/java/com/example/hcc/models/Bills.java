package com.example.hcc.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bills")
public class Bills {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "tuitionfee")
    private String tuitionfee;
    @ColumnInfo(name = "learninginstructional")
    private String learninginstructional;
    @ColumnInfo(name = "registrationfee")
    private String registrationfee;
    @ColumnInfo(name = "computerprocsngfee")
    private String computerprocsngfee;
    @ColumnInfo(name = "guidancecounseling")
    private String guidancecounseling;
    @ColumnInfo(name = "schoolidfee")
    private String schoolidfee;
    @ColumnInfo(name = "studenthand")
    private String studenthand;
    @ColumnInfo(name = "schoolpublication")
    private String schoolpublication;
    @ColumnInfo(name = "insurance")
    private String insurance;
    @ColumnInfo(name = "totalasses")
    private String totalasses;
    @ColumnInfo(name = "lessdiscountscholar")
    private String lessdiscountscholar;
    @ColumnInfo(name = "netassessed")
    private String netassessed;
    @ColumnInfo(name = "lesscashpayment")
    private String lesscashpayment;
    @ColumnInfo(name = "outstandingbal")
    private String outstandingbal;
    @ColumnInfo(name = "datecreated")
    private int datecreated;

    public Bills(String username, String tuitionfee, String learninginstructional, String registrationfee, String computerprocsngfee, String guidancecounseling, String schoolidfee, String studenthand, String schoolpublication, String insurance, String totalasses, String lessdiscountscholar, String netassessed, String lesscashpayment, String outstandingbal, int datecreated) {
        this.username = username;
        this.tuitionfee = tuitionfee;
        this.learninginstructional = learninginstructional;
        this.registrationfee = registrationfee;
        this.computerprocsngfee = computerprocsngfee;
        this.guidancecounseling = guidancecounseling;
        this.schoolidfee = schoolidfee;
        this.studenthand = studenthand;
        this.schoolpublication = schoolpublication;
        this.insurance = insurance;
        this.totalasses = totalasses;
        this.lessdiscountscholar = lessdiscountscholar;
        this.netassessed = netassessed;
        this.lesscashpayment = lesscashpayment;
        this.outstandingbal = outstandingbal;
        this.datecreated = datecreated;
    }

    public int getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(int datecreated) {
        this.datecreated = datecreated;
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

    public String getTuitionfee() {
        return tuitionfee;
    }

    public void setTuitionfee(String tuitionfee) {
        this.tuitionfee = tuitionfee;
    }

    public String getLearninginstructional() {
        return learninginstructional;
    }

    public void setLearninginstructional(String learninginstructional) {
        this.learninginstructional = learninginstructional;
    }

    public String getRegistrationfee() {
        return registrationfee;
    }

    public void setRegistrationfee(String registrationfee) {
        this.registrationfee = registrationfee;
    }

    public String getComputerprocsngfee() {
        return computerprocsngfee;
    }

    public void setComputerprocsngfee(String computerprocsngfee) {
        this.computerprocsngfee = computerprocsngfee;
    }

    public String getGuidancecounseling() {
        return guidancecounseling;
    }

    public void setGuidancecounseling(String guidancecounseling) {
        this.guidancecounseling = guidancecounseling;
    }

    public String getSchoolidfee() {
        return schoolidfee;
    }

    public void setSchoolidfee(String schoolidfee) {
        this.schoolidfee = schoolidfee;
    }

    public String getStudenthand() {
        return studenthand;
    }

    public void setStudenthand(String studenthand) {
        this.studenthand = studenthand;
    }

    public String getSchoolpublication() {
        return schoolpublication;
    }

    public void setSchoolpublication(String schoolpublication) {
        this.schoolpublication = schoolpublication;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getTotalasses() {
        return totalasses;
    }

    public void setTotalasses(String totalasses) {
        this.totalasses = totalasses;
    }

    public String getLessdiscountscholar() {
        return lessdiscountscholar;
    }

    public void setLessdiscountscholar(String lessdiscountscholar) {
        this.lessdiscountscholar = lessdiscountscholar;
    }

    public String getNetassessed() {
        return netassessed;
    }

    public void setNetassessed(String netassessed) {
        this.netassessed = netassessed;
    }

    public String getLesscashpayment() {
        return lesscashpayment;
    }

    public void setLesscashpayment(String lesscashpayment) {
        this.lesscashpayment = lesscashpayment;
    }

    public String getOutstandingbal() {
        return outstandingbal;
    }

    public void setOutstandingbal(String outstandingbal) {
        this.outstandingbal = outstandingbal;
    }
}
