package com.example.hcc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.example.hcc.abstracts.Database;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class Parent extends AppCompatActivity {
    LinearLayout containerbackground;
    String role;
    Database database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent);
        Button logout = findViewById(R.id.logout);
        CardView aboutus = findViewById(R.id.aboutus);
        CardView schedule = findViewById(R.id.schedule);
        CardView course = findViewById(R.id.courses);
        CardView bill = findViewById(R.id.bill);
        CardView grade = findViewById(R.id.grade);
        CardView announcement = findViewById(R.id.announcement);
        containerbackground = findViewById(R.id.containerbackgroud);
        String username = getIntent().getStringExtra("username");
        String parentusername = getIntent().getStringExtra("parentusername");
        String fullname = getIntent().getStringExtra("fullname");
        String nameofstudent = getIntent().getStringExtra("nameofstudent");
        String notification = getIntent().getStringExtra("notification");
        role = getIntent().getStringExtra("role");
        String year = getIntent().getStringExtra("year");
        String section = getIntent().getStringExtra("section");
        if (notification != null) {
            database = Database.getInstance(Parent.this);
            sendToken(database.tokenDao().findOne().getDevice(), username);
            Snackbar snackbar = Snackbar.make(containerbackground, "Welcome Parent!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        /* Announcement */
        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent announce = new Intent(Parent.this, Announcement.class);
                announce.putExtra("username",username);
                announce.putExtra("role",role);
                announce.putExtra("nameofstudent",nameofstudent);
                announce.putExtra("year", year);
                announce.putExtra("section", section);
                startActivity(announce);
            }
        });
        /* Grade */
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent grade = new Intent(Parent.this, Grade_new.class);
                grade.putExtra("username",username);
                grade.putExtra("role",role);
                grade.putExtra("nameofstudent",nameofstudent);
                grade.putExtra("year", year);
                grade.putExtra("section", section);
                startActivity(grade);
            }
        });
        /* Bill */
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bill = new Intent(Parent.this, Bill.class);
                bill.putExtra("username",username);
                bill.putExtra("role",role);
                bill.putExtra("nameofstudent",nameofstudent);
                bill.putExtra("year", year);
                bill.putExtra("section", section);
                startActivity(bill);
            }
        });
        /* Course */
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent course = new Intent(Parent.this, Course.class);
                course.putExtra("username",username);
                course.putExtra("nameofstudent",nameofstudent);
                course.putExtra("role",role);
                course.putExtra("year", year);
                course.putExtra("section", section);
                startActivity(course);
            }
        });
        /* Schedule */
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schedule = new Intent(Parent.this, Schedule.class);
                schedule.putExtra("username",username);
                schedule.putExtra("nameofstudent",nameofstudent);
                schedule.putExtra("role",role);
                schedule.putExtra("year", year);
                schedule.putExtra("section", section);
                startActivity(schedule);
            }
        });
        /* About us */
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAbout();
            }
        });
        /* Logout */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Parent.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to logout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Intent login = new Intent(Parent.this, Login.class);
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
        theme();
    }

    public void about() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Parent.this);
        AlertDialog alertDialog;
        View customView = getLayoutInflater().inflate( R.layout.about, null);
        Button close = customView.findViewById(R.id.close);
        TextView abouttext = customView.findViewById(R.id.abouttext);
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

    public void updateAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Parent.this);
        AlertDialog alertDialog;
        View customView = getLayoutInflater().inflate( R.layout.about, null);
        Button close = customView.findViewById(R.id.close);
        TextView abouttext = customView.findViewById(R.id.abouttext);
        ImageView aboutlogo = customView.findViewById(R.id.aboutlogo);
        ProgressBar progressBar = customView.findViewById(R.id.progressBar);
        ScrollView aboutcontainer = customView.findViewById(R.id.aboutcontainer);
        progressBar.setVisibility(View.VISIBLE);
        aboutcontainer.setVisibility(View.GONE);
        builder.setView(customView);
        alertDialog = builder.create();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("name", "about");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Parent.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                Log.i("aaaaaa", response);
                if (response.equals("success")) {
                    try {
                        String about = jsonObject.getString("value");
                        if (jsonObject.getString("imageb64").length() > 300) {
                            byte[] decodedString = Base64.decode(jsonObject.getString("imageb64"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            aboutlogo.setImageBitmap(decodedByte);
                            aboutlogo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                        abouttext.setText(about.replaceAll("\\\\n","\n"));
                        progressBar.setVisibility(View.GONE);
                        aboutcontainer.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        //todo
                        Log.i("aaaaaa", e.toString());
                    }
                }
            }
        });
    }
    private void sendToken(String token, String username) {
        if (token.length() > 10 && username.length() > 1) {
            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("secret_key", "secret_key");
                jsonParams.put("token", token);
                jsonParams.put("username", username);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new HttpRequest().doPost(Parent.this, getResources().getString(R.string.server_path) + "token-saver.php", jsonParams, new RequestCallback() {
                @Override
                public void success(String response, JSONObject jsonObject) {
                    if (response.equals("success")) {
                        //nothing
//                    Toast.makeText(Dashboard.this, "Token: " + response, Toast.LENGTH_LONG).show();
                        Log.d("NotifierLogger", "Token: success " + response);
                    } else {
//                    Toast.makeText(Dashboard.this, "Token: " + response, Toast.LENGTH_LONG).show();
                        Log.d("NotifierLogger", "Token: not success " + response);
                    }
                }
            });
        }
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
        new HttpRequest().doPost(Parent.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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
                            containerbackground.setBackgroundDrawable(bitmapDrawable);
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
