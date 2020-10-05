package com.example.memeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {
    ProgressBar prog;
    String crrurl=null;
       @Override
        protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);
           loadmeme();
       }

        public void loadmeme() {
            prog=findViewById(R.id.progbar);
            prog.setVisibility(View.VISIBLE);
            final ImageView img = (ImageView) findViewById(R.id.meme);
// ...

// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url = "https://meme-api.herokuapp.com/gimme";


// Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequestRequest = new JsonObjectRequest(Request.Method.GET, url, null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                         crrurl = response.getString("url");

                        Glide.with(MainActivity.this).load(crrurl).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                               prog.setVisibility(View.INVISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                prog.setVisibility(View.INVISIBLE);
                                return false;
                            }
                        }).into(img);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

// Add the request to the RequestQueue.
            queue.add(jsonObjectRequestRequest);

        }

    public void share(View view) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this cool meme from Reddit "+crrurl);
        startActivity(Intent.createChooser(intent,"Share this meme using .."));

    }

    public void next(View view) {
           loadmeme();
    }
}
