package com.yogo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                User u = new Client(user.getUserId(), user.getType(), user.getFirst_name(), user.getLast_name(),
                        user.getAddress(), user.getGender(), user.getEmail());
                userUpdates.add(u);
            } else {
                User u = new Driver(user.getUserId(), user.getType(), user.getFirst_name(), user.getLast_name(),
                        user.getAddress(), user.getGender(), user.getEmail());
                userUpdates.add(u);
            }
        }
        return userUpdates;
    }

    public User save(User user) {
        return repo.save(user);
    }

    /**
     * Return id of the last user
     *
     * @return
     */
    public Integer getId() {
        return repo.findLastUser().getUserId();
    }

    public User get(Integer id) {
        List<User> users = listAll();
        User user = new User();
        for (User u : users) {
            if (u.getUserId() == id) {
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
        ArrayList<User> drivers = DriverManager.getInstance().driverWait; // Lấy ra danh sách những driver đang chờ
        if (drivers.size() == 1) {
            return drivers.get(0);
        } else if (drivers.size() > 1) {
            Random r = new Random();
            int n = r.nextInt(drivers.size());
            return drivers.get(n);
        } else {
            return null;
        }
    }

    public User findById(Integer id) {
        return repo.findById(id).get();
    }
}
