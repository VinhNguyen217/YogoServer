package com.yogo.business.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Coordinates {
    private Double latitude;    // Vĩ độ
    private Double longitude;    // Kinh độ
    private String fullAddress;
}
