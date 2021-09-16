package com.yogo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.Position;
import com.yogo.repository.PositionRepository;



@Service
public class PositionService {

	@Autowired
	private PositionRepository repo;
	
	public List<Position> listAll(){
		return repo.findAll();
	}
	
	public void save(Position position) {
		repo.save(position);
	}
	
	public Position get(Integer id) {
		return repo.findById(id).get();
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public Position find(String place){
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer response = new StringBuffer();
		try {
			URL url = new URL("https://dev.virtualearth.net/REST/v1/Locations?q="+place.trim()+"&maxResults=1&key=Ao9PFmQwSXJV1d36fANi1Tbp3sECeIiZPHzuO4G5Hf7Z_0BR0F7RSabbjFi9xf3S");
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		JSONObject data = new JSONObject(response.toString());
		
		JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
		JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(0);
		
		JSONObject point = resources.getJSONObject("point");
		JSONArray coordinates = point.getJSONArray("coordinates");
		BigDecimal longitude = coordinates.getBigDecimal(0);
		BigDecimal latitude = coordinates.getBigDecimal(1);
		Position position = new Position(null,longitude,latitude);
		return position;
	}
}
