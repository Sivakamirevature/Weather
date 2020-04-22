package com.example.weather_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class CardLayout_Example extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ExampleItem> cities = new ArrayList<ExampleItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_layout__example);
        ArrayList<ExampleItem> exampleList = new ArrayList<>();

        //exampleList.add(new ExampleItem(R.drawable.ic_android_black_24dp, "Line 1", "Line 2"));

        updateListView();
        mRecyclerView = findViewById(R.id.cityRecycleView1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager((this));
        mAdapter = new RecyclerViewAdapter(cities);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void updateListView() {
        RestCall cityObj = new RestCall();
        try {
            String response = cityObj.execute("https://api.openaq.org/v1/cities?country=IN").get();
            Log.i("Content DATA :", response);
            JSONObject jsonObject = new JSONObject(response);
            String results = jsonObject.getString("results");
            Log.i("\nData", results);
            JSONArray array = new JSONArray(results);
            String city = "";
            for (int i = 0; i < array.length(); i++) {
                JSONObject part = array.getJSONObject(i);
                city = part.getString("city");
                cities.add(new ExampleItem(R.drawable.ic_android_black_24dp, city, "Indian City"));
            }
            makeText(this, String.valueOf(cities), LENGTH_SHORT).show();
            Log.i("cities Array: ", String.valueOf(cities));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}