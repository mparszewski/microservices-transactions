package com.mparszewski.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class ApplicationUser {

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

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = EAGER)
    private List<Role> roles;
}
