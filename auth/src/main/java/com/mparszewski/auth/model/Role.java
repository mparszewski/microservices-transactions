package com.mparszewski.auth.model;

import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.toList;

public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_CLIENT("ROLE_CLIENT"),
    ROLE_TESTER("ROLE_TESTER");

    @Getter
    String authority;

    Role(String authority) {
        this.authority = authority;
    }

    static List<String> nameValues(List<Role> roles) {
        return roles.stream().map(Role::getAuthority).collect(toList());
    }
}
