package com.example.hcc.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hcc.R;
import com.example.hcc.helper.Validation;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parent_Insert extends AppCompatActivity {
    Button insert;
    List<String> list,list2;
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText email;

    TextInputLayout firstnamelayout;
    TextInputLayout lastnamelayout;
    TextInputLayout usernamelayout;
    TextInputLayout passwordlayout;
    TextInputLayout emaillayout;

    TextView student_idlayout;

    Spinner student_id;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_parent_insert);
        ImageView prev = findViewById(R.id.back2main);
        insert = findViewById(R.id.insert);
        status = findViewById(R.id.status);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        firstnamelayout = findViewById(R.id.firstnamelayout);
        lastnamelayout = findViewById(R.id.lastnamelayout);
        usernamelayout = findViewById(R.id.usernamelayout);
        passwordlayout = findViewById(R.id.passwordlayout);
        emaillayout = findViewById(R.id.emaillayout);

        student_id = findViewById(R.id.student_id);
        student_idlayout = findViewById(R.id.student_idlayout);

        firstname.addTextChangedListener(new Validation(firstname,firstname,firstnamelayout));
        lastname.addTextChangedListener(new Validation(lastname,lastname,lastnamelayout));
        username.addTextChangedListener(new Validation(username,username,usernamelayout));
        password.addTextChangedListener(new Validation(password,password,passwordlayout));
        email.addTextChangedListener(new Validation(email,email,emaillayout));

        list = new ArrayList<String>();
        list2 = new ArrayList<String>();
        listStudents();
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
        if (student_id.getSelectedItem().toString().equals("Select a StudentID.")) {
            //todo
            student_idlayout.setVisibility(View.VISIBLE);
        } else {
            student_idlayout.setVisibility(View.GONE);
        }
        if (!student_id.getSelectedItem().toString().equals("Select a StudentID.") && new Validation(firstname,firstname,firstnamelayout).validateNames() && new Validation(lastname,lastname,lastnamelayout).validateNames() && new Validation(username,username,usernamelayout).validateUsername() && new Validation(password,password,passwordlayout).validatePassword() && new Validation(email,email,emaillayout).validateEmail()) {
            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("secret_key", "secret_key");
                jsonParams.put("firstname",firstname.getText().toString());
                jsonParams.put("lastname",lastname.getText().toString());
                jsonParams.put("studentid",list2.get(student_id.getSelectedItemPosition()));
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

    public void listStudents() {
        /* Courses list */
        list.clear();
        list2.clear();
        list.add("Select a StudentID.");
        list2.add("Select a StudentID.");
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Parent_Insert.this, getResources().getString(R.string.server_path) + "admin/students.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            list.add( object.getString("lastname") + ", " + object.getString("firstname"));
                            list2.add( object.getString("studentid"));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Parent_Insert.this,
                            android.R.layout.simple_spinner_item, list);
                    adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    student_id.setAdapter(adp1);
                }
            }
        });
    }
}
