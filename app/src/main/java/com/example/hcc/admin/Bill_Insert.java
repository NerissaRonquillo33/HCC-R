package com.example.hcc.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hcc.Admin;
import com.example.hcc.R;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bill_Insert extends AppCompatActivity {
    Spinner students;
    List<String> list,list2;
    Button insert;
    TextInputEditText tf;
    TextInputEditText lai;
    TextInputEditText rf;
    TextInputEditText cpf;
    TextInputEditText gac;
    TextInputEditText sif;
    TextInputEditText sh;
    TextInputEditText sp;
    TextInputEditText ins;
    TextInputEditText ta;
    TextInputEditText ds;
    TextInputEditText na;
    TextInputEditText ccp;
    TextInputEditText ob;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_billing_insert);
        ImageView prev = findViewById(R.id.back2main);
        students = findViewById(R.id.students);
        insert = findViewById(R.id.insert);
        status = findViewById(R.id.status);
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
        list = new ArrayList<String>();
        list2 = new ArrayList<String>();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bill = new Intent(Bill_Insert.this, Bill.class);
                startActivity(bill);
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBill();
            }
        });
        listStudents();
    }

    public void listStudents() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Bill_Insert.this, getResources().getString(R.string.server_path) + "students.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            list.add( object.getString("lastname") + ", " + object.getString("firstname"));
                            list2.add( object.getString("studentid"));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Bill_Insert.this,
                            android.R.layout.simple_spinner_item, list);
                    adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    students.setAdapter(adp1);
                }
            }
        });
    }

    public void insertBill() {
        /*  */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("tuitionfee",tf.getText().toString());
            jsonParams.put("learnandins",lai.getText().toString());
            jsonParams.put("regfee",rf.getText().toString());
            jsonParams.put("compprossfee",cpf.getText().toString());
            jsonParams.put("guidandcouns",gac.getText().toString());
            jsonParams.put("schoolidfee",sif.getText().toString());
            jsonParams.put("studenthand",sh.getText().toString());
            jsonParams.put("schoolfab",sp.getText().toString());
            jsonParams.put("insurance",ins.getText().toString());
            jsonParams.put("totalass",ta.getText().toString());
            jsonParams.put("discount",ds.getText().toString());
            jsonParams.put("netass",na.getText().toString());
            jsonParams.put("cashcheckpay",ccp.getText().toString());
            jsonParams.put("balance",ob.getText().toString());
            jsonParams.put("studentid",list2.get(students.getSelectedItemPosition()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Bill_Insert.this, getResources().getString(R.string.server_path) + "admin/bill-insert.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    // todo
                    status.setVisibility(View.VISIBLE);
                    status.setText("Successful insert!");
                    status.setTextColor(Color.parseColor("#FF03DAC5"));
                }
            }
        });
    }
}
