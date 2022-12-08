package com.example.hcc.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hcc.models.Announcements;
import com.example.hcc.models.Settings;

import java.util.List;

@androidx.room.Dao
public interface SettingsDao {
    @Insert
    void insert(Settings model);
    @Update
    void update(Settings model);
    @Delete
    void delete(Settings model);
    @Query("DELETE FROM settings")
    void deleteAll();
    @Query("DELETE FROM settings WHERE name = :name")
    void deleteOne(String name);
    @Query("UPDATE settings SET value = :value, image = :image WHERE name = :name")
    void updateOne(String name, String value, byte[] image);
    @Query("SELECT * FROM settings")
    List<Settings> all();
    @Query("SELECT * FROM settings WHERE name = :name")
    List<Settings> find(String name);
    @Query("SELECT * FROM settings WHERE name = :name")
    Settings findOne(String name);
}
