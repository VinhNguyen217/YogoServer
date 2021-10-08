package com.yogo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yogo.model.Coordinates;
import okhttp3.*;

@Service
public class MapService {

	private final String key_map = "Ao9PFmQwSXJV1d36fANi1Tbp3sECeIiZPHzuO4G5Hf7Z_0BR0F7RSabbjFi9xf3S";
	private final OkHttpClient httpClient = new OkHttpClient();

	public ArrayList<Coordinates> findPoint(String place) throws IOException {
		Request request = new Request.Builder()
				.url("https://dev.virtualearth.net/REST/v1/Locations?q=" + place + "&maxResults=1&key=" + key_map + "")
				.get().build();

		Response response = httpClient.newCall(request).execute();

		JSONObject data = new JSONObject(response.body().string());
		JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
		JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(0);

		JSONObject point = resources.getJSONObject("point");
		JSONArray coordinates = point.getJSONArray("coordinates");
		
		Gson gson = new Gson();
		ArrayList<Coordinates> res = gson.fromJson(coordinates.toString(), ArrayList.class);
		return res;
	}

	public ArrayList<ArrayList<Coordinates>> findPoints(String place) throws IOException {
		ArrayList<ArrayList<Coordinates>> res = new ArrayList<>();
		Gson gson = new Gson();
		
		Request request = new Request.Builder()
				.url("https://dev.virtualearth.net/REST/v1/Locations?q=" + place + "&key=" + key_map + "").get()
				.build();

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
		return res;
	}

	public ArrayList<Coordinates> findRoute(String p0, String p1) throws IOException {
		Request request = new Request.Builder().url("https://dev.virtualearth.net/REST/V1/Routes/Driving?wp.0=" + p0
				+ "&wp.1=" + p1 + "&ra=routePath&avoid=minimizeTolls&key=" + key_map + "").get().build();

		Response response = httpClient.newCall(request).execute();

		JSONObject data = new JSONObject(response.body().string());
		JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
		JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(0);
		JSONObject routePath = resources.getJSONObject("routePath");
		JSONObject line = routePath.getJSONObject("line");
		JSONArray coordinates = line.getJSONArray("coordinates");

		Gson gson = new Gson();
		ArrayList<?> jsonArray = gson.fromJson(coordinates.toString(), ArrayList.class);
		ArrayList<Coordinates> res = (ArrayList<Coordinates>) jsonArray;
		return res;
	}

}
