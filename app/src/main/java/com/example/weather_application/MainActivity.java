package com.example.weather_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showRecycleView(View view){
        Intent intent = new Intent(this,RecycView_Example.class);
        this.startActivity(intent);
    }

    public void showCardLayout(View view){
        Intent intent = new Intent(this,CardLayout_Example.class);
        this.startActivity(intent);
    }

    public void showSideNavigation(View view){
        Intent intent = new Intent(this,NavigationImpl.class);
        this.startActivity(intent);
    }

    public void sharedPreferenceMethod(View view){
        Intent intent = new Intent(this, SharedPreferenceImpl.class);
        this.startActivity(intent);
    }
}