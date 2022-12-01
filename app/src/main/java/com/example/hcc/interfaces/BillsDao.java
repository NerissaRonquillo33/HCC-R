package com.example.hcc.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hcc.models.Bills;
import com.example.hcc.models.Grades;

import java.util.List;

@androidx.room.Dao
public interface BillsDao {
    @Insert
    void insert(Bills model);
    @Update
    void update(Bills model);
    @Delete
    void delete(Bills model);
    @Query("DELETE FROM bills")
    void deleteAll();
    @Query("DELETE FROM bills WHERE username = :username")
    void deleteOne(String username);
    @Query("SELECT * FROM bills")
    List<Bills> all();
    @Query("SELECT * FROM bills WHERE username = :username")
    Bills find(String username);
}
