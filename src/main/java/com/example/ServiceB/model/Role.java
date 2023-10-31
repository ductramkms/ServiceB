package com.example.ServiceB.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("roles")
public class Role {

    @Id
    private Integer roleId;
    private String name;

    @DBRef
    @Builder.Default
    List<Client> clients = new ArrayList<>();

    public void addClient(Client client) {
        if (client == null) {
            return;
        }

        this.clients.add(client);
        client.getRoles().add(this);
    }

    public void removeClient(Client client) {
        if (client == null) {
            return;
        }

        this.clients.remove(client);
        client.getRoles().remove(this);
    }
}
