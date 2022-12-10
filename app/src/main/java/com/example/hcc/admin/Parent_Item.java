package com.example.hcc.admin;

public class Parent_Item {
    private int id;
    private String student_id;
    private String studentname;
    private String fullname;
    private String username;
    private String password;
    private String email;

    public Parent_Item(int id, String student_id, String studentname, String fullname, String username, String password, String email) {
        this.id = id;
        this.student_id = student_id;
        this.studentname = studentname;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
