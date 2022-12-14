package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursworkapplication.operator.Operator;
import com.example.kursworkapplication.operator.OperatorData;

public class RegistrationOperatorActivity extends AppCompatActivity {
    OperatorData operatorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_operator);
        operatorData = new OperatorData(this);
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
                Operator operator = new Operator();
                operator.setLogin(log);
                operator.setPassword(pas);
                operator.setRole("user");
                boolean ret = operatorData.registration(operator);
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