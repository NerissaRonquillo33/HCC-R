package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class StudentInfo extends AppCompatActivity {

    String username;
    TextView fullname;
    TextView course;
    TextView contact;
    TextView gender;
    TextView dob;
    TextView age;
    ImageView profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        ImageView prev = findViewById(R.id.back2main);
        fullname = findViewById(R.id.fullname);
        course = findViewById(R.id.course);
        contact = findViewById(R.id.contact);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        age = findViewById(R.id.age);
        profile = findViewById(R.id.profile);
        username = getIntent().getStringExtra("username");

        info();

        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(StudentInfo.this, Dashboard.class);
                dashboard.putExtra("username",username);
                startActivity(dashboard);
            }
        });
    }

    public void info() {
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
                        fullname.setText(jsonObject.getString("lastname") + ", " + jsonObject.getString("firstname") + " " + jsonObject.getString("middlename"));
                        course.setText(jsonObject.getString("title"));
                        contact.setText(jsonObject.getString("contact"));
                        gender.setText(jsonObject.getString("gender"));
                        dob.setText(jsonObject.getString("dob"));
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
//                        Log.i("Base64", strBase64);
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
        builder.setView(customView);
        alertDialog = builder.create();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newpassword.getText().toString().equals(confirmpassword.getText().toString())) {
                    //todo
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
                                status.setVisibility(View.VISIBLE);
                                status.setText("Successfully updated");
                                status.setTextColor(Color.parseColor("#23ba36"));
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