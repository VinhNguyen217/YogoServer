package com.yogo.business.order;

import com.yogo.business.auth.UserDto;
import com.yogo.business.auth.UserService;
import com.yogo.business.booking.BookingInfoDto;
import com.yogo.business.socket.SocketClientManage;
import com.yogo.business.socket.SocketDriverManage;
import com.yogo.business.socket.SocketHandler;
import com.yogo.business.socket.UserSocket;
import com.yogo.enums.Status;
import com.yogo.message.MessageText;
import com.yogo.model.Booking;
import com.yogo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SocketHandler socketHandler;

    @Override
    public BookingInfoDto acceptBooking(String bookingId, HttpServletRequest servletRequest) {
        UserDto userDto = userService.checkSession(servletRequest);
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
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
                    socketHandler.sendDriverInfo(null, c.getSocketId());
            });
            return booking.convert();
        }
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.NOT_FOUND);
    }

    @Override
    public BookingInfoDto rejectBooking(String bookingId, HttpServletRequest servletRequest) {
        UserDto userDto = userService.checkSession(servletRequest);
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setStatus(Status.REJECT);
            return bookingRepository.save(booking).convert();
        }
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.NOT_FOUND);
    }
}
