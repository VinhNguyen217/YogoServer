package com.yogo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yogo.dto.PointDto;
import com.yogo.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yogo.model.Coordinates;
import okhttp3.*;

@Service
public class MapService {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

    public List<PointDto> findPoint(String pointFind) throws IOException {
        List<PointDto> res = new ArrayList<>();
        List<Coordinates> coordinatesList = new ArrayList<>();

        Request request = new Request.Builder().url(UrlUtils.getUrlFindPoint(pointFind)).get().build();
        Response response = httpClient.newCall(request).execute();

        JSONObject data = new JSONObject(response.body().string());
        JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
        int estimatedTotal = resourceSets.getInt("estimatedTotal");
        if (estimatedTotal > 0) {
            for (int i = 0; i < estimatedTotal; i++) {
                JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(i);
                JSONObject point = resources.getJSONObject("point");
                JSONArray coordinatesArray = point.getJSONArray("coordinates");
                ArrayList c = gson.fromJson(coordinatesArray.toString(), ArrayList.class);
                Coordinates coordinates = new Coordinates().withLat((Double) c.get(0)).withLon((Double) c.get(1));
                coordinatesList.add(coordinates);
            }
        }
        coordinatesList.forEach((v) -> {
            try {
                PointDto pointDto = new PointDto().withLocation(v).withName(findPointName(v.getLat(), v.getLon()));
                res.add(pointDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return res;
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

    public String findPointName(Double lat, Double lon) throws IOException {
        Request request = new Request.Builder().url(UrlUtils.getUrlFindCoordinates(lat, lon)).get().build();

        Response response = httpClient.newCall(request).execute();
        JSONObject data = new JSONObject(response.body().string());
        JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
        JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(0);
        return resources.getString("name");
    }

}
