package com.example.ServiceB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class ClientController {

    @Autowired
    private AuthenticationManager authenticationManager;

    public void login() {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("", ""));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
