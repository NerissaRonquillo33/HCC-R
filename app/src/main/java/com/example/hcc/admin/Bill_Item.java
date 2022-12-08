package com.example.hcc.admin;

public class Bill_Item {
    private int id;
    private String studentid;
    private String tuitionfee;
    private String learnandins;
    private String regfee;
    private String compprossfee;
    private String guidandcouns;
    private String schoolidfee;
    private String studenthand;
    private String schoolfab;
    private String insurance;
    private String totalass;
    private String discount;
    private String netass;
    private String cashcheckpay;
    private String balance;
    private String studentname;
    private String course;

    public Bill_Item(int id, String studentid, String tuitionfee, String learnandins, String regfee, String compprossfee, String guidandcouns, String schoolidfee, String studenthand, String schoolfab, String insurance, String totalass, String discount, String netass, String cashcheckpay, String balance, String studentname, String course) {
        this.id = id;
        this.studentid = studentid;
        this.tuitionfee = tuitionfee;
        this.learnandins = learnandins;
        this.regfee = regfee;
        this.compprossfee = compprossfee;
        this.guidandcouns = guidandcouns;
        this.schoolidfee = schoolidfee;
        this.studenthand = studenthand;
        this.schoolfab = schoolfab;
        this.insurance = insurance;
        this.totalass = totalass;
        this.discount = discount;
        this.netass = netass;
        this.cashcheckpay = cashcheckpay;
        this.balance = balance;
        this.studentname = studentname;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getTuitionfee() {
        return tuitionfee;
    }

    public void setTuitionfee(String tuitionfee) {
        this.tuitionfee = tuitionfee;
    }

    public String getLearnandins() {
        return learnandins;
    }

    public void setLearnandins(String learnandins) {
        this.learnandins = learnandins;
    }

    public String getRegfee() {
        return regfee;
    }

    public void setRegfee(String regfee) {
        this.regfee = regfee;
    }

    public String getCompprossfee() {
        return compprossfee;
    }

    public void setCompprossfee(String compprossfee) {
        this.compprossfee = compprossfee;
    }

    public String getGuidandcouns() {
        return guidandcouns;
    }

    public void setGuidandcouns(String guidandcouns) {
        this.guidandcouns = guidandcouns;
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

    public String getSchoolfab() {
        return schoolfab;
    }

    public void setSchoolfab(String schoolfab) {
        this.schoolfab = schoolfab;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getTotalass() {
        return totalass;
    }

    public void setTotalass(String totalass) {
        this.totalass = totalass;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getNetass() {
        return netass;
    }

    public void setNetass(String netass) {
        this.netass = netass;
    }

    public String getCashcheckpay() {
        return cashcheckpay;
    }

    public void setCashcheckpay(String cashcheckpay) {
        this.cashcheckpay = cashcheckpay;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
