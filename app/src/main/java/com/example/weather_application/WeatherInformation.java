package com.example.weather_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherInformation extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_information);
        String cname;
        Intent intent = getIntent();
        cname  = intent.getStringExtra("cityname");
        cityName = findViewById(R.id.cityName);
        cityName.setText(cname);
    }

    TextView cityName, result;

    public void search(View view) {
        Button searchButton;
        searchButton = findViewById(R.id.searchButton);
        result = findViewById(R.id.result);
        String city = cityName.getText().toString();
        String content;
        RestCall weather = new RestCall();
        try {
            content = weather.execute("https://openweathermap.org/data/2.5/weather?q="+ city + "&appid=439d4b804bc8187953eb36d2a8c26a02").get();
            Log.i("Content DATA :", content);
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemprature = jsonObject.getString("main");
            double visibility;
            Log.i("Weather Data", weatherData);
            JSONArray array = new JSONArray(weatherData);
            String main = "", description= "", temprature = "";
            for(int i = 0; i<array.length();i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }
            JSONObject mainPart = new JSONObject(mainTemprature);
            temprature = mainPart.getString("temp");
            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            Log.i("main",main);
            Log.i("description",description);
            Toast.makeText(this,"Temprature "+temprature,Toast.LENGTH_LONG).show();
            result.setText("Main: "+main+
                    "\nDescription: "+description +
                    "\nTemprature: "+temprature+
                    "\nVisibility: "+visibility/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
