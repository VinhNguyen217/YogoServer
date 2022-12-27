package com.yogo.business.booking;

import com.yogo.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor
@Data
@With
public class BookingDriverResult {
    private Booking booking;
    private DriverInfoDto driver;
}
