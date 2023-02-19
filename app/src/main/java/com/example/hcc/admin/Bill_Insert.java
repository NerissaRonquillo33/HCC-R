package com.example.hcc.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hcc.R;
import com.example.hcc.helper.Validation;
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
    TextInputLayout tflayout;
    TextInputLayout lailayout;
    TextInputLayout rflayout;
    TextInputLayout cpflayout;
    TextInputLayout gaclayout;
    TextInputLayout siflayout;
    TextInputLayout shlayout;
    TextInputLayout splayout;
    TextInputLayout inslayout;
    TextInputLayout talayout;
    TextInputLayout dslayout;
    TextInputLayout nalayout;
    TextInputLayout ccplayout;
    TextInputLayout oblayout;
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
        tflayout = findViewById(R.id.tflayout);
        lailayout = findViewById(R.id.lailayout);
        rflayout = findViewById(R.id.rflayout);
        cpflayout = findViewById(R.id.cpflayout);
        gaclayout = findViewById(R.id.gaclayout);
        siflayout = findViewById(R.id.siflayout);
        shlayout = findViewById(R.id.shlayout);
        splayout = findViewById(R.id.splayout);
        inslayout = findViewById(R.id.inslayout);
        talayout = findViewById(R.id.talayout);
        dslayout = findViewById(R.id.dslayout);
        nalayout = findViewById(R.id.nalayout);
        ccplayout = findViewById(R.id.ccplayout);
        oblayout = findViewById(R.id.oblayout);
        list = new ArrayList<String>();
        list2 = new ArrayList<String>();
        String role = getIntent().getStringExtra("role");
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bill = new Intent(Bill_Insert.this, Bill.class);
                bill.putExtra("role",role);
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
        tf.addTextChangedListener(new Validation(tf,tf,tflayout));
        lai.addTextChangedListener(new Validation(lai,lai,lailayout));
        rf.addTextChangedListener(new Validation(rf,rf,rflayout));
        cpf.addTextChangedListener(new Validation(cpf,cpf,cpflayout));
        gac.addTextChangedListener(new Validation(gac,gac,gaclayout));
        sif.addTextChangedListener(new Validation(sif,sif,siflayout));
        sh.addTextChangedListener(new Validation(sh,sh,shlayout));
        sp.addTextChangedListener(new Validation(sp,sp,splayout));
        ins.addTextChangedListener(new Validation(ins,ins,inslayout));
        ta.addTextChangedListener(new Validation(ta,ta,talayout));
        ds.addTextChangedListener(new Validation(ds,ds,dslayout));
        na.addTextChangedListener(new Validation(na,na,nalayout));
        ccp.addTextChangedListener(new Validation(ccp,ccp,ccplayout));
        ob.addTextChangedListener(new Validation(ob,ob,oblayout));
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
        new HttpRequest().doPost(Bill_Insert.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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

    public void listStudents() {
        /* Courses list */
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Bill_Insert.this, getResources().getString(R.string.server_path) + "admin/students.php", jsonParams, new RequestCallback() {
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
        if (new Validation(tf,tf,tflayout).validateNumber() && new Validation(lai,lai,lailayout).validateNumber() && new Validation(rf,rf,rflayout).validateNumber() && new Validation(cpf,cpf,cpflayout).validateNumber() && new Validation(gac,gac,gaclayout).validateNumber() && new Validation(sif,sif,siflayout).validateNumber() && new Validation(sh,sh,shlayout).validateNumber() && new Validation(sp,sp,splayout).validateNumber() && new Validation(ins,ins,inslayout).validateNumber() && new Validation(ta,ta,talayout).validateNumber() && new Validation(ds,ds,dslayout).validateNumber() && new Validation(na,na,nalayout).validateNumber() && new Validation(ccp,ccp,ccplayout).validateNumber() && new Validation(ob,ob,oblayout).validateNumber() && list2.size() > 0) {
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
}
