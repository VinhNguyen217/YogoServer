package com.yogo.business.travel_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class ServiceDistanceResult {
    private String id;
    private String name;
    private Double price;
    private Double totalPrice;
    private String describe;
}
