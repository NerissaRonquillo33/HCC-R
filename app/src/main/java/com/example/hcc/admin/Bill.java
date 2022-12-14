package com.example.hcc.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.hcc.Admin;
import com.example.hcc.Course;
import com.example.hcc.Course_Adapter;
import com.example.hcc.Course_Detail;
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

    String username,role;
    List<Bill_Item> lstBills, lstTmp, lstSearch;
    RecyclerView list;
    Bill_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_billing);
        ImageView prev = findViewById(R.id.back2main);
        ImageView dot = findViewById(R.id.dot);
        TextView title = findViewById(R.id.title);
        SearchView search = findViewById(R.id.search);
        list = findViewById(R.id.billing_holder);
        lstBills = new ArrayList<>();
        lstTmp = new ArrayList<>();
        lstSearch = new ArrayList<>();
        adapter = new Bill_Adapter(Bill.this, lstBills);
        list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        list.setAdapter(adapter);
        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Bill.this, Admin.class);
                dashboard.putExtra("username",username);
                dashboard.putExtra("role",role);
                startActivity(dashboard);
            }
        });
        search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev.setVisibility(View.GONE);
                dot.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
            }
        });
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                prev.setVisibility(View.VISIBLE);
                dot.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                lstBills.clear();
                lstBills.addAll(lstTmp);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                lstSearch.clear();
                lstBills.clear();
                lstBills.addAll(lstTmp);
                for (int i = 0; i < lstBills.size(); i++) {
                    if (lstBills.get(i).getStudentid().contains(s)) {
                        lstSearch.add(lstBills.get(i));
                    }
                }
                lstBills.clear();
                lstBills.addAll(lstSearch);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
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
        new HttpRequest().doPost(Bill.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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
                    lstTmp.addAll(lstBills);
                    adapter.notifyDataSetChanged();
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
        dashboard.putExtra("role",role);
        dashboard.putExtra("username",username);
        startActivity(dashboard);
    }
}
