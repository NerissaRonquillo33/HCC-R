package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Grades;
import com.example.hcc.models.Schedules;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Grade extends AppCompatActivity {

    List<Grade_Item> lstgrade;
    DBHelper dbHelper;
    List<String> schoolyearList;
    List<String> semesterList;
    Spinner spinnerSchoolYr;
    Spinner spinnerSem;
    String username;
    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade);
        ImageView prev = findViewById(R.id.back2main);
//        spinnerSchoolYr = findViewById(R.id.schoolyear);
//        spinnerSem = findViewById(R.id.semester);
//        Button search = findViewById(R.id.search);
        lstgrade = new ArrayList<>();
        schoolyearList = new ArrayList<String>();
        semesterList = new ArrayList<String>();
//        dbHelper = new DBHelper(this, lstgrade);
        username = getIntent().getStringExtra("username");
        database = Database.getInstance(Grade.this);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Grade.this, Dashboard.class);
                dashboard.putExtra("username",username);
                startActivity(dashboard);
            }
        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String schoolyr = spinnerSchoolYr.getSelectedItem().toString();
//                String semtr = spinnerSem.getSelectedItem().toString();
//                GradesList(username, schoolyr, semtr);
//            }
//        });
        GradesSelection();
    }

    public void GradesSelection() {
        List<Grades> grades = database.gradesDao().find(username);
        for(int n = 0; n < grades.size(); n++)
        {
            lstgrade.add(new Grade_Item(grades.get(n).getId(),grades.get(n).getSubject(),grades.get(n).getFaculty(),grades.get(n).getPrelim(),grades.get(n).getMidterm(),grades.get(n).getFinals(),grades.get(n).getFinalgrades(),grades.get(n).getAverage(),grades.get(n).getStatus()));
        }
        RecyclerView list = findViewById(R.id.grade_holder);
        Grade_Adapter adapter = new Grade_Adapter(getApplicationContext(), lstgrade);
        list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        list.setAdapter(adapter);
    }

    public void GradesSelection2() {
        /* Courses list */
//        schoolyearList.clear();
//        semesterList.clear();
//        JSONObject jsonParams = new JSONObject();
//        try {
//            jsonParams.put("secret_key", "secret_key");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        new HttpRequest().doPost(Grade.this, getResources().getString(R.string.server_path) + "grades-selection.php", jsonParams, new RequestCallback() {
//            @Override
//            public void success(String response, JSONObject jsonObject) {
//                if (response.equals("success")) {
//                    try {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("results");
//                        JSONArray jsonArray = jsonObject1.getJSONArray("schoolyear");
//                        JSONArray jsonArray2 = jsonObject1.getJSONArray("semester");
//                        for(int n = 0; n < jsonArray.length(); n++)
//                        {
//                            schoolyearList.add(jsonArray.getString(n));
//                        }
//                        for(int n = 0; n < jsonArray2.length(); n++)
//                        {
//                            semesterList.add(jsonArray2.getString(n));
//                        }
//                    } catch (JSONException e) {
//                        //todo
//                    }
//                    ArrayAdapter<String> adapterSYL = new ArrayAdapter<String>(Grade.this, android.R.layout.simple_spinner_item, schoolyearList);
//                    ArrayAdapter<String> adapterS = new ArrayAdapter<String>(Grade.this, android.R.layout.simple_spinner_item, semesterList);
//                    adapterSYL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerSchoolYr.setAdapter(adapterSYL);
//                    spinnerSem.setAdapter(adapterS);
//                }
//            }
//        });
    }

    public void GradesList(String username, String schoolyear, String semester) {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
            jsonParams.put("schoolyear", schoolyear);
            jsonParams.put("semester", semester);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Grade.this, getResources().getString(R.string.server_path) + "grades.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        lstgrade.clear();
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            lstgrade.add(new Grade_Item(Integer.parseInt(object.getString("entryid")),object.getString("subject"),object.getString("faculty"),object.getString("prelim"),object.getString("midterm"),object.getString("finals"),object.getString("finalgrades"),object.getString("average"),object.getString("status")));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                    RecyclerView list = findViewById(R.id.grade_holder);
                    Grade_Adapter adapter = new Grade_Adapter(getApplicationContext(), lstgrade);
                    list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    list.setAdapter(adapter);
                }
            }
        });
    }
}
