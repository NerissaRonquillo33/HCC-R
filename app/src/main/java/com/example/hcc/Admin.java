package com.example.hcc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.example.hcc.admin.Bill;

public class Admin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        Button logout = findViewById(R.id.logout);
//        CardView announcement = findViewById(R.id.announcement);
        CardView bill = findViewById(R.id.bill);
//        CardView grade = findViewById(R.id.grade);
//        CardView courses = findViewById(R.id.courses);
        CardView students = findViewById(R.id.students);
        CardView about = findViewById(R.id.about);
        String username = getIntent().getStringExtra("username");
        /* Bill */
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent announce = new Intent(Admin.this, Bill.class);
                announce.putExtra("username",username);
                startActivity(announce);
            }
        });
        /* Logout */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to logout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Intent login = new Intent(Admin.this, Login.class);
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
}
