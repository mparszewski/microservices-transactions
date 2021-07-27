package com.mparszewski.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mparszewski.auth.model.ApplicationUser;
import com.mparszewski.auth.model.ReviewRequestDto;
import com.mparszewski.auth.model.Role;
import com.mparszewski.auth.model.UserDto;
import com.mparszewski.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void saveUser(ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        userRepository.save(applicationUser);
    }

    @Transactional
    public void addReviewToList(ReviewRequestDto reviewRequestDto) {
        userRepository.findById(reviewRequestDto.getUsername())
                .ifPresent(applicationUser -> applicationUser.getReviews().add(reviewRequestDto.getReviewId()));
    }

    public boolean checkVerification(String username) {
        return userRepository.findById(username)
                .map(ApplicationUser::getVerified)
                .orElse(false);
    }

    public UserDto getUserInfoFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return userRepository.findById(decodedJWT.getSubject())
                .map(UserDto::new)
                .map(userDto -> {
                    userDto.setTokenExpiration(decodedJWT.getExpiresAt());
                    return userDto;
                })
                .orElseThrow(() -> new UsernameNotFoundException("Invalid jwt token"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(user -> new User(user.getUsername(), user.getPassword(), convertToAuthorities(user.getRoles())))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with username: " + username));
    }

    public static List<GrantedAuthority> convertToAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = newArrayList();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getAuthority())));
        return authorities;
    }

}
