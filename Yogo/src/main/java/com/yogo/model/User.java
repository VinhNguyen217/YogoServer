package com.yogo.model;

import com.yogo.enums.Role;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
@With
public class User extends BaseModel {

    private String username;
    private String email;
    private String phone;
    private String address;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Double rating;
    private Integer rideComplete;
    private String typeTransport;
    private String licensePlate;
}
