package com.example.weather_application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class SharedPreferenceImpl extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference_impl);
        sp = getSharedPreferences("UserLogin",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName","Shivi");
        edit.putString("password","Pass123$");
        edit.commit();
        sp = getSharedPreferences("UserLogin",MODE_PRIVATE);
        String userName = sp.getString("userName","");
        Toast.makeText(this,"User Name: "+userName, Toast.LENGTH_LONG).show();
    }
}