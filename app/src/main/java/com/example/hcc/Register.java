package com.example.hcc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Register extends AppCompatActivity {

    List<Course_Item> lstCourses;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        TextView prev = (TextView) findViewById(R.id.backtologin);
        EditText firstname = findViewById(R.id.firstname);
        EditText middlename = findViewById(R.id.middlename);
        EditText lastname = findViewById(R.id.lastname);
        EditText contact = findViewById(R.id.contact);
        EditText gender = findViewById(R.id.gender);
        EditText dob = findViewById(R.id.dob);
        EditText age = findViewById(R.id.age);
        EditText course = findViewById(R.id.course_info);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        TextView status = findViewById(R.id.status);
        Button register = findViewById(R.id.register);
        lstCourses = new ArrayList<>();
        dbHelper = new DBHelper(this, lstCourses);
        /* Register */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonParams = new JSONObject();
                try {
                    jsonParams.put("secret_key", "secret_key");
                    jsonParams.put("firstname", firstname.getText().toString());
                    jsonParams.put("middlename", middlename.getText().toString());
                    jsonParams.put("lastname", lastname.getText().toString());
                    jsonParams.put("contact", contact.getText().toString());
                    jsonParams.put("gender", gender.getText().toString());
                    jsonParams.put("dob", dob.getText().toString());
                    jsonParams.put("age", age.getText().toString());
                    jsonParams.put("course", course.getText().toString());
                    jsonParams.put("username", username.getText().toString());
                    jsonParams.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new HttpRequest().doPost(Register.this, getResources().getString(R.string.server_path) + "register.php", jsonParams, new RequestCallback() {
                    @Override
                    public void success(String response, JSONObject jsonObject) {
                        status.setVisibility(View.VISIBLE);
                        status.setTextColor(Color.parseColor("#e02800"));
                        if (response.equals("success")) {
                            status.setTextColor(Color.parseColor("#23ba36"));
                            status.setText("Registration success.");
                        }
                        else if (response.equals("duplicate")) {
                            status.setText("Already registered.");
                        }
                    }
                });
            }
        });
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(Register.this, Login.class);
                startActivity(main);
            }
        });
    }
}
