package com.yogo.business.booking;

import com.yogo.business.map.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Coordinates pickUp;
    private Coordinates dropOff;
    private String serviceId;
    private Double totalPrice;
}
