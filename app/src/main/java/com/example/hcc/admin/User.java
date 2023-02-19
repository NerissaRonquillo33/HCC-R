package com.example.hcc.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.hcc.Admin;
import com.example.hcc.R;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User extends AppCompatActivity {

    String username;
    List<User_Item> lstBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user);
        ImageView prev = findViewById(R.id.back2main);
        lstBills = new ArrayList<>();
        username = getIntent().getStringExtra("username");
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(User.this, Admin.class);
                dashboard.putExtra("username",username);
                startActivity(dashboard);
            }
        });
        theme();
        listBills();
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
        new HttpRequest().doPost(User.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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

    public void listBills() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(User.this, getResources().getString(R.string.server_path) + "admin/users.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {

                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            lstBills.add(new User_Item(object.getInt("idStudent"),object.getInt("idUser"),object.getString("studentid"),object.getString("firstname"),object.getString("lastname"),object.getString("address"),object.getString("contact"),object.getString("birthday"),object.getString("email"),object.getString("course"),object.getString("year"),object.getString("section"),object.getString("userid"),object.getString("role")));

                        }
                    } catch (JSONException e) {
                        //todo
                    }
                    RecyclerView list = findViewById(R.id.users_holder);
                    User_Adapter adapter = new User_Adapter(User.this, lstBills);
                    list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    list.setAdapter(adapter);
                }
            }
        });
    }

    public void showMenuBillInsert(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add:
                        //todo
                        addUser();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.user_insert);
        popup.show();
    }

    public void addUser() {
        Intent dashboard = new Intent(User.this, User_Insert.class);
        dashboard.putExtra("username",username);
        startActivity(dashboard);
    }
}
