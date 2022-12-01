package com.example.hcc.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Students {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "lastname")
    private String lastname;
    @ColumnInfo(name = "firstname")
    private String firstname;
    @ColumnInfo(name = "birthdate")
    private String birthdate;
    @ColumnInfo(name = "course")
    private String course;
    @ColumnInfo(name = "contact")
    private String contact;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Students(String username, String password, String lastname, String firstname, String birthdate, String course, String contact, String address, byte[] image) {
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthdate = birthdate;
        this.course = course;
        this.contact = contact;
        this.address = address;
        this.image = image;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
