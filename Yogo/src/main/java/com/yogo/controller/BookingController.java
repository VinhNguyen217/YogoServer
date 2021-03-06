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
import com.yogo.model.Booking;
import com.yogo.model.BookingInfo;
import com.yogo.model.Coordinates;
import com.yogo.model.DriverManager;
import com.yogo.model.Payment;
import com.yogo.model.SocketManager;
import com.yogo.model.User;
import com.yogo.service.BookingService;
import com.yogo.service.PaymentService;
import com.yogo.service.UserService;
import com.yogo.socket.SocketHandler;
import com.yogo.socket.SocketServer;

@RestController
@RequestMapping("/booking")
public class BookingController {

	private SocketIOServer server = SocketServer.server;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

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
	public ResponseEntity<HashMap<String, Object>> acceptBooking(@RequestHeader(value = "session") String sessionKey,
			@RequestParam Integer idBooking) {
		HashMap<String, Object> map = new HashMap<>();
		if (userService.isSessionValid(sessionKey) != null) {
			User driverSelected = userService.findDriver(); // T??m ra l??i xe

			if (driverSelected != null) {

				Booking booking = bookingService.findById(idBooking); // l???y ra ?????i t?????ng Booking
				booking.setStatus("accept");
				bookingService.save(booking);

				User client = userService.isSessionValid(sessionKey); // L???y ra ?????i t?????ng Client
				Payment payment = paymentService.findLastPayment();
				BookingInfo bookingInfo = new BookingInfo(client, booking, payment); // T???o ?????i t?????ng BookingInfo

				// T??m socket c???a client
				UUID uuidClient = SocketManager.getInstance().map.get(client.getId_user());
				SocketIOClient socketIOClient = server.getClient(uuidClient);

				socket.sendBooking(socketIOClient, bookingInfo, driverSelected);
				map.put("result", "???? t??m th???y l??i xe.");
				return ResponseEntity.status(HttpStatus.OK).body(map);
			} else {
				map.put("result", "Kh??ng t??m th???y l??i xe.");
				return ResponseEntity.status(HttpStatus.OK).body(map);
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}

	@PostMapping("/cancelBooking")
	public void cancleBooking(@RequestHeader(value = "session") String sessionKey, @RequestParam Integer idBooking) {
		if (userService.isSessionValid(sessionKey) != null) {
			Booking existBooking = bookingService.findById(idBooking);
			existBooking.setStatus("cancel"); // C???p nh???t tr???ng th??i h???y cho booking
			bookingService.save(existBooking);
		}
	}

	@PostMapping("/finish")
	public void finishJourney(@RequestHeader(value = "session") String sessionKey, @RequestParam Integer idClient) {
		if (userService.isSessionValid(sessionKey) != null) {
			User driver = userService.isSessionValid(sessionKey); // l???y ra driver d???a v??o sessionId

			UUID uuidDriver = SocketManager.getInstance().map.get(driver.getId_user());
			SocketIOClient socketIOClient = server.getClient(uuidDriver);

			socket.finish(socketIOClient, idClient);

			DriverManager.getInstance().driverWork.remove(driver); // X??a driver kh???i danh s??ch ??ang l??m vi???c
			DriverManager.getInstance().driverWait.add(driver); // Th??m driver v??o danh s??ch ch???

		}
	}

	@PostMapping("/setTracking")
	public void setTracking(@RequestHeader(value = "session") String sessionKey, @RequestBody Coordinates location,
			@RequestParam Integer idUser) {
		if (userService.isSessionValid(sessionKey) != null) {

			User user1 = userService.isSessionValid(sessionKey);

			UUID uuidUser1 = SocketManager.getInstance().map.get(user1.getId_user());
			SocketIOClient socketIOClient = server.getClient(uuidUser1);

			socket.sendTracking(socketIOClient, location, idUser);
		}
	}

}
