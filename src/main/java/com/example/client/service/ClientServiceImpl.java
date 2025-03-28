package com.example.client.service;

import com.example.client.dto.ClientInputDto;
import com.example.client.dto.ClientOutputDto;
import com.example.client.dto.MerchantDto;
import com.example.client.mappers.ClientMapper;
import com.example.client.model.ClientEntity;
import com.example.client.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientOutputDto createClient(ClientInputDto clientDto) {
        ClientEntity entity = clientMapper.toEntity(clientDto);
        return clientMapper.toDto(clientRepository.save(entity));
    }

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

    @Override
    public List<MerchantDto> findMerchantsOfClient(String clientId) {
        return null;
    }
}
