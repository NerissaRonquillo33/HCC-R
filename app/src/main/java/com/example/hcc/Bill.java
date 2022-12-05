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
import com.example.hcc.models.Students;

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
        updateBilling();
        BillInfo();
    }

    public void BillInfo() {
        Bills bills = database.billsDao().find(username);
        if (bills != null) {
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
    }

    public void updateBilling() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
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
                            database.billsDao().insert(new Bills(object.getString("studentid"),object.getString("tuitionfee"),object.getString("learnandins"),object.getString("regfee"),object.getString("compprossfee"),object.getString("guidandcouns"),object.getString("schoolidfee"),object.getString("studenthand"),object.getString("schoolfab"),object.getString("insurance"),object.getString("totalass"),object.getString("discount"),object.getString("netass"),object.getString("cashcheckpay"),object.getString("balance")));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }
}
