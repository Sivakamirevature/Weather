package com.example.weather_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecycView_Example extends AppCompatActivity {
    ArrayList<String> cities = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view__example);
        updateListView();
    }
    //    Getting Indian Cites
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
                String temp = cityTemprature(city);
                //Toast.makeText(this, "heloo"+i, Toast.LENGTH_LONG).show();
                if(cities.indexOf(city)==-1 && temp!=null) {
                    cities.add(city);
                }
            }
            //for(int i=0;i<cities.size();i++) {
            //  if (temp == "") {
            //cities.add(city);
            //    Toast.makeText(this, "empty"+i, Toast.LENGTH_LONG).show();
            //}
            //}
            // Log.i("cities Array: ", String.valueOf(cities));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        citiesList = (ListView)findViewById(R.id.cityListView);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, cities);
//        citiesList.setAdapter(arrayAdapter);
//        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               Intent intent = new Intent(MainActivity.this, WeatherInformation.class);
//               intent.putExtra("cityname", cities.get(position));
//               startActivity(intent);
//            }
//        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cityRecycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecycView_Example.MyAdapter mAdapter = new RecycView_Example.MyAdapter(this,cities);
        recyclerView.setAdapter(mAdapter);
    }

    private static class MyAdapter extends RecyclerView.Adapter<RecycView_Example.MyAdapter.MyViewHolder> {
        private ArrayList<String> mDataset;
        private Context context;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textView;
            public MyViewHolder(TextView v) {
                super(v);
                textView = v;
            }
        }
        public MyAdapter(Context context1, ArrayList<String> myDataset) {
            context = context1;
            mDataset = myDataset;
        }

        @NonNull
        @Override
        public RecycView_Example.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_listview, parent, false);
            RecycView_Example.MyAdapter.MyViewHolder vh = new RecycView_Example.MyAdapter.MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RecycView_Example.MyAdapter.MyViewHolder holder, final int position) {
            holder.textView.setText(mDataset.get(position));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,WeatherInformation.class);
                    intent.putExtra("cityname",mDataset.get(position));
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            int size = mDataset.size();
            return mDataset.size();
        }
    }
    public String cityTemprature(String city) {
        String content;
        String temprature= "";
        RestCall weather = new RestCall();
        try {
            content = weather.execute("https://openweathermap.org/data/2.5/weather?q=" + city + "&appid=439d4b804bc8187953eb36d2a8c26a02").get();
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemprature = jsonObject.getString("main");
            JSONArray array = new JSONArray(weatherData);
            JSONObject mainPart = new JSONObject(mainTemprature);
            temprature = mainPart.getString("temp");
            Log.i("\n\nTemprature: ",temprature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temprature;
    }
}