package com.example.test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import es.dmoral.toasty.Toasty;

public class Reports extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
        Button btn = findViewById(R.id.btn1_on_report);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String[] table_rows = {
                        DBHelper.TABLE_1_row1,
                        DBHelper.TABLE_1_row2,
                        DBHelper.TABLE_1_row3,
                        DBHelper.TABLE_1_row4,
                        DBHelper.TABLE_1_row5,
                        DBHelper.TABLE_1_row6
                };
                createPDF("Корҳои_механикӣ.pdf", DBHelper.TABLE_1,table_rows);
            }
        });


        BottomNavigationView bnv = findViewById(R.id.bottom_nav);
        bnv.setSelectedItemId(R.id.report);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.report:
                        return true;
                }
                return false;
            }
        });
    }

    public void createPDF(String file_name, String table_name, String[] table_rows) {

        Document doc = new Document();
        DBHelper db = new DBHelper(this);
//        requestPermission();


        try {
            String path = "/storage/emulated/0/Download/Dehqon";
            File dir = new File(path);
            if(!dir.exists()){
                Log.d("PDFCreator", "PDF Path: " + path);
                dir.mkdirs();
                dir.createNewFile();
            }

            File file = new File(dir, file_name);
            if(!file.exists()){
                Log.d("22222222222", "y");
                file.createNewFile();
                Log.d("PDFCreator", "PDF Path: " + file_name);
            }else{
                file.delete();
                file.createNewFile();
            }
            Toasty.info(Reports.this, "Файли шумо дар папкаи " + path, Toasty.LENGTH_LONG, true).show();
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);

            BaseFont urName_bold = BaseFont.createFont("res/font/academy_tajik.TTF", "Cp1251",BaseFont.EMBEDDED);
            Font urFontName_bold = new Font(urName_bold);

            doc.open();
            Paragraph p1 = new Paragraph(table_name);
            p1.setFont(urFontName_bold);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p1);



            PdfPTable table = new PdfPTable(6);
            table.addCell(new PdfPCell(new Phrase("Китъа", urFontName_bold)));
            table.addCell(new PdfPCell(new Phrase("Сана", urFontName_bold)));
            table.addCell(new PdfPCell(new Phrase("Фаъолият", urFontName_bold)));
            table.addCell(new PdfPCell(new Phrase("Ичрокунанда", urFontName_bold)));
            table.addCell(new PdfPCell(new Phrase("Шахси масъул", urFontName_bold)));
            table.addCell(new PdfPCell(new Phrase("Харочот", urFontName_bold)));

            SQLiteDatabase database = db.getWritableDatabase();
            Cursor cursor = database.query(table_name, null, null, null, null, null, null);
            if (cursor.moveToFirst()){
                int r1Index = cursor.getColumnIndex(table_rows[0]);
                int r2Index = cursor.getColumnIndex(table_rows[1]);
                int r3Index = cursor.getColumnIndex(table_rows[2]);
                int r4Index = cursor.getColumnIndex(table_rows[3]);
                int r5Index = cursor.getColumnIndex(table_rows[4]);
                int r6Index = cursor.getColumnIndex(table_rows[5]);
                do {
                    table.addCell(new PdfPCell(new Phrase(cursor.getString(r1Index), urFontName_bold)));
                    table.addCell(new PdfPCell(new Phrase(cursor.getString(r2Index), urFontName_bold)));
                    table.addCell(new PdfPCell(new Phrase(cursor.getString(r3Index), urFontName_bold)));
                    table.addCell(new PdfPCell(new Phrase(cursor.getString(r4Index), urFontName_bold)));
                    table.addCell(new PdfPCell(new Phrase(cursor.getString(r5Index), urFontName_bold)));
                    table.addCell(new PdfPCell(new Phrase(cursor.getString(r6Index), urFontName_bold)));

                } while (cursor.moveToNext());
            }
            cursor.close();
            doc.add((Element) table);
        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally
        {
            doc.close();
            db.close();
        }
    }


    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission","Permission is granted1");
                return true;
            } else {

                Log.v("permission","Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permission","Permission is granted3");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission","Permission is granted2");
                return true;
            } else {

                Log.v("permission","Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permission","Permission is granted3");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
            break;
        }
    }
}
