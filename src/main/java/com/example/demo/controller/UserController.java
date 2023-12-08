package com.example.demo.controller;

import com.example.demo.model.UserDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsManager userDetailsManager;

    public UserController(PasswordEncoder passwordEncoder, UserDetailsManager userDetailsManager) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetails createUser(@RequestBody UserDTO user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        UserDetails userDetails = new User(user.getUsername(),  passwordEncoder.encode(user.getPassword()), authorities);
        userDetailsManager.createUser(userDetails);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }

}
