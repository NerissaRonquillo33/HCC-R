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

public class Schedule extends AppCompatActivity {

    List<Schedule_Item> lstSchedule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        ImageView prev = findViewById(R.id.back2main);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Schedule.this, Dashboard.class);
                startActivity(dashboard);
            }
        });
        /* Schedule list */
        lstSchedule = new ArrayList<>();
        lstSchedule.add(new Schedule_Item(0, "Business Process Management", R.drawable.me_time));
        lstSchedule.add(new Schedule_Item(1,"Human Computer Interaction", R.drawable.me_time));
        lstSchedule.add(new Schedule_Item(2,"Introduction to Computing", R.drawable.me_time));
        lstSchedule.add(new Schedule_Item(3,"Networks and Communications", R.drawable.me_time));
        lstSchedule.add(new Schedule_Item(4,"Operating Management Systems", R.drawable.me_time));
        lstSchedule.add(new Schedule_Item(5,"Operating Management Systems", R.drawable.me_time));
        RecyclerView list = findViewById(R.id.schedule_holder);
        Schedule_Adapter adapter = new Schedule_Adapter(this, lstSchedule);
        list.setLayoutManager(new GridLayoutManager(this, 3));
        list.setAdapter(adapter);
    }
}
