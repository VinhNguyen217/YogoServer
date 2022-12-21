package com.yogo.business.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Double startPoint;
    private Double endPoint;
    private Integer serviceId;
}
