package com.yogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "oder")
public class Oder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_oder;

	private Date accept_time;

	private Integer id_user;

	private Integer id_booking;

	private String status;
}
