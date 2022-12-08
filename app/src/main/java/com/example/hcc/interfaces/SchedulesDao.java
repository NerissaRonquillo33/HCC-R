package com.example.hcc.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hcc.models.Schedules;
import com.example.hcc.models.Students;

import java.util.List;

@androidx.room.Dao
public interface SchedulesDao {
    @Insert
    void insert(Schedules model);
    @Update
    void update(Schedules model);
    @Delete
    void delete(Schedules model);
    @Query("DELETE FROM schedules")
    void deleteAll();
    @Query("DELETE FROM schedules WHERE username = :username")
    void deleteOne(String username);
    @Query("SELECT * FROM schedules")
    List<Schedules> all();
    @Query("SELECT * FROM schedules WHERE username = :username ORDER BY CASE WHEN days = 'Sunday' THEN 1 WHEN days = 'Monday' THEN 2 WHEN days = 'Tuesday' THEN 3 WHEN days = 'Wednesday' THEN 4 WHEN days = 'Thursday' THEN 5 WHEN days = 'Friday' THEN 6 WHEN days = 'Saturday' THEN 7 END ASC")
    List<Schedules> find(String username);
}
