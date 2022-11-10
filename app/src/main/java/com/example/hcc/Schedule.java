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

import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends AppCompatActivity {

    List<Schedule_Item> lstSchedule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        String username = getIntent().getStringExtra("username");
        ImageView prev = findViewById(R.id.back2main);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Schedule.this, Dashboard.class);
                startActivity(dashboard);
            }
        });
        /* Schedule list */
        lstSchedule = new ArrayList<>();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Schedule.this, getResources().getString(R.string.server_path) + "/api/schedules.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                Log.i("Responv", response);
                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        lstSchedule.clear();
                        for(int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject object = jsonArray.getJSONObject(n);
                            lstSchedule.add(new Schedule_Item(Integer.parseInt(object.getString("id")),object.getString("title"), R.drawable.me_time));
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                    RecyclerView list = findViewById(R.id.schedule_holder);
                    Schedule_Adapter adapter = new Schedule_Adapter(getApplicationContext(), lstSchedule);
                    list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    list.setAdapter(adapter);
                }
            }
        });
    }
}
