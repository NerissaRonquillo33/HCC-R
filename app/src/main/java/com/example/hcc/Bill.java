package com.example.hcc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class Bill extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill);
        ImageView prev = findViewById(R.id.back2main);
        /* Back to main */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(Bill.this, Dashboard.class);
                startActivity(dashboard);
            }
        });
    }
}
