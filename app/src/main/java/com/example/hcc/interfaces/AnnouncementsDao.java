package com.example.hcc.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hcc.models.Announcements;

import java.util.List;

@androidx.room.Dao
public interface AnnouncementsDao {
    @Insert
    void insert(Announcements model);
    @Update
    void update(Announcements model);
    @Delete
    void delete(Announcements model);
    @Query("DELETE FROM announcements")
    void deleteAll();
    @Query("DELETE FROM announcements WHERE eventid = :eventid")
    void deleteOne(String eventid);
    @Query("SELECT * FROM announcements ORDER BY eventid DESC")
    List<Announcements> all();
    @Query("SELECT * FROM announcements WHERE eventid = :eventid")
    List<Announcements> find(String eventid);
    @Query("SELECT * FROM announcements WHERE eventid = :eventid LIMIT 1")
    Announcements findOne(int eventid);
}
