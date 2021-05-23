package com.mparszewski.auth.config;

import com.google.common.collect.ImmutableList;
import com.mparszewski.auth.model.ApplicationUser;
import com.mparszewski.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.mparszewski.auth.model.Role.*;

@Component
@RequiredArgsConstructor
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private final UserDetailsServiceImpl userService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setEmail("admin@example.com");
        applicationUser.setFirstName("First");
        applicationUser.setLastName("Last");
        applicationUser.setPassword("admin");
        applicationUser.setRoles(ImmutableList.of(ROLE_CLIENT, ROLE_TESTER, ROLE_ADMIN));
        userService.saveUser(applicationUser);
    }
}
