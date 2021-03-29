package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class About extends AppCompatActivity {
    TextView name, farm_name, owner, phone, area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (!isFirstRun) {
            name = findViewById(R.id.usr_name);
            name.setText(
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getString("name", "NULL")
            );
            farm_name = findViewById(R.id.usr_farm_name);
            farm_name.setText(
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getString("farm_name", "NULL")
            );
            owner = findViewById(R.id.usr_owner);
            owner.setText(
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getString("owner", "NULL")
            );
            phone = findViewById(R.id.usr_phone);
            phone.setText(
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getString("phone", "NULL")
            );
            area = findViewById(R.id.usr_area);
            area.setText(
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                            .getString("area", "0")
            );
        }

        BottomNavigationView bnv = findViewById(R.id.bottom_nav);
        bnv.setSelectedItemId(R.id.about);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.report:
                        startActivity(new Intent(getApplicationContext(), Reports.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        return true;
                }
                return false;
            }
        });
    }
}