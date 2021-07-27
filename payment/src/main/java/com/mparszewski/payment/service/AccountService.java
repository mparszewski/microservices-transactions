package com.mparszewski.payment.service;

import com.mparszewski.payment.model.Account;
import com.mparszewski.payment.model.PaymentDto;
import com.mparszewski.payment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Boolean canBuy(PaymentDto paymentDto) {
        return accountRepository.findById(paymentDto.getUsername())
                .map(Account::getMoney)
                .filter(money -> money > paymentDto.getPrice())
                .isPresent();
    }

}
