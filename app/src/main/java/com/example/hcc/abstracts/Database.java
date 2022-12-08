package com.example.hcc.abstracts;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hcc.interfaces.AnnouncementsDao;
import com.example.hcc.interfaces.BillsDao;
import com.example.hcc.interfaces.GradesDao;
import com.example.hcc.interfaces.SchedulesDao;
import com.example.hcc.interfaces.SettingsDao;
import com.example.hcc.interfaces.StudentsDao;
import com.example.hcc.models.Announcements;
import com.example.hcc.models.Bills;
import com.example.hcc.models.Grades;
import com.example.hcc.models.Schedules;
import com.example.hcc.models.Settings;
import com.example.hcc.models.Students;

@androidx.room.Database(entities = {Students.class, Schedules.class, Grades.class, Bills.class, Announcements.class, Settings.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract StudentsDao studentsDao();
    public abstract SchedulesDao schedulesDao();
    public abstract GradesDao gradesDao();
    public abstract BillsDao billsDao();
    public abstract AnnouncementsDao announcementsDao();
    public abstract SettingsDao settingsDao();
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
