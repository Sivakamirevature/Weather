package com.example.weather_application;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;


public class SharedPreferencesDemo extends AppCompatActivity {
    SharedPreferences sp, gsp;
    TextView userName,password;
    private String encrypted_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences_demo);
        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File UserDetails;
        EncryptedSharedPreferences sharedPreferences;
        try {
           sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences
                    .create(
                            encrypted_preferences,
                            masterKeyAlias,
                            this,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("userName","Shivi");
        ed.putString("password","Pass123$");
        ed.commit();
    }

    public void check1(View view){
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        String uName, pass;
        uName = userName.getText().toString();
        pass = password.getText().toString();

        gsp = getSharedPreferences("UserDetails",MODE_PRIVATE);
        String suserName = gsp.getString("userName","That user name is not available");
        String spassword = gsp.getString("password","");

        if(uName.equals(suserName)&&pass.equals(spassword)){
            Toast.makeText(this, "Login successfull", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Login Fail", Toast.LENGTH_LONG).show();
        }
    }
}