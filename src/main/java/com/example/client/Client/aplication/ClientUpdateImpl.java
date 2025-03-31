package com.example.client.Client.aplication;


import com.example.client.Client.aplication.port.ClientUpdate;
import com.example.client.Client.domain.ClientEntity;
import com.example.client.Client.domain.mappers.ClientMapper;
import com.example.client.Client.infrastructure.Repository.ClientRepository;
import com.example.client.Client.infrastructure.controller.DTO.ClientInputDto;
import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientUpdateImpl  implements ClientUpdate {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    @Override
    public ClientOutputDto updateClient(String id, ClientInputDto dto) {
        ClientEntity client = clientRepository.findById(id);

        if (client == null) {
            return null;
        }

        if (dto.getName() != null) {
            client.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            client.setEmail(dto.getEmail());
        }

        if (dto.getPhone() != null) {
            client.setPhone(dto.getPhone());
        }

        if (dto.getSurname() != null) {
            client.setSurname(dto.getSurname());
        }

        if (dto.getCifNifNie() != null) {
            client.setCifNifNie(dto.getCifNifNie());
        }



        ClientEntity updated = clientRepository.save(client,false);

        return clientMapper.toDto(updated);
    }


}
