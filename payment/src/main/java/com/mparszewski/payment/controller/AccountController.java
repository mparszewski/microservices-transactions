package com.mparszewski.payment.controller;


import com.mparszewski.payment.model.PaymentDto;
import com.mparszewski.payment.model.ResponseDto;
import com.mparszewski.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/")
    public ResponseDto pay(@RequestBody PaymentDto paymentDto) {
        Boolean canBuy = accountService.canBuy(paymentDto);
        return ResponseDto.builder()
                .username(paymentDto.getUsername())
                .consent(accountService.canBuy(paymentDto))
                .build();
    }
}
