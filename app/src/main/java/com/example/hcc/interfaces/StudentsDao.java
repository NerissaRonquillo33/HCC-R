package com.example.hcc.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hcc.models.Students;

import java.util.List;

@androidx.room.Dao
public interface StudentsDao {
    @Insert
    void insert(Students model);
    @Update
    void update(Students model);
    @Delete
    void delete(Students model);
    @Query("UPDATE students SET image = :image WHERE username = :username")
    void updateImage(String username, byte[] image);
    @Query("UPDATE students SET password = :password WHERE username = :username")
    void updatePassword(String username, String password);
    @Query("DELETE FROM students")
    void deleteAll();
    @Query("DELETE FROM students WHERE username = :username")
    void deleteOne(String username);
    @Query("SELECT * FROM students")
    List<Students> all();
    @Query("SELECT * FROM students WHERE username = :username")
    Students findOne(String username);
    @Query("SELECT COUNT(*) FROM students WHERE username = :username AND password = :password")
    int login(String username, String password);
}
