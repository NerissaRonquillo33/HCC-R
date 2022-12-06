package com.example.hcc.http_request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hcc.interfaces.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequest {
    public void doPost(Context context, String url, JSONObject jsonParams, RequestCallback requestCallback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(
                com.android.volley.Request.Method.POST,url,jsonParams,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    Log.i("Response", status);
                    requestCallback.success(status, response);
                } catch (JSONException e) {
                    requestCallback.success("error", response);
                }

            }
        },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        requestCallback.success("error", null);

                    }
                });
        queue.add(request);
    }
    public void doGet(Context context, String url, RequestCallback requestCallback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (!response.getString("students").equals("null")) {
                        requestCallback.success("success", response);
                    }
                    requestCallback.success("none", response);
                } catch (JSONException e) {
                    requestCallback.success("error", response);
                }

            }
        },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        requestCallback.success("error", null);

                    }
                });
        queue.add(request);
    }
}
