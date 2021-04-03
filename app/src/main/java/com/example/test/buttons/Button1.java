package com.example.test.buttons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.DBHelper;
import com.example.test.R;

import es.dmoral.toasty.Toasty;

public class Button1 extends AppCompatActivity {

    EditText row1, row2, row3, row4, row5, row6;
    String r1, r2, r3, r4, r5, r6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button1);
        DBHelper db = new DBHelper(this);

        Button btn = findViewById(R.id.btn1_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row1 = findViewById(R.id.btn1_input_1);
                row2 = findViewById(R.id.btn1_input_2);
                row3 = findViewById(R.id.btn1_input_3);
                row4 = findViewById(R.id.btn1_input_4);
                row5 = findViewById(R.id.btn1_input_5);
                row6 = findViewById(R.id.btn1_input_6);

                r1 = row1.getText().toString();
                r2 = row2.getText().toString();
                r3 = row3.getText().toString();
                r4 = row4.getText().toString();
                r5 = row5.getText().toString();
                r6 = row6.getText().toString();

                if (r1.length() > 0 && r2.length() > 0 && r3.length() > 0 && r4.length() > 0 && r5.length() > 0 && r6.length() > 0) {
                    SQLiteDatabase database = db.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.TABLE_1_row1, r1);
                    contentValues.put(DBHelper.TABLE_1_row2, r2);
                    contentValues.put(DBHelper.TABLE_1_row3, r3);
                    contentValues.put(DBHelper.TABLE_1_row4, r4);
                    contentValues.put(DBHelper.TABLE_1_row5, r5);
                    contentValues.put(DBHelper.TABLE_1_row6, r6);
                    database.insert(DBHelper.TABLE_1, null, contentValues);
                    Toasty.success(Button1.this,
                            "Навишти шумо ба База дохил шуд",
                            Toast.LENGTH_LONG, true).show();
                }else{
                    Toasty.error(Button1.this,
                            "Бодиккат данныхоро дароред",
                            Toast.LENGTH_LONG,true).show();
                }
            }
        });
        db.close();
    }
}