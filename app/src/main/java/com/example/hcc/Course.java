package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Bills;
import com.example.hcc.models.Schedules;
import com.example.hcc.models.Students;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Course extends AppCompatActivity {

    List<Course_Item> lstCourses;
    DBHelper dbHelper;
    String username;
    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);
        ImageView prev = findViewById(R.id.back2main);
        lstCourses = new ArrayList<>();
        dbHelper = new DBHelper(this, lstCourses);
        username = getIntent().getStringExtra("username");
        database = Database.getInstance(Course.this);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Course.this, Dashboard.class);
                dashboard.putExtra("username",username);
                startActivity(dashboard);
            }
        });
        updateSchedule();
        list();
    }

    public void list() {
        List<Schedules> schedules = database.schedulesDao().find(username);
        for(int n = 0; n < schedules.size(); n++)
        {
            lstCourses.add(new Course_Item(schedules.get(n).getId(),schedules.get(n).getSubject(),schedules.get(n).getCourse(),schedules.get(n).getDays(),schedules.get(n).getTime(),schedules.get(n).getRoom()));
        }
        RecyclerView list = findViewById(R.id.course_holder);
        Course_Adapter adapter = new Course_Adapter(getApplicationContext(), lstCourses);
        list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        list.setAdapter(adapter);
    }

    public void updateSchedule() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Course.this, getResources().getString(R.string.server_path) + "courses.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    database.schedulesDao().deleteAll();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            database.schedulesDao().insert(new Schedules(object.getString("studentid"),object.getString("subject"),object.getString("course"),object.getString("days"),object.getString("time"),object.getString("room")));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }
}
