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

    private String userId;

    private String userName;

    private Double latStartPoint;

    private Double lonStartPoint;

    private String nameStartPoint;

    private Double latEndPoint;

    private Double lonEndPoint;

    private String nameEndPoint;

    private Double totalPrice;

    private String status;

    private String notes;

    private String createdAt;
}
