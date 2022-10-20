package com.yogo.model;

import com.sun.istack.NotNull;
import com.yogo.enums.Gender;
import com.yogo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;

    private char type;

    private String first_name;

    private String last_name;

    private String address;

    private Gender gender;

    private String email;

    @NotNull
    private Role role;

    private String password;
}
