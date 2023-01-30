package com.yogo.business.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yogo.business.auth.UserDto;
import com.yogo.business.auth.UserService;
import com.yogo.business.socket.SocketClientManage;
import com.yogo.business.socket.SocketDriverManage;
import com.yogo.business.socket.SocketHandler;
import com.yogo.business.socket.UserSocket;
import com.yogo.enums.Role;
import com.yogo.enums.Status;
import com.yogo.message.MessageText;
import com.yogo.model.Booking;
import com.yogo.model.User;
import com.yogo.repository.BookingRepository;
import com.yogo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SocketHandler socketHandler;

    @Override
    public Booking create(BookingRequest bookingRequest, HttpServletRequest servletRequest) {
        UserDto userDto = userService.checkSession(servletRequest);
        Booking bookingCreate = new Booking()
                .withLatStartPoint(bookingRequest.getPickUp().getLatitude())
                .withLonStartPoint(bookingRequest.getPickUp().getLongitude())
                .withLatEndPoint(bookingRequest.getDropOff().getLatitude())
                .withLonEndPoint(bookingRequest.getDropOff().getLongitude())
                .withServiceId(bookingRequest.getServiceId())
                .withUserId(userDto.getId())
                .withTotalPrice(bookingRequest.getTotalPrice())
                .withNotes(bookingRequest.getNotes())
                .withStatus(Status.CREATED);
        Booking booking = bookingRepository.save(bookingCreate);

        Booking bookingFind = bookingRepository.findById(booking.getId()).get();
        log.info("booking find : " + bookingFind);

        BookingInfoDto bookingInfo = booking.convert();
        bookingInfo.setNameStartPoint(bookingRequest.getPickUp().getFullAddress());
        bookingInfo.setNameEndPoint(bookingRequest.getDropOff().getFullAddress());
        bookingInfo.setUserName(userDto.getUsername());
        UserSocket driverReady = userService.findDriver();
        if (driverReady != null)
            socketHandler.sendBooking(bookingInfo, driverReady.getSocketIOClient().getSessionId());
        return booking;
    }

    @Override
    public Booking findById(String id, HttpServletRequest servletRequest) {
        UserDto userDto = userService.checkSession(servletRequest);
        Optional<Booking> bookingOptional = bookingRepository.findByIdAndUserId(id, userDto.getId());
        if (bookingOptional.isPresent()) return bookingOptional.get();
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.NOT_FOUND);
    }

    @Override
    public Booking acceptBooking(String bookingId, HttpServletRequest servletRequest) {
        UserDto userDto = userService.checkSession(servletRequest);
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        log.info("booking : " + bookingOptional);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setStatus(Status.ACCEPT);
            bookingRepository.save(booking);
            List<UserSocket> drivers = SocketDriverManage.getInstance().list;
            List<UserSocket> clients = SocketClientManage.getInstance().list;
            drivers.forEach(d -> {
                if (d.getUserId().equals(userDto.getId()))
                    d.setStatus(Status.BUSY);
            });
            clients.forEach(c -> {
                if (c.getUserId().equals(booking.getUserId()))
                    socketHandler.sendDriverInfo(userDto, c.getSocketIOClient().getSessionId());
            });
            return booking;
        }
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.NOT_FOUND);
    }
}
