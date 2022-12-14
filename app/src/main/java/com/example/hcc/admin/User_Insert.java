package com.example.hcc.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hcc.Course_Detail;
import com.example.hcc.R;
import com.example.hcc.helper.Validation;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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
    TextInputEditText section;
    TextInputEditText password;

    TextInputLayout studentidlayout;
    TextInputLayout lastnamelayout;
    TextInputLayout firstnamelayout;
    TextInputLayout addresslayout;
    TextInputLayout contactlayout;
    TextInputLayout birthdaylayout;
    TextInputLayout emaillayout;
    TextInputLayout sectionlayout;
    TextInputLayout passwordlayout;

    TextView rolelayout,courselayout,yearlayout;

    Spinner course;
    Spinner year;
    Spinner role;
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
        password = findViewById(R.id.password);
        section = findViewById(R.id.section);

        studentidlayout = findViewById(R.id.studentidlayout);
        lastnamelayout = findViewById(R.id.lastnamelayout);
        firstnamelayout = findViewById(R.id.firstnamelayout);
        addresslayout = findViewById(R.id.addresslayout);
        contactlayout = findViewById(R.id.contactlayout);
        birthdaylayout = findViewById(R.id.birthdaylayout);
        emaillayout = findViewById(R.id.emaillayout);
        passwordlayout = findViewById(R.id.passwordlayout);
        sectionlayout = findViewById(R.id.sectionlayout);

        rolelayout = findViewById(R.id.rolelayout);
        courselayout = findViewById(R.id.courselayout);
        yearlayout = findViewById(R.id.yearlayout);

        studentid.addTextChangedListener(new Validation(studentid,studentid,studentidlayout));
        lastname.addTextChangedListener(new Validation(lastname,lastname,lastnamelayout));
        firstname.addTextChangedListener(new Validation(firstname,firstname,firstnamelayout));
        address.addTextChangedListener(new Validation(address,address,addresslayout));
        contact.addTextChangedListener(new Validation(contact,contact,contactlayout));
        birthday.addTextChangedListener(new Validation(birthday,birthday,birthdaylayout));
        email.addTextChangedListener(new Validation(email,email,emaillayout));
        password.addTextChangedListener(new Validation(password,password,passwordlayout));
        section.addTextChangedListener(new Validation(section,section,sectionlayout));

        course = findViewById(R.id.course);
        year = findViewById(R.id.year);
        role = findViewById(R.id.role);
        theme();

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(User_Insert.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String nd = "" + dayOfMonth;
                                String nm = "" + monthOfYear ;
                                if ( dayOfMonth < 10 ){
                                    nd = "0"+ dayOfMonth;
                                }
                                if ( (monthOfYear + 1) < 10){
                                    nm = "0"+ ( monthOfYear +1 ) ;
                                }else {
                                    nm = ""+ ( monthOfYear + 1) ;
                                }
                                birthday.setText(year + "/" + nm + "/" + nd);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bill = new Intent(User_Insert.this, User.class);
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

    public int selectYear(String year) {
        int year_number = 0;
        switch (year) {
            case "First":
                year_number = 1;
                break;
            case "Second":
                year_number = 2;
                break;
            case "Third":
                year_number = 3;
                break;
            case "Fourth":
                year_number = 4;
                break;
        }
        return year_number;
    }

    public void insertUser() {
        /*  */
        if (role.getSelectedItem().toString().equals("Select a role.")) {
            //todo
            rolelayout.setVisibility(View.VISIBLE);
        } else {
            rolelayout.setVisibility(View.GONE);
        }
        if (course.getSelectedItem().toString().equals("Select a course.")) {
            //todo
            courselayout.setVisibility(View.VISIBLE);
        } else {
            courselayout.setVisibility(View.GONE);
        }
        if (year.getSelectedItem().toString().equals("Select a year.")) {
            //todo
            yearlayout.setVisibility(View.VISIBLE);
        } else {
            yearlayout.setVisibility(View.GONE);
        }

        if (!role.getSelectedItem().toString().equals("Select a role.") && !course.getSelectedItem().toString().equals("Select a role.") && !year.getSelectedItem().toString().equals("Select a role.") && new Validation(studentid,studentid,studentidlayout).validateNumber() && new Validation(lastname,lastname,lastnamelayout).validateNames() && new Validation(firstname,firstname,firstnamelayout).validateNames() && new Validation(address,address,addresslayout).validateText() && new Validation(contact,contact,contactlayout).validatePhoneNumber() && new Validation(birthday,birthday,birthdaylayout).validateBirthday() && new Validation(email,email,emaillayout).validateEmail() && new Validation(password,password,passwordlayout).validatePassword() && new Validation(section,section,sectionlayout).validateNames()) {
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
                jsonParams.put("course",course.getSelectedItem().toString());
                jsonParams.put("year",selectYear(year.getSelectedItem().toString()));
                jsonParams.put("section",section.getText().toString());
                jsonParams.put("role",role.getSelectedItem().toString());
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
}
