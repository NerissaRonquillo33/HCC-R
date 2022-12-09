package com.example.hcc.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hcc.Course_Detail;
import com.example.hcc.R;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User_Insert extends AppCompatActivity {
    Button insert;
    TextInputEditText studentid;
    TextInputEditText lastname;
    TextInputEditText firstname;
    TextInputEditText address;
    TextInputEditText contact;
    TextInputEditText birthday;
    TextInputEditText email;
    TextInputEditText course;
    TextInputEditText year;
    TextInputEditText section;
    TextInputEditText role;
    TextInputEditText password;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_insert);
        ImageView prev = findViewById(R.id.back2main);
        insert = findViewById(R.id.insert);
        status = findViewById(R.id.status);
        studentid = findViewById(R.id.studentid);
        lastname = findViewById(R.id.lastname);
        firstname = findViewById(R.id.firstname);
        address = findViewById(R.id.address);
        contact = findViewById(R.id.contact);
        birthday = findViewById(R.id.birthday);
        email = findViewById(R.id.email);
        course = findViewById(R.id.course);
        year = findViewById(R.id.year);
        section = findViewById(R.id.section);
        role = findViewById(R.id.role);
        password = findViewById(R.id.password);
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bill = new Intent(User_Insert.this, Bill.class);
                startActivity(bill);
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();
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
        new HttpRequest().doPost(User_Insert.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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

    public void insertUser() {
        /*  */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("studentid",studentid.getText().toString());
            jsonParams.put("lastname",lastname.getText().toString());
            jsonParams.put("firstname",firstname.getText().toString());
            jsonParams.put("address",address.getText().toString());
            jsonParams.put("contact",contact.getText().toString());
            jsonParams.put("birthday",birthday.getText().toString());
            jsonParams.put("email",email.getText().toString());
            jsonParams.put("course",course.getText().toString());
            jsonParams.put("year",year.getText().toString());
            jsonParams.put("section",section.getText().toString());
            jsonParams.put("role",role.getText().toString());
            jsonParams.put("password",password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(User_Insert.this, getResources().getString(R.string.server_path) + "admin/user-insert.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                Log.i("gggggg", response + " asdsa");
                if (response.equals("success")) {
                    // todo
                    status.setVisibility(View.VISIBLE);
                    status.setText("Successful insert!");
                    status.setTextColor(Color.parseColor("#FF03DAC5"));
                }
            }
        });
    }
}
