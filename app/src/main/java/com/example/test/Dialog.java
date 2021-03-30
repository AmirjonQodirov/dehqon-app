package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.buttons.Button1;

public class Dialog extends AppCompatActivity {

    EditText name, farm_name, area,  phone;
    String cons_name, cons_farm_name, cons_phone, cons_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Button btn = findViewById(R.id.fill_constant);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = findViewById(R.id.input_name);
                farm_name = findViewById(R.id.farm_name);
                area = findViewById(R.id.area_input);
                phone = findViewById(R.id.telephone);

                cons_name = name.getText().toString();
                cons_farm_name = farm_name.getText().toString();
                cons_phone = phone.getText().toString();
                cons_area = area.getText().toString();

                if (cons_area.length() > 0 && cons_phone.length() > 0 && cons_name.length() > 0 && cons_farm_name.length() > 0){

                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).apply();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().
                            putString("name", cons_name).apply();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().
                            putString("farm_name", cons_farm_name).apply();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().
                            putString("phone",cons_phone).apply();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().
                            putString("area", cons_area).apply();
                    Intent intent = new Intent(Dialog.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


}