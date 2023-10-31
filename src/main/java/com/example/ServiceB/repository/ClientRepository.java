package com.example.ServiceB.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ServiceB.model.Client;

public interface ClientRepository extends MongoRepository<Client, Integer> {

    Optional<Client> findByUsername();
}
