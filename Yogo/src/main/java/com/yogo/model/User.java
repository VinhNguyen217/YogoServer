package com.yogo.model;

import com.yogo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user")
@With
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer userId;

    private String username;
    private String email;
    private String phone;
    private String address;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
