package com.yogo.business.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yogo.business.auth.UserDto;
import com.yogo.business.auth.UserService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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
        BookingInfoDto bookingInfo = booking.convert();
        if (!SocketDriverManage.getInstance().list.isEmpty()) {
            List<UserSocket> driversReady = SocketDriverManage.getInstance().list
                    .stream()
                    .filter(u -> u.getStatus().equals(Status.READY))
                    .collect(Collectors.toList());
            if (!driversReady.isEmpty()) {
                socketHandler.sendBooking(bookingInfo, driversReady.get(0).getSocketId());
            }
        }
        return booking;
    }

    @Override
    public Booking findById(String id, HttpServletRequest servletRequest) {
        UserDto userDto = userService.checkSession(servletRequest);
        Optional<Booking> bookingOptional = bookingRepository.findByIdAndUserId(id, userDto.getId());
        if (bookingOptional.isPresent()) return bookingOptional.get();
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.NOT_FOUND);
    }
}
