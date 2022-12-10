package com.example.hcc.admin;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hcc.Admin;
import com.example.hcc.Course_Detail;
import com.example.hcc.Dashboard;
import com.example.hcc.R;
import com.example.hcc.StudentInfo;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class About extends AppCompatActivity {
    Button uploadpic, update;
    EditText description;
    ImageView image;
    TextView status;
    byte[] imagebytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_about);
        ImageView prev = findViewById(R.id.back2main);
        uploadpic = findViewById(R.id.uploadpic);
        update = findViewById(R.id.update);
        image = findViewById(R.id.image);
        description = findViewById(R.id.description);
        status = findViewById(R.id.status);
        imagebytes = new byte[0];
        String role = getIntent().getStringExtra("role");
        getAbout();
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(About.this, Admin.class);
                dashboard.putExtra("role",role);
                startActivity(dashboard);
            }
        });
        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAbout();
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
        new HttpRequest().doPost(About.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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

    public void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }

    public void updateAbout() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("name", "about");
            jsonParams.put("value", description.getText().toString());
            jsonParams.put("image_base64", Base64.encodeToString(imagebytes, 0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(About.this, getResources().getString(R.string.server_path) + "admin/setting.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    //todo
                    status.setVisibility(View.VISIBLE);
                    status.setText("Successful updated!");
                    status.setTextColor(Color.parseColor("#FF03DAC5"));
                }
            }
        });
    }
    public void getAbout() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("name", "about");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(About.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                Log.i("aaaaaa", response);
                if (response.equals("success")) {
                    try {
                        String about = jsonObject.getString("value");
                        description.setText(about);
                        if (jsonObject.getString("imageb64").length() > 300) {
                            byte[] decodedString = Base64.decode(jsonObject.getString("imageb64"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            image.setImageBitmap(decodedByte);
                            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                    } catch (JSONException e) {
                        //todo
                        Log.i("aaaaaa", e.toString());
                    }
                }
            }
        });
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
                        imagebytes = stream.toByteArray();
                        image.setImageBitmap(bitmap);
                        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
