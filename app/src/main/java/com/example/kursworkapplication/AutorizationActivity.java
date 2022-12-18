package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.data.UserData;

public class AutorizationActivity extends AppCompatActivity {

    UserData touristData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        touristData = new UserData(this);
        Button aut = findViewById(R.id.autorizationAut);
        Button reg = findViewById(R.id.autorizationReg);
        TextView login = findViewById(R.id.autEditTextLogin);
        TextView password = findViewById(R.id.autEditTextPassword);

        aut.setOnClickListener(v -> {
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("")){
                Toast.makeText(this, "Введите логин и пароль",
                        Toast.LENGTH_LONG).show();
            }
            else{
                User tourist = new User();
                tourist.setLogin(log);
                tourist.setPassword(pas);
                User ret = touristData.authorization(tourist);
                if (ret == null){
                    Toast.makeText(this, "Пара логин пароль не верна или такого пользователя нет",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString("login", ret.getLogin());
                    ed.putString("role", ret.getRole());
                    ed.commit();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Авторизация успешна",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        reg.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}