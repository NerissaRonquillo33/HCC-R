package com.example.hcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Login extends AppCompatActivity {

    List<Course_Item> lstCourses;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        TextView denied = findViewById(R.id.status);
        TextView register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);
        lstCourses = new ArrayList<>();
        dbHelper = new DBHelper(this, lstCourses);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonParams = new JSONObject();
                try {
                    jsonParams.put("secret_key", "secret_key");
                    jsonParams.put("username", username.getText().toString());
                    jsonParams.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new HttpRequest().doPost(Login.this, getResources().getString(R.string.server_path) + "/api/login.php", jsonParams, new RequestCallback() {
                    @Override
                    public void success(String response, JSONObject jsonObject) {
                        if (response.equals("success")) {
                            Intent dashboard = new Intent(Login.this, Dashboard.class);
                            dashboard.putExtra("username",username.getText().toString());
                            startActivity(dashboard);
                        } else {
                            denied.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
        /* Register */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registeration = new Intent(Login.this, Register.class);
                startActivity(registeration);
            }
        });
    }
}