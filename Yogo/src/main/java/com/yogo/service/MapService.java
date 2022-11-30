package com.yogo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.yogo.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yogo.model.Coordinates;
import okhttp3.*;

@Service
public class MapService {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

    public HashMap<String, Object> findPoint(String pointFind) throws IOException {
        ArrayList<ArrayList<Coordinates>> res = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();

        Request request = new Request.Builder().url(UrlUtils.getUrlFindPoint(pointFind)).get().build();

        Response response = httpClient.newCall(request).execute();

        JSONObject data = new JSONObject(response.body().string());
        JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
        Integer estimatedTotal = resourceSets.getInt("estimatedTotal");
        if (estimatedTotal > 0) {
            for (int i = 0; i < estimatedTotal; i++) {
                JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(i);
                JSONObject point = resources.getJSONObject("point");
                JSONArray coordinates = point.getJSONArray("coordinates");
                ArrayList<Coordinates> c = gson.fromJson(coordinates.toString(), ArrayList.class);
                res.add(c);
            }
        }
        map.put("coordinates", res);
        return map;
    }

    public HashMap<String, Object> findRoute(String p0, String p1) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        Request request = new Request.Builder().url(UrlUtils.getUrlFindRoute(p0, p1)).get().build();

        Response response = httpClient.newCall(request).execute();

        JSONObject data = new JSONObject(response.body().string());
        JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
        JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(0);
        JSONObject routePath = resources.getJSONObject("routePath");
        JSONObject line = routePath.getJSONObject("line");
        JSONArray coordinates = line.getJSONArray("coordinates");

        ArrayList<?> jsonArray = gson.fromJson(coordinates.toString(), ArrayList.class);
        ArrayList<Coordinates> res = (ArrayList<Coordinates>) jsonArray;

        map.put("coordinates", res);
        return map;
    }

    public HashMap<String, Object> findPointName(BigDecimal lat, BigDecimal lon) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        Request request = new Request.Builder().url(UrlUtils.getUrlFindCoordinates(lat, lon)).get().build();

        Response response = httpClient.newCall(request).execute();
        JSONObject data = new JSONObject(response.body().string());
        JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
        JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(0);
        String name = resources.getString("name");

        map.put("name", name);
        return map;
    }

}
