package com.yogo.business.auth;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yogo.enums.Role;
import com.yogo.message.MessageText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.yogo.model.DriverManager;
import com.yogo.model.SessionManager;
import com.yogo.model.User;
import com.yogo.repository.UserRepository;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UserRepository userRepository;

    @Value("${name-session}")
    private String name_session;

    public List<UserDto> getCurrentUsers() {
        return sessionRegistry.getAllPrincipals()
                .stream()
                .map(o -> objectMapper.convertValue(o, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto createClient(UserRegisterDto user) {
        return createUser(user, Role.ROLE_CLIENT);
    }

    public UserDto createDriver(UserRegisterDto user) {
        return createUser(user, Role.ROLE_DRIVER);
    }

    public UserDto createUser(UserRegisterDto userRegisterDto, Role role) {
        User userCheck = userRepository.findByEmail(userRegisterDto.getEmail());
        if (userCheck != null) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.EMAIL_EXIST);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User().withUsername(userRegisterDto.getUsername()).withEmail(userRegisterDto.getEmail()).withPhone(userRegisterDto.getPhone()).withAddress(userRegisterDto.getAddress()).withRole(role).withPassword(encoder.encode(userRegisterDto.getPassword()));
        User userCreate = userRepository.save(user);
        return objectMapper.convertValue(userCreate, UserDto.class);
    }

    public UserLoginResponse login(UserLoginRequest userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassword();
        User userCheck = userRepository.findByEmail(email);
        if (userCheck == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.LOGIN_FAILED);
        } else {
            boolean checkPassword = BCrypt.checkpw(password, userCheck.getPassword());
            if (!checkPassword) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.LOGIN_FAILED);
            else {
                UserDto userDto = objectMapper.convertValue(userCheck, UserDto.class);
                String sessionId = String.valueOf(UUID.randomUUID());
                sessionRegistry.registerNewSession(sessionId, userDto);
                return new UserLoginResponse().withInfo(userDto)
                        .withSessionId(sessionId);
            }
        }
    }

    public UserDto checkSession(HttpServletRequest servletRequest) {
        String sessionId = servletRequest.getHeader(name_session);
        if (sessionId == null)
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        try {
            return (UserDto) sessionInformation.getPrincipal();
        } catch (Exception ex) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
        }
    }

    public User isSessionValid(String sessionId) {
        User user = SessionManager.getInstance().map.get(sessionId);
        return user;
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
}
