package com.mparszewski.auth.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@DiscriminatorValue("USER")
public class User {

    @Id
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
