package com.example.hcc.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.hcc.Admin;
import com.example.hcc.Course;
import com.example.hcc.Course_Adapter;
import com.example.hcc.Course_Item;
import com.example.hcc.Dashboard;
import com.example.hcc.R;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Schedules;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bill extends AppCompatActivity {

    String username;
    List<Bill_Item> lstBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_billing);
        ImageView prev = findViewById(R.id.back2main);
        lstBills = new ArrayList<>();
        username = getIntent().getStringExtra("username");
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Bill.this, Admin.class);
                dashboard.putExtra("username",username);
                startActivity(dashboard);
            }
        });
        listBills();
    }

    public void listBills() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Bill.this, getResources().getString(R.string.server_path) + "admin/bills.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            lstBills.add(new Bill_Item(object.getInt("billingid"),object.getString("studentid"),object.getString("tuitionfee"),object.getString("learnandins"),object.getString("regfee"),object.getString("compprossfee"),object.getString("guidandcouns"),object.getString("schoolidfee"),object.getString("studenthand"),object.getString("schoolfab"),object.getString("insurance"),object.getString("totalass"),object.getString("discount"),object.getString("netass"),object.getString("cashcheckpay"),object.getString("balance"),object.getString("lastname") + ", " + object.getString("firstname"),object.getString("course")));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                    RecyclerView list = findViewById(R.id.billing_holder);
                    Bill_Adapter adapter = new Bill_Adapter(Bill.this, lstBills);
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
                        addBill();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.bill_insert);
        popup.show();
    }

    public void addBill() {
        Intent dashboard = new Intent(Bill.this, Bill_Insert.class);
        dashboard.putExtra("username",username);
        startActivity(dashboard);
    }
}
