package com.example.hcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Bills;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class Bill extends AppCompatActivity {

    TextView tf;
    TextView lai;
    TextView rf;
    TextView cpf;
    TextView gac;
    TextView sif;
    TextView sh;
    TextView sp;
    TextView ins;
    TextView ta;
    TextView ds;
    TextView na;
    TextView ccp;
    TextView ob;
    TextView nameofstudent;
    Database database;
    String username,role,studentname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill);
        ImageView prev = findViewById(R.id.back2main);
        nameofstudent = findViewById(R.id.nameofstudent);
        tf = findViewById(R.id.tf);
        lai = findViewById(R.id.lai);
        rf = findViewById(R.id.rf);
        cpf = findViewById(R.id.cpf);
        gac = findViewById(R.id.gac);
        sif = findViewById(R.id.sif);
        sh = findViewById(R.id.sh);
        sp = findViewById(R.id.sp);
        ins = findViewById(R.id.ins);
        ta = findViewById(R.id.ta);
        ds = findViewById(R.id.ds);
        na = findViewById(R.id.na);
        ccp = findViewById(R.id.ccp);
        ob = findViewById(R.id.ob);
        database = Database.getInstance(Bill.this);
        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        studentname = getIntent().getStringExtra("nameofstudent");
        if (role.equals("parent") && studentname != null) {
            nameofstudent.setText(studentname);
            nameofstudent.setVisibility(View.VISIBLE);
        }
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Bill.this, Dashboard.class);
                if (role != null && role.equals("parent")) {
                    dashboard = new Intent(Bill.this, Parent.class);
                }
                dashboard.putExtra("username",username);
                dashboard.putExtra("role",role);
                dashboard.putExtra("nameofstudent",studentname);
                startActivity(dashboard);
            }
        });
//        ob.setTextColor(Color.parseColor("#FF0000"));
        updateBilling(username);
        BillInfo();
    }

    public void BillInfo() {
        Bills bills = database.billsDao().find(username);
        if (bills != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(3);
            tf.setText(decimalFormat.format(Integer.parseInt(bills.getTuitionfee())));
            lai.setText(decimalFormat.format(Integer.parseInt(bills.getLearninginstructional())));
            rf.setText(decimalFormat.format(Integer.parseInt(bills.getRegistrationfee())));
            cpf.setText(decimalFormat.format(Integer.parseInt(bills.getComputerprocsngfee())));
            gac.setText(decimalFormat.format(Integer.parseInt(bills.getGuidancecounseling())));
            sif.setText(decimalFormat.format(Integer.parseInt(bills.getSchoolidfee())));
            sh.setText(decimalFormat.format(Integer.parseInt(bills.getStudenthand())));
            sp.setText(decimalFormat.format(Integer.parseInt(bills.getSchoolpublication())));
            ins.setText(decimalFormat.format(Integer.parseInt(bills.getInsurance())));
            ta.setText(decimalFormat.format(Integer.parseInt(bills.getTotalasses())));
            ds.setText(decimalFormat.format(Integer.parseInt(bills.getLessdiscountscholar())));
            na.setText(decimalFormat.format(Integer.parseInt(bills.getNetassessed())));
            ccp.setText(decimalFormat.format(Integer.parseInt(bills.getLesscashpayment())));
            ob.setText(decimalFormat.format(Integer.parseInt(bills.getOutstandingbal())));
//            if (Integer.parseInt(bills.getOutstandingbal()) <= 0) {
//                ob.setTextColor(Color.parseColor("#4E6EA9"));
//            }
        }
    }

    public void updateBilling(String username) {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
            Log.i("dddddd", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Bill.this, getResources().getString(R.string.server_path) + "billings.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                Log.i("dddddd", response);
                if (response.equals("success")) {
                    database.billsDao().deleteAll();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            database.billsDao().insert(new Bills(object.getString("studentid"),object.getString("tuitionfee"),object.getString("learnandins"),object.getString("regfee"),object.getString("compprossfee"),object.getString("guidandcouns"),object.getString("schoolidfee"),object.getString("studenthand"),object.getString("schoolfab"),object.getString("insurance"),object.getString("totalass"),object.getString("discount"),object.getString("netass"),object.getString("cashcheckpay"),object.getString("balance"),object.getInt("convertedTS"),object.getInt("billingid")));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                }
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
}
