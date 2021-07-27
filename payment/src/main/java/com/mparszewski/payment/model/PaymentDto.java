package com.mparszewski.payment.model;

import lombok.Data;

@Data
public class PaymentDto {
    String username;
    Double price;
}