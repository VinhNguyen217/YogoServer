package com.yogo.controller;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.yogo.exception.MyException;
import com.yogo.model.Booking;
import com.yogo.model.BookingInfo;
import com.yogo.model.Coordinates;
import com.yogo.model.SocketManager;
import com.yogo.model.User;
import com.yogo.service.BookingService;
import com.yogo.service.UserService;
import com.yogo.socket.SocketHandler;
import com.yogo.socket.SocketServer;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	private SocketIOServer server = SocketServer.server;
	
	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;

	@Autowired
	private SocketHandler socket;

	@PostMapping("/create")
	public ResponseEntity<HashMap<String, Object>> create(@RequestHeader(value = "session") String sessionKey,
			@RequestBody Booking booking) {
		HashMap<String, Object> map = new HashMap<>();
		if (userService.isSessionValid(sessionKey) != null) {
			bookingService.save(booking);
			Booking b = bookingService.findLastBooking();
			map.put("booking", b);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} else {
			map.put("booking", null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}

	@PostMapping("/acceptBooking")
	public void acceptBooking(@RequestHeader(value = "session") String sessionKey,
			@RequestParam Integer idBooking) {
		
		if (userService.isSessionValid(sessionKey) != null) {
			User driverSelected = userService.findDriver();	//Tìm ra lái xe
			if (driverSelected != null) {
				
				Booking booking = bookingService.findById(idBooking);	//lấy ra đối tượng Booking
				booking.setStatus("accept");
				bookingService.save(booking);
				User client = userService.isSessionValid(sessionKey);	// Lấy ra đối tượng Client
				BookingInfo bookingInfo = new BookingInfo(client, booking);	//Tạo đối tượng BookingInfo
				
				// Tìm socket của client
				UUID uuidClient = SocketManager.getInstance().map.get(client.getId_user());				
				SocketIOClient socketIOClient = server.getClient(uuidClient);
				
				socket.sendBooking(socketIOClient, bookingInfo, driverSelected);
			}
		}
	}
	
	@PostMapping("/cancleBooking")
	public void cancleBooking(@RequestHeader(value = "session") String sessionKey,
			@RequestParam Integer idBooking) {
		if(userService.isSessionValid(sessionKey) != null) {
			Booking existBooking = bookingService.findById(idBooking);
			existBooking.setStatus("cancel");	//Cập nhật trạng thái hủy cho booking
			bookingService.save(existBooking);
		}
	}
	
	@PostMapping("/setDriverInfo")
	public void setDriverInfo(@RequestHeader(value = "session") String sessionKey,@RequestParam Integer idDriver) {
		if(userService.isSessionValid(sessionKey) != null) {
			User driver = userService.get(idDriver);
			socket.sendDriverInfo(driver);
		}
		
	}
	
	@PostMapping("/setTrackingLocation")
	public void setTrackingLocation(@RequestHeader(value = "session") String sessionKey,@RequestBody Coordinates location) {
		if(userService.isSessionValid(sessionKey) != null) {
			socket.sendTracking(location);
		}
	}
	

}
