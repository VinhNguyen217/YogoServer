package com.yogo.business.booking;

import com.yogo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor
@Data
@With
public class DriverInfoDto {
    private String id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private Double rating;
    private Integer rideComplete;
    private String typeTransport;
    private String licensePlate;
}
