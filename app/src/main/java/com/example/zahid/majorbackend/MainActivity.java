package com.example.zahid.majorbackend;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String url = "http://192.168.137.1/FramesPie/wp-json/jwt-auth/v1/token";
    Gson gson;
    Button mybutton;
    Map<String,Object> mapPost;
    JSONObject jsonBody = new JSONObject();
    String Admin = "Zahid";
    String Password = "zahid.shakeel4@yahoo.com";
    String token = "";
    String auth = "";
    RequestQueue rQueue;
    Woocommerce wc = new Woocommerce();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            jsonBody.put("username", Admin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonBody.put("password", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();
        rQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //gson = new Gson();
                //mapPost = (Map<String,Object>)gson.fromJson(response,Map.class);
                //String name = (String)mapPost.get("link");
                //responseText.append(name);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                token = wc.GetData(response,"token");
                auth = "Bearer " + token;
                Woocommerce.auth = auth;
                //responseText.append(auth);
                Intent intent = new Intent(MainActivity.this,ProductsActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Some error occurred", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        rQueue.add(request);
    }
}
