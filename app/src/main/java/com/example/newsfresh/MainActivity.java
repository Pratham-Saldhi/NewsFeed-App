package com.example.newsfresh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String URL_DATA = "http://newsapi.org/v2/everything?q=bitcoin&from=2020-12-04&sortBy=publishedAt&apiKey=9b383a9418024ccab5c2415f5fca1c64";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);


        ArrayList<Data> list = new ArrayList<Data>();
        fetchData(list);
        // Create an instance of DataAdapter
        DataAdapter adapter = new DataAdapter(list, this);

        recyclerView.setAdapter(adapter);


        // If we want to horizontal layout
       // LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void fetchData(final ArrayList<Data> list) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading news..");
        progressDialog.show();

        // Instantiate the RequestQueue.
       // String url = "http://newsapi.org/v2/everything?q=bitcoin&from=2020-12-04&sortBy=publishedAt&apiKey=9b383a9418024ccab5c2415f5fca1c64";

        // Request a json object response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_DATA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                // Response is the JSONObject

                try {
                    JSONArray jsonArray = response.getJSONArray("articles");

                    for(int i = 0; i < jsonArray.length();i++){
                        JSONObject o  = jsonArray.getJSONObject(i);
                        Data data = new Data(o.getString("title"), o.getString("author"), o.getString("url"), o.getString("urlToImage"));
                        list.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // if some error occurs
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error Occurred",Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
    }
}