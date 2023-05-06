package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Announcements;
import com.example.hcc.models.Bills;

import org.json.JSONException;
import org.json.JSONObject;

public class Announcement_Detail extends AppCompatActivity {
    TextView title_announcement;
    TextView body_announcement;
    LinearLayout containerbackground;
    String username,role,studentname;

    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_details);
        title_announcement = (TextView) findViewById(R.id.title_announcement);
        body_announcement = (TextView) findViewById(R.id.body_announcement);
        containerbackground = findViewById(R.id.containerbackgroud);
        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        studentname = getIntent().getStringExtra("nameofstudent");
        ImageView prev = findViewById(R.id.back2main);
        String username = getIntent().getStringExtra("username");
        int id = getIntent().getIntExtra("id",0);
        database = Database.getInstance(Announcement_Detail.this);
        theme();
        displayAnnouncementDetails(id);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent announcement = new Intent(Announcement_Detail.this, Announcement.class);
                announcement.putExtra("username",username);
                announcement.putExtra("role",role);
                announcement.putExtra("nameofstudent",studentname);
                startActivity(announcement);
            }
        });
        /* Details */

    }

    public void displayAnnouncementDetails(int id) {
        Announcements announcements = database.announcementsDao().findOne(id);
        title_announcement.setText(announcements.getTitle());
        body_announcement.setText(announcements.getDuration() + "\n" + announcements.getCaption());
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
        new HttpRequest().doPost(Announcement_Detail.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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
