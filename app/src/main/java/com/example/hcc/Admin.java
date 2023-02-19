package com.example.hcc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hcc.admin.About;
import com.example.hcc.admin.Bill;
import com.example.hcc.admin.Parent;
import com.example.hcc.admin.Theme;
import com.example.hcc.admin.User;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class Admin extends AppCompatActivity {
    LinearLayout containerbackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        Button logout = findViewById(R.id.logout);
//        CardView announcement = findViewById(R.id.announcement);
        CardView bill = findViewById(R.id.bill);
//        CardView grade = findViewById(R.id.grade);
//        CardView courses = findViewById(R.id.courses);
        CardView users = findViewById(R.id.users);
        CardView parents = findViewById(R.id.parents);
        CardView about = findViewById(R.id.about);
        CardView theme = findViewById(R.id.theme);
        containerbackground = findViewById(R.id.containerbackgroud);
        String username = getIntent().getStringExtra("username");
        String role = getIntent().getStringExtra("role");
        String notification = getIntent().getStringExtra("notification");
        if (notification != null) {
            Snackbar snackbar = Snackbar.make(containerbackground, "Welcome Admin!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        theme();
        /* Bill */
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent announce = new Intent(Admin.this, Bill.class);
                announce.putExtra("username",username);
                announce.putExtra("role",role);
                startActivity(announce);
            }
        });
        /* Users */
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent announce = new Intent(Admin.this, User.class);
                announce.putExtra("role",role);
                startActivity(announce);
            }
        });
        /* Parents */
        parents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent announce = new Intent(Admin.this, Parent.class);
                announce.putExtra("role",role);
                startActivity(announce);
            }
        });
        /* About */
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent announce = new Intent(Admin.this, About.class);
                announce.putExtra("username",username);
                announce.putExtra("role",role);
                startActivity(announce);
            }
        });
        /* Theme */
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent announce = new Intent(Admin.this, Theme.class);
                announce.putExtra("username",username);
                announce.putExtra("role",role);
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
    public void theme() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("name", "theme");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Admin.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
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
                    }
                }
            }
        });
    }
}
