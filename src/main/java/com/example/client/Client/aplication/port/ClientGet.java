package com.example.client.Client.aplication.port;

import com.example.client.Client.aplication.ClientModel;
import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;

import java.util.List;

public interface ClientGet {

    ClientModel findClientById(String id, boolean simpleOutput);
    List<ClientModel> findClientByName(String name);
    ClientModel findClientByEmail(String email);
}
