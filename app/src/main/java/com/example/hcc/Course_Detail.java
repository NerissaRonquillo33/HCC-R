package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class Course_Detail extends AppCompatActivity {
    TextView detail_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details);
        detail_title = (TextView) findViewById(R.id.detail_title);
        ImageView prev = findViewById(R.id.back2main);
        String username = getIntent().getStringExtra("username");
        int id = getIntent().getIntExtra("id",0);
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
                        detail_title.setText(jsonObject.getString("description"));
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }
}
