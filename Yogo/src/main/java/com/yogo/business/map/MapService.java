package com.yogo.business.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yogo.business.auth.UserService;
import com.yogo.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import okhttp3.*;

import javax.servlet.http.HttpServletRequest;

@Service
public class MapService {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

    @Autowired
    UserService userService;

    public List<PointDto> findPoint(String pointFind, HttpServletRequest servletRequest) throws IOException {
        userService.checkSession(servletRequest);

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
                Coordinates coordinates = new Coordinates().withLatitude((Double) c.get(0)).withLongitude((Double) c.get(1));
                coordinatesList.add(coordinates);
            }
        }
        coordinatesList.forEach((v) -> {
            try {
                PointDto pointDto = new PointDto().withLocation(v).withName(findPointName(v.getLatitude(), v.getLongitude(), servletRequest));
                res.add(pointDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return res;
    }

    public HashMap<String, Object> findRoute(String p0, String p1, HttpServletRequest servletRequest) throws IOException {
        userService.checkSession(servletRequest);

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

    public String findPointName(Double lat, Double lon, HttpServletRequest servletRequest) throws IOException {
        userService.checkSession(servletRequest);

        Request request = new Request.Builder().url(UrlUtils.getUrlFindCoordinates(lat, lon)).get().build();

        Response response = httpClient.newCall(request).execute();
        JSONObject data = new JSONObject(response.body().string());
        JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
        JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(0);
        return resources.getString("name");
    }

}
