package com.yogo.service;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yogo.model.Payment;
import com.yogo.repository.PaymentRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Service
public class PaymentService {
	
	private final String key_map = "Ao9PFmQwSXJV1d36fANi1Tbp3sECeIiZPHzuO4G5Hf7Z_0BR0F7RSabbjFi9xf3S";
	private final OkHttpClient httpClient = new OkHttpClient();
	
	
	@Autowired
	private PaymentRepository repo;
	
	@Autowired
	private SerService serService;

	public void save(Payment payment) {
		repo.save(payment);
	}
	
	public Payment findLastPayment() {
		return repo.findLastPayment();
	}
	
	/**
	 * Calculate price for route  
	 * @param p0
	 * @param p1
	 * @param id_service
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, Object> calPrice(String p0,String p1,Integer id_service) throws IOException {
		HashMap<String , Object> map = new HashMap<>();
		Request request = new Request.Builder().url("https://dev.virtualearth.net/REST/V1/Routes/Driving?wp.0=" + p0
				+ "&wp.1=" + p1 + "&ra=routePath&avoid=minimizeTolls&key=" + key_map + "").get().build();

		Response response = httpClient.newCall(request).execute();

		JSONObject data = new JSONObject(response.body().string());
		JSONObject resourceSets = data.getJSONArray("resourceSets").getJSONObject(0);
		JSONObject resources = resourceSets.getJSONArray("resources").getJSONObject(0);
		float travelDistance = resources.getFloat("travelDistance");
		com.yogo.model.Service s = serService.getById(id_service);
		
		float calPrice = travelDistance * s.getPrice();
		map.put("travelDistance", travelDistance);
		map.put("calPrice", calPrice);
		Payment p = new Payment(null,calPrice);
		repo.save(p);
		return map;
	}
}
