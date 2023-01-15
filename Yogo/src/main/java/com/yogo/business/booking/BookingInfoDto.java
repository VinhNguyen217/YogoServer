package com.yogo.business.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class BookingInfoDto {
    private String id;

    private Double latStartPoint;

    private Double lonStartPoint;

    private Double latEndPoint;

    private Double lonEndPoint;

    private Double totalPrice;

    private String status;

    private String notes;

    private String createdAt;
}
