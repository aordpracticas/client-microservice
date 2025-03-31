package com.example.client.Client.aplication;

import com.example.client.Client.aplication.port.ClientCreate;
import com.example.client.Client.domain.ClientEntity;
import com.example.client.Client.domain.mappers.ClientMapper;
import com.example.client.Client.infrastructure.Repository.ClientRepository;
import com.example.client.Client.infrastructure.controller.DTO.ClientInputDto;
import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientCreateImpl implements ClientCreate {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientOutputDto createClient(ClientInputDto clientDto) {
        ClientEntity entity = clientMapper.toEntity(clientDto);
        return clientMapper.toDto(clientRepository.save(entity,true));
    }

}
