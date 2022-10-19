package com.yogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service")
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_service;

    private String name;

    private Integer active;

    private float price;

    private String detail;
}
