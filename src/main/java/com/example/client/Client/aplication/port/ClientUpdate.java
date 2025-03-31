package com.example.client.Client.aplication.port;

import com.example.client.Client.infrastructure.controller.DTO.ClientInputDto;
import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;

public interface ClientUpdate {

    public ClientOutputDto updateClient(String id, ClientInputDto clientDto);
}
