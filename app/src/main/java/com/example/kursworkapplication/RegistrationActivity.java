package com.example.kursworkapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kursworkapplication.data.User;
import com.example.kursworkapplication.data.UserData;

public class RegistrationActivity extends AppCompatActivity {
    UserData touristData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        touristData = new UserData(this);
        Button reg = findViewById(R.id.registrationButton);
        TextView login = findViewById(R.id.regEditTextLogin);
        TextView password = findViewById(R.id.regEditTextPassword);
        reg.setOnClickListener(v -> {
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("") ||
                    log.length() < 4 || pas.length() < 4){
                Toast.makeText(this, "Введите логин и пароль, минимальная длина логина и пароля 4 символа",
                        Toast.LENGTH_LONG).show();
            }
            else{
                User tourist = new User();
                tourist.setLogin(log);
                tourist.setPassword(pas);
                tourist.setRole("user");
                boolean ret = touristData.registration(tourist);
                if (ret){
                    Intent data = new Intent();
                    setResult(Activity.RESULT_OK, data);
                    Toast.makeText(this, "Регистрация успешна",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(this, "Пользователь с таким логином уже существует",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}