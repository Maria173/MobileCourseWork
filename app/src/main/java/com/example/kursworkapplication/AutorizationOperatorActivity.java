package com.example.kursworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursworkapplication.operator.Operator;
import com.example.kursworkapplication.operator.OperatorData;

public class AutorizationOperatorActivity extends AppCompatActivity {

    OperatorData operatorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization_operator);

        operatorData = new OperatorData(this);
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
                Operator operator = new Operator();
                operator.setLogin(log);
                operator.setPassword(pas);
                Operator ret = operatorData.authorization(operator);
                if (ret == null){
                    Toast.makeText(this, "Пара логин пароль не верна или такого пользователя нет",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences sPref = getSharedPreferences("Operator", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString("login", ret.getLogin());
                    ed.putString("role", ret.getRole());
                    ed.commit();
                    Intent intent = new Intent(this, MainOperatorActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Авторизация успешна",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        reg.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrationOperatorActivity.class);
            startActivity(intent);
//            String log = login.getText().toString();
//            String pas = password.getText().toString();
//            if (log.equals("") || pas.equals("") ||
//                    log.length() < 4 || pas.length() < 4){
//                Toast.makeText(this, "Введите логин и пароль, минимальная длина логина и пароля 4 символа",
//                        Toast.LENGTH_LONG).show();
//            }
//            else{
//                Operator operator = new Operator();
//                operator.setLogin(log);
//                operator.setPassword(pas);
//                operator.setRole("user");
//                boolean ret = operatorData.registration(operator);
//                if (ret){
//                    Toast.makeText(this, "Регистрация успешна",
//                            Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(this, "Пользователь с таким логином уже существует",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
        });
    }
}