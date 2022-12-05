package com.example.hcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hcc.abstracts.Database;
import com.example.hcc.helper.MD5;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Students;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    List<Course_Item> lstCourses;
    DBHelper dbHelper;
    TextView denied;
    Database database;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        denied = findViewById(R.id.status);
        TextView register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);
        lstCourses = new ArrayList<>();
        dbHelper = new DBHelper(this, lstCourses);
        database = Database.getInstance(Login.this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    Intent admin = new Intent(Login.this, Admin.class);
                    admin.putExtra("username", username.getText().toString());
                    startActivity(admin);
                } else {
                    if (database.studentsDao().login(username.getText().toString(), new MD5().encrypt(password.getText().toString())) > 0) {
                        Intent dashboard = new Intent(Login.this, Dashboard.class);
                        dashboard.putExtra("username", username.getText().toString());
                        startActivity(dashboard);
                    } else {
                        denied.setVisibility(View.VISIBLE);
                    }
                }
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
        updateStudents();
    }

    public void updateStudents() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Login.this, getResources().getString(R.string.server_path) + "students.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    database.studentsDao().deleteAll();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            database.studentsDao().insert(new Students(object.getString("studentid"),object.getString("password"),object.getString("lastname"),object.getString("firstname"),object.getString("birthday"),object.getString("course"),object.getString("contact"),object.getString("address"),new byte[0]));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }

    public void login(String username, String password) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
            jsonParams.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Login.this, getResources().getString(R.string.server_path) + "login.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    Intent dashboard = new Intent(Login.this, Dashboard.class);
                    dashboard.putExtra("username", username);
                    startActivity(dashboard);
                } else {
                    denied.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}