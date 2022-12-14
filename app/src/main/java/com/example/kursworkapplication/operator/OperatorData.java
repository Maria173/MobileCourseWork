package com.example.kursworkapplication.operator;

import android.content.Context;

import com.example.kursworkapplication.operator.databaseOperator.OperatorDB;

import java.util.List;

public class OperatorData {
    private OperatorDB operatorDB;

    public OperatorData(Context context){
        operatorDB = new OperatorDB(context);
    }

    public boolean registration(Operator operator){
        try{
            boolean  ret = operatorDB.registration(operator);
            return ret;
        }
        catch(Exception ex){
            return false;
        }
    }

    public Operator authorization(Operator operator){
        try{
            Operator  ret = operatorDB.authorization(operator);
            return ret;
        }
        catch(Exception ex){
            return null;
        }
    }

    public List<Operator> readAll(String login, String role){
        Operator operator = new Operator();
        operator.setLogin(login);
        operator.setRole(role);
        return operatorDB.readAll(operator);
    }
}
