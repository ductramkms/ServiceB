package com.example.ServiceB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ServiceB.model.Role;
import com.example.ServiceB.repository.RoleRepository;
import com.example.ServiceB.service.ClientService;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private ClientService securityService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

    }

}
