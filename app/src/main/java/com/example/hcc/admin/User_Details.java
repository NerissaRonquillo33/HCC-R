package com.example.hcc.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hcc.Course_Detail;
import com.example.hcc.R;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class User_Details extends AppCompatActivity {
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
    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText role;
    String lastname1;
    String studentid1;
    String firstname1;
    String address1;
    String contact1;
    String birthday1;
    String email1;
    String course1;
    String year1;
    String section1;
    String username1;
    String role1;
    int idStudent,idUser;
    ScrollView scrollView;
    ProgressBar progressBar;
    ImageView dot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_details);
        ImageView prev = findViewById(R.id.back2main);
        dot = findViewById(R.id.dot);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.userdetails);
        idStudent = getIntent().getIntExtra("idStudent",0);
        idUser = getIntent().getIntExtra("idUser",0);
        studentid1 = getIntent().getStringExtra("studentid");
        lastname1 = getIntent().getStringExtra("lastname");
        firstname1 = getIntent().getStringExtra("firstname");
        address1 = getIntent().getStringExtra("address");
        contact1 = getIntent().getStringExtra("contact");
        birthday1 = getIntent().getStringExtra("birthday");
        email1 = getIntent().getStringExtra("email");
        course1 = getIntent().getStringExtra("course");
        year1 = getIntent().getStringExtra("year");
        section1 = getIntent().getStringExtra("section");
        username1 = getIntent().getStringExtra("username");
        role1 = getIntent().getStringExtra("role");
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
        username = findViewById(R.id.username);
        role = findViewById(R.id.role);
        password = findViewById(R.id.password);
        studentid.setText(studentid1);
        lastname.setText(lastname1);
        firstname.setText(firstname1);
        address.setText(address1);
        contact.setText(contact1);
        birthday.setText(birthday1);
        email.setText(email1);
        course.setText(course1);
        year.setText(year1);
        section.setText(section1);
        username.setText(username1);
        role.setText(role1);
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(User_Details.this, User.class);
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        if (bundle.get(key) != null) {
                            dashboard.putExtra(key,bundle.get(key).toString());
                        }
                    }
                }
                startActivity(dashboard);
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
        new HttpRequest().doPost(User_Details.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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

    public void showMenuBill(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.update:
                        //todo
                        updateBill();
                        return true;
                    case R.id.delete:
                        //todo
                        deleteBill();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.bill_edit);
        popup.show();
    }

    public void updateBill() {
        /*  */
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        dot.setVisibility(View.INVISIBLE);
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("action","update");
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
            jsonParams.put("username",username.getText().toString());
            jsonParams.put("role",role.getText().toString());
            jsonParams.put("password",password.getText().toString());
            jsonParams.put("idStudent",idStudent);
            jsonParams.put("idUser",idUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(User_Details.this, getResources().getString(R.string.server_path) + "admin/user-update.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    // todo
                }
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                dot.setVisibility(View.VISIBLE);
            }
        });
    }

    public void deleteBill() {
        /*  */
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        dot.setVisibility(View.INVISIBLE);
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("action", "delete");
            jsonParams.put("idStudent",idStudent);
            jsonParams.put("idUser",idUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(User_Details.this, getResources().getString(R.string.server_path) + "admin/user-update.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    // todo
                    Intent dashboard = new Intent(User_Details.this, User.class);
                    startActivity(dashboard);
                }
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                dot.setVisibility(View.VISIBLE);
            }
        });
    }
}
