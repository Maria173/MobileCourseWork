package com.example.kursworkapplication;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursworkapplication.operator.reportsOperator.ReportsOperatorLogic;
import com.example.kursworkapplication.operator.reportsOperator.toursGuides;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ReportToursGuidesActivity extends AppCompatActivity {

    ReportsOperatorLogic reportsLogic;
    String login = "";
    String role = "";

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PERMISSION_STORAGE = 101;
    public static final String FONT = "/assets/fonts/arial.ttf";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_tours_guides);
        BaseFont bf= null;
        try {
            bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font=new Font(bf,30,Font.NORMAL);

        SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        role = sPref.getString("role", "");

        reportsLogic = new ReportsOperatorLogic(this, login);

        Intent intent = getIntent();
        int[] orders = intent.getIntArrayExtra("guides");

        List<toursGuides> list = reportsLogic.getToursByGuides(login,
                Arrays.stream(orders).boxed().collect(Collectors.toList()));

        TableLayout table = findViewById(R.id.reportTourGuideTable);
        TableRow head = new TableRow(this);
        head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView h1 = new TextView(this);
        h1.setText("Гид");
        h1.setTextSize(18);
        h1.setTextColor(Color.parseColor("#9944cc"));
        h1.setPadding(10, 5, 0, 0);
        head.addView(h1);
        TextView h2 = new TextView(this);
        h2.setText("Тур");
        h2.setTextSize(18);
        h2.setTextColor(Color.parseColor("#9944cc"));
        h2.setPadding(20, 5, 0, 0);
        head.addView(h2);
        table.addView(head);
        final View vline = new View(this);
        vline.setLayoutParams(new
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
        vline.setBackgroundColor(Color.parseColor("#BC5454"));
        table.addView(vline);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        for(toursGuides trGui : list){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            TextView log = new TextView(this);
            log.setText(trGui.getGuide());
            log.setTextSize(18);
            log.setTextColor(Color.parseColor("#9944cc"));
            log.setMaxWidth(displayMetrics.widthPixels / 2);
            row.addView(log);
            TextView rol = new TextView(this);
            rol.setText(trGui.getTour());
            rol.setTextSize(18);
            rol.setTextColor(Color.parseColor("#9944cc"));
            rol.setMaxWidth(displayMetrics.widthPixels / 2);
            row.addView(rol);
            table.addView(row);

            final View vline1 = new View(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    1
            );
            params.setMargins(0, 5, 0, 5);
            vline1.setLayoutParams(params);
            vline1.setBackgroundColor(Color.parseColor("#BC5454"));
            table.addView(vline1);
        }

        Button save = findViewById(R.id.reportTourGuideToPdf);
        save.setOnClickListener(v -> {
            try {
                if (!checkPermission()) {
                    requestPermission();
                }
                if (!reportUsersActivity.PermissionUtils.hasPermissions(this)) {
                    reportUsersActivity.PermissionUtils.requestPermissions(this, PERMISSION_STORAGE);
                }
                String filename="toursByGuides.pdf";
                Document document=new Document();
                File root = new File(Environment.getExternalStorageDirectory(), "Reports");
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root,filename);  // generate pdf file in that directory
                PdfWriter.getInstance(document,new FileOutputStream(gpxfile));
                document.open();
                Paragraph p3=new Paragraph("Туры по гидам", font);
                document.add(p3);
                Paragraph p4=new Paragraph();
                p4.add(" ");
                document.add(p4);
                Paragraph p5=new Paragraph();
                p5.add(" ");
                document.add(p5);


                PdfPTable tablePdf = new PdfPTable(2);
                tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Гиды",font));
                tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,"Туры",font));

                for(toursGuides trGui : list){
                    tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,trGui.getGuide(),font));
                    tablePdf.addCell(new Phrase(Element.ALIGN_LEFT,trGui.getTour(),font));
                }

                document.add(tablePdf);
                document.addCreationDate();
                document.close();
                Toast.makeText(this, "Файл успешно создан", Toast.LENGTH_SHORT).show();


            }catch (Exception e){
                Toast.makeText(this, "Ошибка при работе с файлами",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public static class PermissionUtils {
        public static boolean hasPermissions(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return Environment.isExternalStorageManager();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                return true;
            }
        }

        public static void requestPermissions(Activity activity, int requestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", activity.getPackageName())));
                    activity.startActivityForResult(intent, requestCode);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activity.startActivityForResult(intent, requestCode);
                }
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        requestCode);
            }
        }
    }
}