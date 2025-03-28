package com.example.client.service;

import com.example.client.dto.ClientInputDto;
import com.example.client.dto.ClientOutputDto;
import com.example.client.dto.MerchantDto;

import java.util.List;

public interface ClientService {
    ClientOutputDto createClient(ClientInputDto clientDto);
    ClientOutputDto findClientById(String id, boolean simpleOutput);
    List<ClientOutputDto> findClientByName(String name);
    ClientOutputDto findClientByEmail(String email);
    List<MerchantDto> findMerchantsOfClient(String clientId);
}
