package com.yogo.model;

import com.yogo.enums.Gender;
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
    @Column(name = "id_user")
    private Integer userId;

    private char type;

    private String first_name;

    private String last_name;

    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String email;
}
