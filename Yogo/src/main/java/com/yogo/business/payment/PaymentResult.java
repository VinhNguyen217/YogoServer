package com.yogo.business.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class PaymentResult {
    private String id;
    private String name;
    private Double price;
    private Double totalPrice;
    private String describe;
}
