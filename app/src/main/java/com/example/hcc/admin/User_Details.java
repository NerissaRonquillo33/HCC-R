package com.example.hcc.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hcc.R;
import com.example.hcc.helper.Validation;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class User_Details extends AppCompatActivity {
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

    Spinner course;
    Spinner year;
    Spinner role;

    TextView rolelayout,courselayout,yearlayout;

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
        String[] roles = getResources().getStringArray(R.array.roles);
        String[] years = getResources().getStringArray(R.array.year);
        String[] courses = getResources().getStringArray(R.array.course);
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
        role1 = getIntent().getStringExtra("role");

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

        for (int i = 0; i < roles.length; i++) {
            if (roles[i].equals(role1)) {
                role.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < courses.length; i++) {
            if (courses[i].equals(course1)) {
                course.setSelection(i);
                break;
            }
        }
        year.setSelection(Integer.parseInt(year1));

        studentid.setText(studentid1);
        lastname.setText(lastname1);
        firstname.setText(firstname1);
        address.setText(address1);
        contact.setText(contact1);
        birthday.setText(birthday1);
        email.setText(email1);
        section.setText(section1);

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
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(User_Details.this,
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

    public void updateBill() {
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
                jsonParams.put("course",course.getSelectedItem().toString());
                jsonParams.put("year",selectYear(year.getSelectedItem().toString()));
                jsonParams.put("section",section.getText().toString());
                jsonParams.put("username",studentid.getText().toString());
                jsonParams.put("role",role.getSelectedItem().toString());
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
