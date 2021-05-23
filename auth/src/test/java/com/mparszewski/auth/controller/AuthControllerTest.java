package com.mparszewski.auth.controller;

import com.mparszewski.auth.model.ApplicationUser;
import com.mparszewski.auth.model.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthControllerTest {

    @Test
    void helloController() {
        ApplicationUser applicationUser = new ApplicationUser();
    }

    @Test
    public void testEnums() {
        Role role = Role.ROLE_ADMIN;
        assertThat(role.getAuthority()).isEqualTo(role.name());
    }
}