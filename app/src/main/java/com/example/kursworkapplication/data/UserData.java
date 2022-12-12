package com.example.kursworkapplication.data;

import android.content.Context;

import com.example.kursworkapplication.data.DBs.UserDB;

import java.util.List;

public class UserData {
    private UserDB userDB;

    public UserData(Context context){
        userDB = new UserDB(context);
    }

    public boolean registration(User user){
        try{
            boolean  ret = userDB.registration(user);
            return ret;
        }
        catch(Exception ex){
            return false;
        }
    }

    public User authorization(User user){
        try{
            User  ret = userDB.authorization(user);
            return ret;
        }
        catch(Exception ex){
            return null;
        }
    }

    public List<User> readAll(String login, String role){
        User user = new User();
        user.setLogin(login);
        user.setRole(role);
        return userDB.readAll(user);
    }
}
