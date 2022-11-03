package com.example.hcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        TextView denied = findViewById(R.id.status);
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    Intent dashboard = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(dashboard);
                }
                else {
                    denied.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}