package com.example.hcc.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "token")
public class Token {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "device")
    private String device;
    public Token(String device) {
        this.device = device;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
