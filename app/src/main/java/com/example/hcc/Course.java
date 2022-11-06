package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        String username = getIntent().getStringExtra("username");
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Course.this, Dashboard.class);
                startActivity(dashboard);
            }
        });
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Course.this, "http://192.168.1.19/api/courses.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        lstCourses.clear();
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            lstCourses.add(new Course_Item(Integer.parseInt(object.getString("id")),object.getString("code"),object.getString("description"),Integer.parseInt(object.getString("unit")),Integer.parseInt(object.getString("semester")),Integer.parseInt(object.getString("year")),R.drawable.book_edit));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                    RecyclerView list = findViewById(R.id.course_holder);
                    Course_Adapter adapter = new Course_Adapter(getApplicationContext(), lstCourses);
                    list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    list.setAdapter(adapter);
                }
            }
        });
    }
}
