package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Schedule_Detail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_details);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        ImageView prev = findViewById(R.id.back2main);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schedule = new Intent(Schedule_Detail.this, Schedule.class);
                startActivity(schedule);
            }
        });
        /* Title from intent */
        String title = getIntent().getStringExtra("title");
        detail_title.setText(title);
    }
}
