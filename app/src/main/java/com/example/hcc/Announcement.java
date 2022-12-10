package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Announcements;
import com.example.hcc.models.Grades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Announcement extends AppCompatActivity {

    List<Announcement_Item> lstA;
    String username,role;
    Database database;
    LinearLayout containerbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement);
        ImageView prev = findViewById(R.id.back2main);
        containerbackground = findViewById(R.id.containerbackgroud);
        lstA = new ArrayList<>();
        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        String nameofstudent = getIntent().getStringExtra("nameofstudent");
        database = Database.getInstance(Announcement.this);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Announcement.this, Dashboard.class);
                if (role != null && role.equals("parent")) {
                    dashboard = new Intent(Announcement.this, Parent.class);
                }
                dashboard.putExtra("username",username);
                dashboard.putExtra("nameofstudent",nameofstudent);
                dashboard.putExtra("role",role);
                startActivity(dashboard);
            }
        });
        updateAnnouncement();
        announcement();
        theme();
    }

    public void announcement() {
        List<Announcements> announcements = database.announcementsDao().all();
        for(int n = 0; n < announcements.size(); n++)
        {
            lstA.add(new Announcement_Item(announcements.get(n).getEventid(),announcements.get(n).getTitle(),announcements.get(n).getCaption(),announcements.get(n).getDuration()));
        }
        RecyclerView list = findViewById(R.id.announcement_holder);
        Announcement_Adapter adapter = new Announcement_Adapter(getApplicationContext(), lstA);
        list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        list.setAdapter(adapter);
    }

    public void updateAnnouncement() {
        /* Announcement list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Announcement.this, getResources().getString(R.string.server_path) + "announcement.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    database.announcementsDao().deleteAll();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            database.announcementsDao().insert(new Announcements(object.getInt("eventid"),object.getString("title"),object.getString("caption"),object.getString("duration")));
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
        new HttpRequest().doPost(Announcement.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                Log.i("aaaaaa", response);
                if (response.equals("success")) {
                    try {
                        String about = jsonObject.getString("value");
                        if (jsonObject.getString("imageb64").length() > 300) {
                            byte[] decodedString = Base64.decode(jsonObject.getString("imageb64"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            BitmapDrawable bitmapDrawable = new BitmapDrawable(decodedByte);
                            containerbackground.setBackgroundDrawable(bitmapDrawable);
                        }

                    } catch (JSONException e) {
                        //todo
                        Log.i("aaaaaa", e.toString());
                    }
                }
            }
        });
    }
}