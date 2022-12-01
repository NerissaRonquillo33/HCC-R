package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Bills;
import com.example.hcc.models.Schedules;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


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
    Database database;
    String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill);
        ImageView prev = findViewById(R.id.back2main);
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
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Bill.this, Dashboard.class);
                dashboard.putExtra("username",username);
                startActivity(dashboard);
            }
        });
        BillInfo();
    }

    public void BillInfo() {
        Bills bills = database.billsDao().find(username);
        tf.setText(bills.getTuitionfee());
        lai.setText(bills.getLearninginstructional());
        rf.setText(bills.getRegistrationfee());
        cpf.setText(bills.getComputerprocsngfee());
        gac.setText(bills.getGuidancecounseling());
        sif.setText(bills.getSchoolidfee());
        sh.setText(bills.getStudenthand());
        sp.setText(bills.getSchoolpublication());
        ins.setText(bills.getInsurance());
        ta.setText(bills.getTotalasses());
        ds.setText(bills.getLessdiscountscholar());
        na.setText(bills.getNetassessed());
        ccp.setText(bills.getLesscashpayment());
        ob.setText(bills.getOutstandingbal());
    }

    public void BillInfo2(String username) {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Bill.this, getResources().getString(R.string.server_path) + "billing.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                Log.i("dddddd", response);
                if (response.equals("success")) {
                    try {
                        tf.setText(jsonObject.getString("tuitionfee"));
                        lai.setText(jsonObject.getString("learnandins"));
                        rf.setText(jsonObject.getString("regfee"));
                        cpf.setText(jsonObject.getString("compprossfee"));
                        gac.setText(jsonObject.getString("guidandcouns"));
                        sif.setText(jsonObject.getString("schoolidfee"));
                        sh.setText(jsonObject.getString("studenthand"));
                        sp.setText(jsonObject.getString("schoolfab"));
                        ins.setText(jsonObject.getString("insurance"));
                        ta.setText(jsonObject.getString("totalass"));
                        ds.setText(jsonObject.getString("discount"));
                        na.setText(jsonObject.getString("netass"));
                        ccp.setText(jsonObject.getString("cashcheckpay"));
                        ob.setText(jsonObject.getString("balance"));
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }
}
