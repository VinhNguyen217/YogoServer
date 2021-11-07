package com.yogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.Client;
import com.yogo.model.Driver;
import com.yogo.model.DriverManager;
import com.yogo.model.SessionManager;
import com.yogo.model.User;
import com.yogo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> listAll() {
		List<User> users = repo.findAll();
		List<User> userUpdates = new ArrayList<User>();
		for (User user : users) {
			if (user.getType() == 'c') {
				User u = new Client(user.getId_user(), user.getType(), user.getFirst_name(), user.getLast_name(),
						user.getAddress(), user.getGender(), user.getEmail());
				userUpdates.add(u);
			} else {
				User u = new Driver(user.getId_user(), user.getType(), user.getFirst_name(), user.getLast_name(),
						user.getAddress(), user.getGender(), user.getEmail());
				userUpdates.add(u);
			}
		}
		return userUpdates;
	}

	public void save(User user) {
		repo.save(user);
	}

	/**
	 * Return id of the last user
	 * 
	 * @return
	 */
	public Integer getId() {
		return repo.findLastUser().getId_user();
	}

	public User get(Integer id) {
		List<User> users = listAll();
		User user = new User();
		for (User u : users) {
			if (u.getId_user() == id) {
				user = u;
			}
		}
		return user;
	}

	/**
	 * Check id if exists return true , otherwise return false
	 * 
	 * @param id
	 * @return
	 */
	public boolean isIdValid(Integer id) {
		return repo.existsById(id);
	}

	/**
	 * Check session to return user
	 * 
	 * @param sessionId
	 * @return
	 */
	public User isSessionValid(String sessionId) {
		User user = SessionManager.getInstance().map.get(sessionId);
		return user;
	}

	/**
	 * return list of drivers
	 * 
	 * @return
	 */
	public List<User> getDriverAll() {
		List<User> users = listAll();
		List<User> drivers = new ArrayList<User>();
		for (User user : users) {
			if (user instanceof Driver) {
				drivers.add(user);
			}
		}
		return drivers;
	}

	/**
	 * Find driver for user
	 * 
	 * @return
	 */
	public User findDriver() {
//		ArrayList<User> drivers = DriverManager.getInstance().driverWait;	//Lấy ra danh sách những driver đang chờ
//		Random r = new Random();
//		int n = r.nextInt(drivers.size() + 1);
		User driver = repo.findById(15).get();
		return driver;
//		return drivers.get(n);
	}
}
