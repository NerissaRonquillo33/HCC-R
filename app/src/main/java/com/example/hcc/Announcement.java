package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
    String username;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement);
        ImageView prev = findViewById(R.id.back2main);
        lstA = new ArrayList<>();
        username = getIntent().getStringExtra("username");
        database = Database.getInstance(Announcement.this);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Announcement.this, Dashboard.class);
                dashboard.putExtra("username",username);
                startActivity(dashboard);
            }
        });
        updateAnnouncement();
        announcement();
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
}