package com.example.hcc.interfaces;

import org.json.JSONObject;

public interface RequestCallback {
    void success(String response, JSONObject jsonObject);
}
