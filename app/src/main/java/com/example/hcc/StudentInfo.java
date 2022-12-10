package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hcc.abstracts.Database;
import com.example.hcc.helper.MD5;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Students;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class StudentInfo extends AppCompatActivity {

    String username,role;
    TextView fullname;
    TextView course;
    TextView contact;
    TextView address;
    TextView dob;
    TextView age;
    ImageView profile;
    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        ImageView prev = findViewById(R.id.back2main);
        fullname = findViewById(R.id.fullname);
        course = findViewById(R.id.course);
        contact = findViewById(R.id.contact);
        address = findViewById(R.id.address_s);
        dob = findViewById(R.id.dob);
        age = findViewById(R.id.age);
        profile = findViewById(R.id.profile);
        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        database = Database.getInstance(StudentInfo.this);
        theme();
        info();

        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(StudentInfo.this, Dashboard.class);
                if (role != null && role.equals("parent")) {
                    dashboard = new Intent(StudentInfo.this, Parent.class);
                }
                dashboard.putExtra("username",username);
                dashboard.putExtra("role",role);
                startActivity(dashboard);
            }
        });
    }

    public void info() {
        Students students = database.studentsDao().findOne(username);
        fullname.setText(students.getLastname() + ", " + students.getFirstname());
        course.setText(students.getCourse());
        contact.setText(students.getContact());
        address.setText(students.getAddress());
        dob.setText(students.getBirthdate());
        age.setText("23");
        if (students.getImage().length > 300) {
            Bitmap decodedByte = BitmapFactory.decodeByteArray(students.getImage(), 0, students.getImage().length);
            profile.setImageBitmap(decodedByte);
            profile.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
        new HttpRequest().doPost(StudentInfo.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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

    public void info2() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(StudentInfo.this, getResources().getString(R.string.server_path) + "info.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        fullname.setText(jsonObject.getString("lastname") + ", " + jsonObject.getString("firstname"));
                        course.setText(jsonObject.getString("course"));
                        contact.setText(jsonObject.getString("contact"));
                        address.setText(jsonObject.getString("address"));
                        dob.setText(jsonObject.getString("birthday"));
                        age.setText(jsonObject.getString("age"));
                        if (jsonObject.getString("image").length() > 300) {
                            byte[] decodedString = Base64.decode(jsonObject.getString("image"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profile.setImageBitmap(decodedByte);
                            profile.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Log.i("Profile", "meron");
                        }
                    } catch (JSONException e) {
                        //todo
                        Log.i("Profile", String.valueOf(e));
                    }
                }
            }
        });
    }

    public static int getPerfectAgeInYears(String birthday) {
        String[] birthdayarr = birthday.split("-");
        if (birthdayarr.length != 3) {
            return 0;
        }
        int year = Integer.parseInt(birthdayarr[0]);
        int month = Integer.parseInt(birthdayarr[1]);
        int date = Integer.parseInt(birthdayarr[2]);

        Calendar dobCalendar = Calendar.getInstance();

        dobCalendar.set(Calendar.YEAR, year);
        dobCalendar.set(Calendar.MONTH, month);
        dobCalendar.set(Calendar.DATE, date);

        int ageInteger = 0;

        Calendar today = Calendar.getInstance();

        ageInteger = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH)) {

            if (today.get(Calendar.DAY_OF_MONTH) < dobCalendar.get(Calendar.DAY_OF_MONTH)) {

                ageInteger = ageInteger - 1;
            }

        } else if (today.get(Calendar.MONTH) < dobCalendar.get(Calendar.MONTH)) {

            ageInteger = ageInteger - 1;

        }

        return ageInteger;
    }

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.uploadpic:
                        //todo
                        Log.i("Menusss", "upload");
                        uploadImage();
                        return true;
                    case R.id.changepassword:
                        //todo
                        Log.i("Menusss", "changepass");
                        changePassword();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.info);
        popup.show();
    }

    public void uploadImage() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == 200) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        String strBase64= Base64.encodeToString(byteArray, 0);
                        JSONObject jsonParams = new JSONObject();
                        try {
                            jsonParams.put("secret_key", "secret_key");
                            jsonParams.put("username", username);
                            jsonParams.put("image_base64", strBase64);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new HttpRequest().doPost(StudentInfo.this, getResources().getString(R.string.server_path) + "upload.php", jsonParams, new RequestCallback() {
                            @Override
                            public void success(String response, JSONObject jsonObject) {
                                if (response.equals("success")) {
                                    //todo
                                    database.studentsDao().updateImage(username, stream.toByteArray());
                                    info();
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void changePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfo.this);
        AlertDialog alertDialog;
        View customView = getLayoutInflater().inflate( R.layout.changepassword, null);
        EditText newpassword = customView.findViewById(R.id.newpassword);
        EditText confirmpassword = customView.findViewById(R.id.condfirmpassword);
        TextView status = customView.findViewById(R.id.status);
        Button cancel = customView.findViewById(R.id.cancel);
        Button update = customView.findViewById(R.id.update);
        ProgressBar progressBar = customView.findViewById(R.id.progress_loader);
        builder.setView(customView);
        alertDialog = builder.create();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newpassword.getText().toString().equals(confirmpassword.getText().toString())) {
                    //todo
                    update.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    JSONObject jsonParams = new JSONObject();
                    try {
                        jsonParams.put("secret_key", "secret_key");
                        jsonParams.put("username", username);
                        jsonParams.put("password", newpassword.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new HttpRequest().doPost(StudentInfo.this, getResources().getString(R.string.server_path) + "update-password.php", jsonParams, new RequestCallback() {
                        @Override
                        public void success(String response, JSONObject jsonObject) {
                            if (response.equals("success")) {
                                database.studentsDao().updatePassword(username, new MD5().encrypt(newpassword.getText().toString()));
                                status.setVisibility(View.VISIBLE);
                                status.setText("Successfully updated");
                                status.setTextColor(Color.parseColor("#23ba36"));
                                update.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                else {
                    status.setVisibility(View.VISIBLE);
                    status.setText("Password not match");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
