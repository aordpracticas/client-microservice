package com.example.client.Client.aplication;


import com.example.client.Client.aplication.port.ClientGet;
import com.example.client.Client.domain.ClientEntity;
import com.example.client.Client.domain.mappers.ClientMapper;
import com.example.client.Client.infrastructure.Repository.ClientRepository;
import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientGetImpl implements ClientGet {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;



    @Override
    public ClientOutputDto findClientById(String id, boolean simpleOutput) {
        ClientEntity client = clientRepository.findById(id);
        if (client == null) return null;

        if (simpleOutput) {
            ClientOutputDto dto = new ClientOutputDto();
            dto.setId(client.getId());
            return dto;
        }
        return clientMapper.toDto(client);
    }

    @Override
    public List<ClientOutputDto> findClientByName(String name) {
        return clientRepository.findByName(name)
                .stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientOutputDto findClientByEmail(String email) {
        return clientMapper.toDto(clientRepository.findByEmail(email));
    }
}
