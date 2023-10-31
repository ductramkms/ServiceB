package com.example.ServiceB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ServiceB.model.Client;
import com.example.ServiceB.model.Role;
import com.example.ServiceB.repository.ClientRepository;
import com.example.ServiceB.repository.RoleRepository;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> cOptional = clientRepository.findByUsername();
        if (cOptional.isEmpty()) {
            throw new UsernameNotFoundException("Couldn't find the user with the username = "
                    + username);
        }
        return cOptional.get();
    }

    public Client createClient(String username, String password, Role... roles) {
        Client client = Client.builder().username(username).password(passwordEncoder.encode(
                password)).build();
        for (Role role : roles) {
            client.addRole(role);
        }
        return client;
    }

    public void generateUserRoles() {
        Role employeeRole = Role.builder().roleId(1).name("EMPLOYEE").build();
        roleRepository.save(employeeRole);
    }

}
