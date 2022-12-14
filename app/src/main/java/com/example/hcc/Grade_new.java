package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Grades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Grade_new extends AppCompatActivity {

    String username,role;
    Database database;
    WebView views;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_new);
        ImageView prev = findViewById(R.id.back2main);
        String views_data = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n" +
                "    <style type=\"text/css\">\n" +
                "        body {\n" +
                "            font-size: 10px;\n" +
                "            font-size: 2.5vw;\n" +
                "        }\n" +
                "        th {\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>" +
                "    <table>";
        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        views = findViewById(R.id.grades_view);
        views.setBackgroundResource(R.drawable.background);
        views.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        String studentname = getIntent().getStringExtra("nameofstudent");
        if (role.equals("parent") && studentname != null) {
            views_data += "<h1>"+ studentname +"</h1>";
        }
        database = Database.getInstance(Grade_new.this);
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Grade_new.this, Dashboard.class);
                if (role != null && role.equals("parent")) {
                    dashboard = new Intent(Grade_new.this, Parent.class);
                }
                dashboard.putExtra("username",username);
                dashboard.putExtra("nameofstudent",studentname);
                dashboard.putExtra("role",role);
                startActivity(dashboard);
            }
        });
        updateGrades();
        views_data += "<tr><td colspan=\"8\">&nbsp;</td></tr>\n" +
                "        <tr><th colspan=\"8\" align=\"left\"><h1>FIRST YEAR</h1></th></tr>\n" +
                "        <tr><th colspan=\"8\" align=\"left\">First Semester</th></tr>\n" +
                "        <tr bgcolor=\"white\"><th>Subject</th><th>Faculty</th><th>Prelim</th><th>Midterm</th><th>Finals</th><th>Final Grade</th><th>Average</th><th>Status</th></tr>";
        views_data += gradesInit("2019/2020","First");

        views_data += "<tr><th colspan=\"8\" align=\"left\">Second Semester</th></tr>\n" +
                "        <tr bgcolor=\"white\"><th>Subject</th><th>Faculty</th><th>Prelim</th><th>Midterm</th><th>Finals</th><th>Final Grade</th><th>Average</th><th>Status</th></tr>";
        views_data += gradesInit("2019/2020","Second");

        views_data += "<tr><td colspan=\"8\">&nbsp;</td></tr>\n" +
                "        <tr style=\"margin-top: 10px;\"><th colspan=\"8\" align=\"left\"><h1>SECOND YEAR</h1></th></tr>\n" +
                "        <tr><th colspan=\"8\" align=\"left\">First Semester</th></tr>\n" +
                "        <tr bgcolor=\"white\"><th>Subject</th><th>Faculty</th><th>Prelim</th><th>Midterm</th><th>Finals</th><th>Final Grade</th><th>Average</th><th>Status</th></tr>";
        views_data += gradesInit("2020/2021","First");

        views_data += "<tr><th colspan=\"8\" align=\"left\">Second Semester</th></tr>\n" +
                "        <tr bgcolor=\"white\"><th>Subject</th><th>Faculty</th><th>Prelim</th><th>Midterm</th><th>Finals</th><th>Final Grade</th><th>Average</th><th>Status</th></tr>";
        views_data += gradesInit("2020/2021","Second");

        views_data += "<tr><td colspan=\"8\">&nbsp;</td></tr>\n" +
                "        <tr><th colspan=\"8\" align=\"left\"><h1>THIRD YEAR</h1></th></tr>\n" +
                "        <tr><th colspan=\"8\" align=\"left\">First Semester</th></tr>\n" +
                "        <tr bgcolor=\"white\"><th>Subject</th><th>Faculty</th><th>Prelim</th><th>Midterm</th><th>Finals</th><th>Final Grade</th><th>Average</th><th>Status</th></tr>";
        views_data += gradesInit("2021/2022","First");

        views_data += "<tr><th colspan=\"8\" align=\"left\">Second Semester</th></tr>\n" +
                "        <tr bgcolor=\"white\"><th>Subject</th><th>Faculty</th><th>Prelim</th><th>Midterm</th><th>Finals</th><th>Final Grade</th><th>Average</th><th>Status</th></tr>";
        views_data += gradesInit("2021/2022","Second");

        views_data += "<tr><td colspan=\"8\">&nbsp;</td></tr>\n" +
                "        <tr><th colspan=\"8\" align=\"left\"><h1>FOURTH YEAR</h1></th></tr>\n" +
                "        <tr><th colspan=\"8\" align=\"left\">First Semester</th></tr>\n" +
                "        <tr bgcolor=\"white\"><th>Subject</th><th>Faculty</th><th>Prelim</th><th>Midterm</th><th>Finals</th><th>Final Grade</th><th>Average</th><th>Status</th></tr>";
        views_data += gradesInit("2022/2023","First");

        views_data += "<tr><th colspan=\"8\" align=\"left\">Second Semester</th></tr>\n" +
                "        <tr bgcolor=\"white\"><th>Subject</th><th>Faculty</th><th>Prelim</th><th>Midterm</th><th>Finals</th><th>Final Grade</th><th>Average</th><th>Status</th></tr>";
        views_data += gradesInit("2022/2023","Second");
        views.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        views.loadData(views_data+="</table>\n" +
                "</body>\n" +
                "</html>","text/html","UTF-8");
    }

    public void GradesSelection(String schoolyear, String semester, int holder, List<Grade_Item> lstgrad, TextView firstyear,TextView firstsem,TextView secondsem) {
        int ctr = 0;
        RecyclerView list = findViewById(holder);
        List<Grades> grades = database.gradesDao().findPerSem(username, schoolyear, semester);
        for(int n = 0; n < grades.size(); n++)
        {
            lstgrad.add(new Grade_Item(grades.get(n).getId(),grades.get(n).getSubject(),grades.get(n).getFaculty(),grades.get(n).getPrelim(),grades.get(n).getMidterm(),grades.get(n).getFinals(),grades.get(n).getFinalgrades(),grades.get(n).getAverage(),grades.get(n).getStatus(),grades.get(n).getSchoolyear(),grades.get(n).getSemester()));
            ctr++;
        }
        if (ctr > 0) {
            Grade_Adapter adapter = new Grade_Adapter(getApplicationContext(), lstgrad);
            list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            list.setAdapter(adapter);
        } else {
        }
    }

    public String gradesInit(String schoolyear, String semester) {
        String data = "";
        List<Grades> grades = database.gradesDao().findPerSem(username, schoolyear, semester);
        for(int n = 0; n < grades.size(); n++)
        {
            data += "<tr bgcolor=\"white\"><td>"+ grades.get(n).getSubject() +"</td><td>"+ grades.get(n).getFaculty() +"</td><td>"+ grades.get(n).getPrelim() +"</td><td>"+ grades.get(n).getMidterm() +"</td><td>"+ grades.get(n).getFinals() +"</td><td>"+ grades.get(n).getFinalgrades() +"</td><td>"+ grades.get(n).getAverage() +"</td><td>"+ grades.get(n).getStatus() +"</td></tr>";
        }
        return data;
    }


    public void updateGrades() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Grade_new.this, getResources().getString(R.string.server_path) + "grades.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    database.gradesDao().deleteAll();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            database.gradesDao().insert(new Grades(object.getString("studentid"),object.getString("subject"),object.getString("faculty"),object.getString("prelim"),object.getString("midterm"),object.getString("finals"),object.getString("finalgrades"),object.getString("average"),object.getString("status"),object.getString("schoolyear"),object.getString("semester")));
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
        new HttpRequest().doPost(Grade_new.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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
                            views.setBackgroundDrawable(bitmapDrawable);
                            views.setBackgroundColor(android.graphics.Color.TRANSPARENT);
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
