package com.example.client.Client.aplication.port;

import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;

import java.util.List;

public interface ClientGet {

    ClientOutputDto findClientById(String id, boolean simpleOutput);
    List<ClientOutputDto> findClientByName(String name);
    ClientOutputDto findClientByEmail(String email);
}
