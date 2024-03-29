package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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


public class Grade extends AppCompatActivity {

    List<Grade_Item> lstgrade1,lstgrade2,lstgrade3,lstgrade4,lstgrade5,lstgrade6,lstgrade7,lstgrade8;
    DBHelper dbHelper;
    List<String> schoolyearList;
    List<String> semesterList;
    Spinner spinnerSchoolYr;
    Spinner spinnerSem;
    String username,role;
    Database database;
    TextView firstyear,firstsem,secondsem,firstyear2,firstsem2,secondsem2,firstyear3,firstsem3,secondsem3,firstyear4,firstsem4,secondsem4;
    RecyclerView grade_firstyrsem_holder,grade_firstyr_second_sem_holder,grade_firstyrsem_holder2,grade_firstyr_second_sem_holder2,grade_firstyrsem_holder3,grade_firstyr_second_sem_holder3,grade_firstyrsem_holder4,grade_firstyr_second_sem_holder4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade);
        ImageView prev = findViewById(R.id.back2main);
//        spinnerSchoolYr = findViewById(R.id.schoolyear);
//        spinnerSem = findViewById(R.id.semester);
//        Button search = findViewById(R.id.search);

        firstyear = findViewById(R.id.firstyear);
        firstsem = findViewById(R.id.firstsem);
        secondsem = findViewById(R.id.secondsem);
        firstyear2 = findViewById(R.id.firstyear2);
        firstsem2 = findViewById(R.id.firstsem2);
        secondsem2 = findViewById(R.id.secondsem2);
        firstyear3 = findViewById(R.id.firstyear3);
        firstsem3 = findViewById(R.id.firstsem3);
        secondsem3 = findViewById(R.id.secondsem3);
        firstyear4 = findViewById(R.id.firstyear4);
        firstsem4 = findViewById(R.id.firstsem4);
        secondsem4 = findViewById(R.id.secondsem4);

        grade_firstyrsem_holder = findViewById(R.id.grade_firstyrsem_holder);
        grade_firstyr_second_sem_holder = findViewById(R.id.grade_firstyr_second_sem_holder);
        grade_firstyrsem_holder2 = findViewById(R.id.grade_firstyrsem_holder2);
        grade_firstyr_second_sem_holder2 = findViewById(R.id.grade_firstyr_second_sem_holder2);
        grade_firstyrsem_holder3 = findViewById(R.id.grade_firstyrsem_holder3);
        grade_firstyr_second_sem_holder3 = findViewById(R.id.grade_firstyr_second_sem_holder3);
        grade_firstyrsem_holder4 = findViewById(R.id.grade_firstyrsem_holder4);
        grade_firstyr_second_sem_holder4 = findViewById(R.id.grade_firstyr_second_sem_holder4);

        lstgrade1 = new ArrayList<>();
        lstgrade2 = new ArrayList<>();
        lstgrade3 = new ArrayList<>();
        lstgrade4 = new ArrayList<>();
        lstgrade5 = new ArrayList<>();
        lstgrade6 = new ArrayList<>();
        lstgrade7 = new ArrayList<>();
        lstgrade8 = new ArrayList<>();
        schoolyearList = new ArrayList<String>();
        semesterList = new ArrayList<String>();
//        dbHelper = new DBHelper(this, lstgrade);
        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        TextView nameofstudent = findViewById(R.id.nameofstudent);
        String studentname = getIntent().getStringExtra("nameofstudent");
        String year = getIntent().getStringExtra("year");
        String section = getIntent().getStringExtra("section");
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
                dashboard.putExtra("year",year);
                dashboard.putExtra("section",section);
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
        GradesSelection("2019/2020","First", R.id.grade_firstyrsem_holder, lstgrade1,firstyear,firstsem,secondsem);
        GradesSelection("2019/2020","Second", R.id.grade_firstyr_second_sem_holder, lstgrade2,firstyear,firstsem,secondsem);
        GradesSelection("2020/2021","First", R.id.grade_firstyrsem_holder2, lstgrade3,firstyear2,firstsem2,secondsem2);
        GradesSelection("2020/2021","Second", R.id.grade_firstyr_second_sem_holder2, lstgrade4,firstyear2,firstsem2,secondsem2);
        GradesSelection("2021/2022","First", R.id.grade_firstyrsem_holder3, lstgrade5,firstyear3,firstsem3,secondsem3);
        GradesSelection("2021/2022","Second", R.id.grade_firstyr_second_sem_holder3, lstgrade6,firstyear3,firstsem3,secondsem3);
        GradesSelection("2022/2023","First", R.id.grade_firstyrsem_holder4, lstgrade7,firstyear4,firstsem4,secondsem4);
        GradesSelection("2022/2023","Second", R.id.grade_firstyr_second_sem_holder4, lstgrade8,firstyear4,firstsem4,secondsem4);
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
//            firstyear.setVisibility(View.GONE);
//            firstsem.setVisibility(View.GONE);
//            secondsem.setVisibility(View.GONE);
//            list.setVisibility(View.GONE);
        }
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
