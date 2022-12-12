package com.yogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Coordinates {
    private Double lat;    // Vĩ độ
    private Double lon;    // Kinh độ
}
