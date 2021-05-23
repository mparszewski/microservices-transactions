package com.mparszewski.auth.config;

import com.mparszewski.auth.repository.UserRepository;
import com.mparszewski.auth.service.UserDetailsServiceImpl;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationProperties applicationProperties;
    private final UserRepository userRepository;


    public WebSecurity(UserDetailsServiceImpl userService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ApplicationProperties applicationProperties,
                       UserRepository userRepository) {
        this.userDetailsService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.applicationProperties = applicationProperties;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), applicationProperties))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), applicationProperties, userRepository));
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

}
