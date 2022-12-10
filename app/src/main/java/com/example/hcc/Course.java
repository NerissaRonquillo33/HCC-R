package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    String username,role;
    Database database;
    TextView nameofstudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);
        ImageView prev = findViewById(R.id.back2main);
        lstCourses = new ArrayList<>();
        nameofstudent = findViewById(R.id.nameofstudent);
        dbHelper = new DBHelper(this, lstCourses);
        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        String studentname = getIntent().getStringExtra("nameofstudent");
        if (role.equals("parent") && studentname != null) {
            nameofstudent.setText(studentname);
            nameofstudent.setVisibility(View.VISIBLE);
        }
        database = Database.getInstance(Course.this);
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Course.this, Dashboard.class);
                if (role != null && role.equals("parent")) {
                    dashboard = new Intent(Course.this, Parent.class);
                }
                dashboard.putExtra("username",username);
                dashboard.putExtra("nameofstudent",studentname);
                dashboard.putExtra("role",role);
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
            lstCourses.add(new Course_Item(schedules.get(n).getId(),schedules.get(n).getSubject(),schedules.get(n).getCourse(),schedules.get(n).getDays(),schedules.get(n).getTime(),schedules.get(n).getRoom(),schedules.get(n).getProf()));
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
            jsonParams.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Course.this, getResources().getString(R.string.server_path) + "schedules.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    database.schedulesDao().deleteAll();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            database.schedulesDao().insert(new Schedules(object.getString("studentid"),object.getString("subject"),object.getString("course"),object.getString("days"),object.getString("time"),object.getString("room"),object.getString("prof")));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }
    public void updateOneSchedule(String username) {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doGet(Course.this, getResources().getString(R.string.server_path2) + username, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    database.schedulesDao().deleteAll();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("students");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            database.schedulesDao().insert(new Schedules(object.getString("studentid"),object.getString("subject"),object.getString("course"),object.getString("days"),object.getString("time"),object.getString("room"),object.getString("prof")));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }
    public void theme() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("name", "theme");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Course.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        String about = jsonObject.getString("value");
                        if (jsonObject.getString("imageb64").length() > 300) {
                            byte[] decodedString = Base64.decode(jsonObject.getString("imageb64"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            BitmapDrawable bitmapDrawable = new BitmapDrawable(decodedByte);
                            LinearLayout containerbackgroud = findViewById(R.id.containerbackgroud);
                            containerbackgroud.setBackgroundDrawable(bitmapDrawable);
                        }

                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }
}
