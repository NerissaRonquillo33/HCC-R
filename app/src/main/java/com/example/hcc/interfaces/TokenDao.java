package com.example.hcc.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hcc.models.Token;

import java.util.List;

@androidx.room.Dao
public interface TokenDao {
    @Insert
    void insert(Token model);
    @Update
    void update(Token model);
    @Delete
    void delete(Token model);
    @Query("UPDATE token SET device = :token")
    void updateToken(String token);
    @Query("DELETE FROM token")
    void deleteAll();
    @Query("SELECT * FROM token LIMIT 1")
    Token findOne();
}
