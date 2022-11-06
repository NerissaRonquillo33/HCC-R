package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentInfo extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        ImageView prev = findViewById(R.id.back2main);
        TextView fullname = findViewById(R.id.fullname);
        TextView course = findViewById(R.id.course);
        TextView contact = findViewById(R.id.contact);
        TextView gender = findViewById(R.id.gender);
        TextView dob = findViewById(R.id.dob);
        TextView age = findViewById(R.id.age);
        String username = getIntent().getStringExtra("username");

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(StudentInfo.this, "http://192.168.1.19/api/info.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        fullname.setText(jsonObject.getString("lastname") + ", " + jsonObject.getString("firstname") + " " + jsonObject.getString("middlename"));
                        course.setText(jsonObject.getString("course"));
                        contact.setText(jsonObject.getString("contact"));
                        gender.setText(jsonObject.getString("gender"));
                        dob.setText(jsonObject.getString("dob"));
                        age.setText(jsonObject.getString("age"));
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });

        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(StudentInfo.this, Dashboard.class);
                startActivity(dashboard);
            }
        });
    }
}
