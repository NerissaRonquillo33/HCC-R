package com.example.hcc.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hcc.Course;
import com.example.hcc.Course_Detail;
import com.example.hcc.Dashboard;
import com.example.hcc.R;
import com.example.hcc.helper.Validation;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Schedules;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Bill_Details extends AppCompatActivity {
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
    String studentid;
    String tuitionfee;
    String learnandins;
    String regfee;
    String compprossfee;
    String guidandcouns;
    String schoolidfee;
    String studenthand;
    String schoolfab;
    String insurance;
    String totalass;
    String discount;
    String netass;
    String cashcheckpay;
    String balance;
    String studentname;
    String course;
    String role;
    int id;
    ScrollView scrollView;
    ProgressBar progressBar;
    ImageView dot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_billing_details);
        ImageView prev = findViewById(R.id.back2main);
        dot = findViewById(R.id.dot);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.billdetails);
        id = getIntent().getIntExtra("id",0);
        studentid = getIntent().getStringExtra("studentid");
        tuitionfee = getIntent().getStringExtra("tuitionfee");
        learnandins = getIntent().getStringExtra("learnandins");
        regfee = getIntent().getStringExtra("regfee");
        compprossfee = getIntent().getStringExtra("compprossfee");
        guidandcouns = getIntent().getStringExtra("guidandcouns");
        schoolidfee = getIntent().getStringExtra("schoolidfee");
        studenthand = getIntent().getStringExtra("studenthand");
        schoolfab = getIntent().getStringExtra("schoolfab");
        insurance = getIntent().getStringExtra("insurance");
        totalass = getIntent().getStringExtra("totalass");
        discount = getIntent().getStringExtra("discount");
        netass = getIntent().getStringExtra("netass");
        cashcheckpay = getIntent().getStringExtra("cashcheckpay");
        balance = getIntent().getStringExtra("balance");
        studentname = getIntent().getStringExtra("studentname");
        course = getIntent().getStringExtra("course");
        role = getIntent().getStringExtra("role");
        TextView studentnamea = findViewById(R.id.studentname);
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
        studentnamea.setText(studentname.equals("null, null") ? studentid : studentname);
        tf.setText(tuitionfee);
        lai.setText(learnandins);
        rf.setText(regfee);
        cpf.setText(compprossfee);
        gac.setText(guidandcouns);
        sif.setText(schoolidfee);
        sh.setText(studenthand);
        sp.setText(schoolfab);
        ins.setText(insurance);
        ta.setText(totalass);
        ds.setText(discount);
        na.setText(netass);
        ccp.setText(cashcheckpay);
        ob.setText(balance);
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
        theme();
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Bill_Details.this, Bill.class);
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        if (bundle.get(key) != null) {
                            dashboard.putExtra(key,bundle.get(key).toString());
                        }
                    }
                }
                startActivity(dashboard);
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
        new HttpRequest().doPost(Bill_Details.this, getResources().getString(R.string.server_path) + "settings.php", jsonParams, new RequestCallback() {
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

    public void showMenuBill(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.update:
                        //todo
                        updateBill();
                        return true;
                    case R.id.delete:
                        //todo
                        deleteBill();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.bill_edit);
        popup.show();
    }

    public void updateBill() {
        /*  */
        if (new Validation(tf,tf,tflayout).validateNumber() && new Validation(lai,lai,lailayout).validateNumber() && new Validation(rf,rf,rflayout).validateNumber() && new Validation(cpf,cpf,cpflayout).validateNumber() && new Validation(gac,gac,gaclayout).validateNumber() && new Validation(sif,sif,siflayout).validateNumber() && new Validation(sh,sh,shlayout).validateNumber() && new Validation(sp,sp,splayout).validateNumber() && new Validation(ins,ins,inslayout).validateNumber() && new Validation(ta,ta,talayout).validateNumber() && new Validation(ds,ds,dslayout).validateNumber() && new Validation(na,na,nalayout).validateNumber() && new Validation(ccp,ccp,ccplayout).validateNumber() && new Validation(ob,ob,oblayout).validateNumber()) {
            progressBar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            dot.setVisibility(View.INVISIBLE);
            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("secret_key", "secret_key");
                jsonParams.put("action", "update");
                jsonParams.put("tuitionfee", tf.getText().toString());
                jsonParams.put("learnandins", lai.getText().toString());
                jsonParams.put("regfee", rf.getText().toString());
                jsonParams.put("compprossfee", cpf.getText().toString());
                jsonParams.put("guidandcouns", gac.getText().toString());
                jsonParams.put("schoolidfee", sif.getText().toString());
                jsonParams.put("studenthand", sh.getText().toString());
                jsonParams.put("schoolfab", sp.getText().toString());
                jsonParams.put("insurance", ins.getText().toString());
                jsonParams.put("totalass", ta.getText().toString());
                jsonParams.put("discount", ds.getText().toString());
                jsonParams.put("netass", na.getText().toString());
                jsonParams.put("cashcheckpay", ccp.getText().toString());
                jsonParams.put("balance", ob.getText().toString());
                jsonParams.put("studentid", studentid);
                jsonParams.put("billingid", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new HttpRequest().doPost(Bill_Details.this, getResources().getString(R.string.server_path) + "admin/bill-update.php", jsonParams, new RequestCallback() {
                @Override
                public void success(String response, JSONObject jsonObject) {
                    if (response.equals("success")) {
                        // todo
                    }
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    dot.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void deleteBill() {
        /*  */
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        dot.setVisibility(View.INVISIBLE);
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("action", "delete");
            jsonParams.put("billingid",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Bill_Details.this, getResources().getString(R.string.server_path) + "admin/bill-update.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    // todo
                    Intent dashboard = new Intent(Bill_Details.this, Bill.class);
                    startActivity(dashboard);
                }
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                dot.setVisibility(View.VISIBLE);
            }
        });
    }
}
