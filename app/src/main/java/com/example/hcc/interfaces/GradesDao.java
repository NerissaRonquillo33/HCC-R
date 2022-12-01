package com.example.hcc.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hcc.models.Grades;
import com.example.hcc.models.Schedules;

import java.util.List;

@androidx.room.Dao
public interface GradesDao {
    @Insert
    void insert(Grades model);
    @Update
    void update(Grades model);
    @Delete
    void delete(Grades model);
    @Query("DELETE FROM grades")
    void deleteAll();
    @Query("DELETE FROM grades WHERE username = :username")
    void deleteOne(String username);
    @Query("SELECT * FROM grades")
    List<Grades> all();
    @Query("SELECT * FROM grades WHERE username = :username")
    List<Grades> find(String username);
}
