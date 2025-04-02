package com.example.client.Client.aplication;

import com.example.client.Client.aplication.port.ClientCreate;
import com.example.client.Client.domain.ClientEntity;
import com.example.client.Client.domain.mappers.ClientEntityMapper;
import com.example.client.Client.infrastructure.Repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientCreateImpl implements ClientCreate {

    private final ClientRepository clientRepository;
    private final ClientEntityMapper clientEntityMapper;

    @Override
    public ClientModel createClient(ClientModel clientCreado) {

        ClientEntity entity = clientEntityMapper.toEntity(clientCreado);
        ClientEntity savedEntity = clientRepository.save(entity, true);
        return clientEntityMapper.toModel(savedEntity);
    }
}



