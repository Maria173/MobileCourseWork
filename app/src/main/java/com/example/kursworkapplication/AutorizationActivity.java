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

    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        userData = new UserData(this);
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
                User user = new User();
                user.setLogin(log);
                user.setPassword(pas);
                User ret = userData.authorization(user);
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
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("") ||
                log.length() < 4 || pas.length() < 4){
                Toast.makeText(this, "Введите логин и пароль, минимальная длина логина и пароля 4 символа",
                        Toast.LENGTH_LONG).show();
            }
            else{
                User user = new User();
                user.setLogin(log);
                user.setPassword(pas);
                user.setRole("user");
                boolean ret = userData.registration(user);
                if (ret){
                    Toast.makeText(this, "Регистрация успешна",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "Пользователь с таким логином уже существует",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}