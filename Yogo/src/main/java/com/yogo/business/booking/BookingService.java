package com.yogo.business.booking;

import com.yogo.business.auth.UserDto;
import com.yogo.business.auth.UserService;
import com.yogo.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.Booking;
import com.yogo.repository.BookingRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    public Booking create(BookingRequest bookingRequest, HttpServletRequest servletRequest) {
        UserDto userDto = userService.checkSession(servletRequest);
        Booking bookingCreate = new Booking().withStartPoint(bookingRequest.getStartPoint())
                .withEndPoint(bookingRequest.getEndPoint())
                .withServiceId(bookingRequest.getServiceId())
                .withUserId(userDto.getId())
                .withStatus(Status.CREATED);
        return bookingRepository.save(bookingCreate);
    }

    public Booking findById(Integer id) {
        return bookingRepository.findById(id).get();
    }

}
