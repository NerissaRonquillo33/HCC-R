package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.hcc.abstracts.Database;
import com.example.hcc.helper.MD5;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Students;
import com.example.hcc.workers.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    List<Course_Item> lstCourses;
    DBHelper dbHelper;
    TextView status;
    TextView sync;
    Database database;
    EditText username;
    LinearLayout background,containerbackgroud;

    PeriodicWorkRequest periodicWorkRequest;

    Data.Builder workerData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextInputEditText username = findViewById(R.id.username);
        TextInputEditText password = findViewById(R.id.password);
        TextInputLayout etPasswordLayout = findViewById(R.id.etPasswordLayout);
        etPasswordLayout.setVisibility(View.GONE);
        status = findViewById(R.id.status);
        background = findViewById(R.id.background);
        containerbackgroud = findViewById(R.id.containerbackgroud);
        TextView register = findViewById(R.id.register);
        sync = findViewById(R.id.synch);
        Button login = findViewById(R.id.login);
        lstCourses = new ArrayList<>();
        dbHelper = new DBHelper(this, lstCourses);
        database = Database.getInstance(Login.this);
        updateStudents();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sync.getVisibility() == View.GONE) {
                    Students students_verify = database.studentsDao().findOne(username.getText().toString());
                    if (etPasswordLayout.getVisibility() == View.GONE && students_verify != null && students_verify.getRole().equals("Student")) {
                        status.setVisibility(View.VISIBLE);
                        status.setText("Login successful!");
                        status.setTextColor(Color.parseColor("#FF03DAC5"));
                        Intent dashboard = new Intent(Login.this, Dashboard.class);
                        dashboard.putExtra("username", username.getText().toString());
                        dashboard.putExtra("notification", "welcome");
                        dashboard.putExtra("role", "Student");
                        dashboard.putExtra("year", students_verify.getYear());
                        dashboard.putExtra("section", students_verify.getSection());
//                    workerData = new Data.Builder();
//                    workerData.putString("studentid", username.getText().toString());
//                    periodicWorkRequest = new PeriodicWorkRequest.Builder(Notification.class,1, TimeUnit.MINUTES).setInputData(workerData.build()).build();
//                    WorkManager.getInstance(Login.this).enqueueUniquePeriodicWork("HCC", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
                        startActivity(dashboard);
                    }
                    else if (etPasswordLayout.getVisibility() == View.VISIBLE && username.getText().toString().equals("developer") && password.getText().toString().equals("developer")) {
                        Intent developer = new Intent(Login.this, Developer.class);
                        developer.putExtra("username", username.getText().toString());
                        startActivity(developer);
                    }
                    else if (etPasswordLayout.getVisibility() == View.VISIBLE && username.getText().toString().equals("admins") && password.getText().toString().equals("admins")) {
                        Intent admin = new Intent(Login.this, Admin.class);
                        admin.putExtra("username", username.getText().toString());
                        admin.putExtra("notification", "welcome");
                        admin.putExtra("role", "admin");
                        startActivity(admin);
                    }
                    else if (etPasswordLayout.getVisibility() == View.VISIBLE && database.studentsDao().login(username.getText().toString(), new MD5().encrypt(password.getText().toString())) > 0) {
                        status.setVisibility(View.VISIBLE);
                        status.setText("Login successful!");
                        status.setTextColor(Color.parseColor("#FF03DAC5"));
                        Students students = database.studentsDao().findOne(username.getText().toString());
                        Intent dashboard = new Intent(Login.this, Dashboard.class);
                        if (students.getRole().equals("Admin")) {
                            dashboard = new Intent(Login.this, Admin.class);
                        }
                        else if (students.getRole().equals("Faculty")) {
                            status.setText("Login failed!");
                            status.setTextColor(Color.parseColor("#F88379"));
                            return;
                        }
                        else if (students.getRole().equals("Cashier")) {
                            status.setText("Login failed!");
                            status.setTextColor(Color.parseColor("#F88379"));
                            return;
                        }
                        else if (students.getRole().equals("Registrar")) {
                            status.setText("Login failed!");
                            status.setTextColor(Color.parseColor("#F88379"));
                            return;
                        }
                        dashboard.putExtra("username", username.getText().toString());
                        dashboard.putExtra("notification", "welcome");
                        dashboard.putExtra("role", students.getRole());
                        startActivity(dashboard);
                    }
                    else if (etPasswordLayout.getVisibility() == View.VISIBLE) {
                        login(username.getText().toString(), password.getText().toString());
                    } else {
                        etPasswordLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Please wait for data synchronization to complete.",Toast.LENGTH_LONG).show();
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
        theme();
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
                            database.studentsDao().insert(new Students(object.getString("studentid"),object.getString("password"),object.getString("lastname"),object.getString("firstname"),object.getString("birthday"),object.getString("course"),object.getString("contact"),object.getString("address"),object.getString("role"), Base64.decode(object.getString("image"), Base64.DEFAULT), object.getString("year"), object.getString("section")));
                        }
                        sync.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        //todo
                        sync.setText("It seems connection timeout occurred. Please restart the app.");
                        sync.setTextColor(Color.parseColor("#F44336"));
                    }
                } else {
                    sync.setText("It seems connection timeout occurred. Please restart the app.");
                    sync.setTextColor(Color.parseColor("#F44336"));
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
                Log.i("aaaaaa", response);
                if (response.equals("success")) {
                    try {
                        Intent dashboard = new Intent(Login.this, Parent.class);
                        dashboard.putExtra("username", jsonObject.getString("student_id"));
                        dashboard.putExtra("parentusername", jsonObject.getString("username"));
                        dashboard.putExtra("fullname", jsonObject.getString("fullname"));
                        dashboard.putExtra("nameofstudent", jsonObject.getString("lastname") + ", " + jsonObject.getString("firstname"));
                        dashboard.putExtra("notification", "welcome");
                        dashboard.putExtra("role", "parent");
                        dashboard.putExtra("year", jsonObject.getString("year"));
                        dashboard.putExtra("section", jsonObject.getString("section"));
                        startActivity(dashboard);
                    } catch (JSONException e) {
                        //todo
                        Log.i("vbbbbb", e.toString());
                    }
                } else {
                    status.setVisibility(View.VISIBLE);
                    status.setText("Access denied!");
                    status.setTextColor(Color.parseColor("#F88379"));
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
        new HttpRequest().doPost(Login.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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
                            background.setBackgroundDrawable(bitmapDrawable);
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