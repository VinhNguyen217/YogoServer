package com.yogo.business.booking;

import com.yogo.business.auth.UserDto;
import com.yogo.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor
@Data
@With
public class BookingDriver {
    private Booking booking;
    private UserDto driver;
}
