package com.mparszewski.payment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    String username;
    Boolean consent;
}
