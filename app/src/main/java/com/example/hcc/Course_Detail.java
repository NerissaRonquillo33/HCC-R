package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class Course_Detail extends AppCompatActivity {
    TextView description;
    TextView objectives;
    TextView schedules;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details);
        description = (TextView) findViewById(R.id.description);
        objectives = (TextView) findViewById(R.id.objectives);
        schedules = (TextView) findViewById(R.id.schedules);
        ImageView prev = findViewById(R.id.back2main);
        String username = getIntent().getStringExtra("username");
        int id = getIntent().getIntExtra("id",0);
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courses = new Intent(Course_Detail.this, Course.class);
                courses.putExtra("username",username);
                startActivity(courses);
            }
        });
        /* Details */
        courseInfo(username, id);
    }

    public void courseInfo(String username, int id) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
            jsonParams.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Course_Detail.this, getResources().getString(R.string.server_path) + "course-info.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                Log.i("Ressss", response);
                if (response.equals("success")) {
                    try {
                        description.setText(jsonObject.getString("description"));
                        objectives.setText(jsonObject.getString("objectives"));
                        schedules.setText(jsonObject.getString("schedule"));
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
        new HttpRequest().doPost(Course_Detail.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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
                            LinearLayout containerbackgroud = findViewById(R.id.containerbackgroud);
                            containerbackgroud.setBackgroundDrawable(bitmapDrawable);
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
