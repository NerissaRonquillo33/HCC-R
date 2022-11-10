package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Course_Detail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        ImageView prev = findViewById(R.id.back2main);
        String username = getIntent().getStringExtra("username");
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courses = new Intent(Course_Detail.this, Course.class);
                courses.putExtra("username",username);
                startActivity(courses);
            }
        });
        /* Title from intent */
        String title = getIntent().getStringExtra("title");
        detail_title.setText(title+" content soon...");
    }
}
