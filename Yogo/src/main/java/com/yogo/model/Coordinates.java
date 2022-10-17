package com.yogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
	private BigDecimal latitude;	// Vĩ độ
	private BigDecimal longitude;	// Kinh độ

}
