package com.yogo.business.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yogo.business.auth.UserDto;
import com.yogo.business.auth.UserService;
import com.yogo.enums.Role;
import com.yogo.enums.Status;
import com.yogo.model.User;
import com.yogo.repository.UserRepository;
import com.yogo.socket.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.Booking;
import com.yogo.repository.BookingRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public BookingDriver create(BookingRequest bookingRequest, HttpServletRequest servletRequest) {
        UserDto userDto = userService.checkSession(servletRequest);
        Booking bookingCreate = new Booking()
                .withStartPoint(bookingRequest.getStartPoint())
                .withEndPoint(bookingRequest.getEndPoint())
                .withServiceId(bookingRequest.getServiceId())
                .withUserId(userDto.getId())
                .withStatus(Status.CREATED);
        Booking booking = bookingRepository.save(bookingCreate);
        List<User> drivers = userRepository.findByRole(Role.ROLE_DRIVER);
        UserDto driverInfo = objectMapper.convertValue(drivers.get(0), UserDto.class);
        BookingDriver bookingDriver = new BookingDriver()
                .withBooking(booking)
                .withDriver(driverInfo);
        return bookingDriver;
    }

    public Booking findById(Integer id) {
        return bookingRepository.findById(id).get();
    }
}
