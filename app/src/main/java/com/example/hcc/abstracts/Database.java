package com.example.hcc.abstracts;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hcc.interfaces.GradesDao;
import com.example.hcc.interfaces.SchedulesDao;
import com.example.hcc.interfaces.StudentsDao;
import com.example.hcc.models.Grades;
import com.example.hcc.models.Schedules;
import com.example.hcc.models.Students;

@androidx.room.Database(entities = {Students.class, Schedules.class, Grades.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract StudentsDao studentsDao();
    public abstract SchedulesDao schedulesDao();
    public abstract GradesDao gradesDao();
    public static final String DATABASE_NAME = "hcc";
    public static Database instance;
    public static Database getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,Database.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }

        return instance;
    }
}
