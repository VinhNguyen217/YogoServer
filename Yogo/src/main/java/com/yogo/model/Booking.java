package com.yogo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_booking")
	private Integer bookingId;

	private Integer number;

	private Timestamp timer;

	private Integer serviceId;

	private String pick_location;

	private String destination_location;

	private String status;
}
