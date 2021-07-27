package com.mparszewski.payment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "money")
    private Double money;
}
