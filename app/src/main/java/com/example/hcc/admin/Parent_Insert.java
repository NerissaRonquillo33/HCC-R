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

public class Parent_Insert extends AppCompatActivity {
    Button insert;
    TextInputEditText fullname;
    TextInputEditText student_id;
    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText email;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_parent_insert);
        ImageView prev = findViewById(R.id.back2main);
        insert = findViewById(R.id.insert);
        status = findViewById(R.id.status);
        fullname = findViewById(R.id.fullname);
        student_id = findViewById(R.id.student_id);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bill = new Intent(Parent_Insert.this, Parent.class);
                startActivity(bill);
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertParent();
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
        new HttpRequest().doPost(Parent_Insert.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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

    public void insertParent() {
        /*  */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("fullname",fullname.getText().toString());
            jsonParams.put("studentid",student_id.getText().toString());
            jsonParams.put("username",username.getText().toString());
            jsonParams.put("password",password.getText().toString());
            jsonParams.put("email",email.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Parent_Insert.this, getResources().getString(R.string.server_path) + "admin/parent-insert.php", jsonParams, new RequestCallback() {
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
