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

public class Parent extends AppCompatActivity {

    String username;
    List<Parent_Item> lstBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_parent);
        ImageView prev = findViewById(R.id.back2main);
        lstBills = new ArrayList<>();
        username = getIntent().getStringExtra("username");
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Parent.this, Admin.class);
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
        new HttpRequest().doPost(Parent.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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
        new HttpRequest().doPost(Parent.this, getResources().getString(R.string.server_path) + "admin/parent.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {

                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            lstBills.add(new Parent_Item(object.getInt("id"),object.getString("student_id"),object.getString("slastname")+", "+object.getString("sfirstname"),object.getString("lastname"),object.getString("firstname"),object.getString("username"),object.getString("password"),object.getString("email")));

                        }
                    } catch (JSONException e) {
                        //todo
                    }
                    RecyclerView list = findViewById(R.id.parents_holder);
                    Parent_Adapter adapter = new Parent_Adapter(Parent.this, lstBills);
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
                        addParent();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.parent_insert);
        popup.show();
    }

    public void addParent() {
        Intent dashboard = new Intent(Parent.this, Parent_Insert.class);
        dashboard.putExtra("username",username);
        startActivity(dashboard);
    }
}
