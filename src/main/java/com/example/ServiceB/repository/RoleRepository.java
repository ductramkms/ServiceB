package com.example.ServiceB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiceB.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, Integer> {

}
