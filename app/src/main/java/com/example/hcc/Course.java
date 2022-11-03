package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Course extends AppCompatActivity {

    List<Course_Item> lstCourses;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);
        ImageView prev = findViewById(R.id.back2main);
        lstCourses = new ArrayList<>();
        dbHelper = new DBHelper(this, lstCourses);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Course.this, Dashboard.class);
                startActivity(dashboard);
            }
        });
        /* Courses list */
//        lstCourses = new ArrayList<>();
//        lstCourses.add(new Course_Item(0, "Business Process Management", R.drawable.me_time));
//        lstCourses.add(new Course_Item(1,"Human Computer Interaction", R.drawable.me_time));
//        lstCourses.add(new Course_Item(2,"Introduction to Computing", R.drawable.me_time));
//        lstCourses.add(new Course_Item(3,"Networks and Communications", R.drawable.me_time));
//        lstCourses.add(new Course_Item(4,"Operating Management Systems", R.drawable.me_time));
//        lstCourses.add(new Course_Item(5,"Operating Management Systems", R.drawable.me_time));
        RecyclerView list = findViewById(R.id.course_holder);
        Course_Adapter adapter = new Course_Adapter(this, dbHelper.getData());
        list.setLayoutManager(new GridLayoutManager(this, 1));
        list.setAdapter(adapter);
    }
}
