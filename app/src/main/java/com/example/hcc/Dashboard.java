package com.example.hcc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Button logout = findViewById(R.id.logout);
        CardView aboutus = findViewById(R.id.aboutus);
        CardView stud_info = findViewById(R.id.stud_info);
        CardView schedule = findViewById(R.id.schedule);
        CardView course = findViewById(R.id.courses);
        CardView bill = findViewById(R.id.bill);
        CardView grade = findViewById(R.id.grade);
        String username = getIntent().getStringExtra("username");
        /* Grade */
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent grade = new Intent(Dashboard.this, Grade.class);
                startActivity(grade);
            }
        });
        /* Bill */
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bill = new Intent(Dashboard.this, Bill.class);
                startActivity(bill);
            }
        });
        /* Course */
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent course = new Intent(Dashboard.this, Course.class);
                course.putExtra("username",username);
                startActivity(course);
            }
        });
        /* Schedule */
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schedule = new Intent(Dashboard.this, Schedule.class);
                schedule.putExtra("username",username);
                startActivity(schedule);
            }
        });
        /* Student info */
        stud_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent student_info = new Intent(Dashboard.this, StudentInfo.class);
                student_info.putExtra("username",username);
                startActivity(student_info);
            }
        });
        /* About us */
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about();
            }
        });
        /* Logout */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to logout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Intent login = new Intent(Dashboard.this, Login.class);
                        startActivity(login);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void about() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        AlertDialog alertDialog;
        View customView = getLayoutInflater().inflate( R.layout.about, null);
        Button close = customView.findViewById(R.id.close);
        builder.setView(customView);
        alertDialog = builder.create();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
