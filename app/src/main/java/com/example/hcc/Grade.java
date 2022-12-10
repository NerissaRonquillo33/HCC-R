package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Bills;
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
    String username,role;
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
        role = getIntent().getStringExtra("role");
        TextView nameofstudent = findViewById(R.id.nameofstudent);
        String studentname = getIntent().getStringExtra("nameofstudent");
        if (role.equals("parent") && studentname != null) {
            nameofstudent.setText(studentname);
            nameofstudent.setVisibility(View.VISIBLE);
        }
        database = Database.getInstance(Grade.this);
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Grade.this, Dashboard.class);
                if (role != null && role.equals("parent")) {
                    dashboard = new Intent(Grade.this, Parent.class);
                }
                dashboard.putExtra("username",username);
                dashboard.putExtra("nameofstudent",studentname);
                dashboard.putExtra("role",role);
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
        updateGrades();
        GradesSelection();
    }

    public void GradesSelection() {
        List<Grades> grades = database.gradesDao().find(username);
        for(int n = 0; n < grades.size(); n++)
        {
            lstgrade.add(new Grade_Item(grades.get(n).getId(),grades.get(n).getSubject(),grades.get(n).getFaculty(),grades.get(n).getPrelim(),grades.get(n).getMidterm(),grades.get(n).getFinals(),grades.get(n).getFinalgrades(),grades.get(n).getAverage(),grades.get(n).getStatus(),grades.get(n).getSchoolyear(),grades.get(n).getSemester()));
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

    public void updateGrades() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Grade.this, getResources().getString(R.string.server_path) + "grades.php", jsonParams, new RequestCallback() {
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
        new HttpRequest().doPost(Grade.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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
