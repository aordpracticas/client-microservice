package com.example.client.Client.aplication;
import com.example.client.Client.aplication.port.ClientUpdate;
import com.example.client.Client.domain.ClientEntity;
import com.example.client.Client.domain.mappers.ClientDtoMapper;
import com.example.client.Client.domain.mappers.ClientEntityMapper;
import com.example.client.Client.infrastructure.Repository.ClientRepository;
import com.example.client.Client.infrastructure.controller.DTO.ClientInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientUpdateImpl implements ClientUpdate {

    private final ClientRepository clientRepository;
    private final ClientEntityMapper clientEntityMapper;
    private final ClientDtoMapper clientDtoMapper;

    @Override
    public ClientModel updateClient(String id, ClientModel dto) {
        ClientEntity originalEntity = clientRepository.findById(id);
        if (originalEntity == null) return null;


        ClientModel model = clientEntityMapper.toModel(originalEntity);


        if (dto.getName() != null) model.setName(dto.getName());
        if (dto.getEmail() != null) model.setEmail(dto.getEmail());
        if (dto.getPhone() != null) model.setPhone(dto.getPhone());
        if (dto.getSurname() != null) model.setSurname(dto.getSurname());
        if (dto.getCifNifNie() != null) model.setCifNifNie(dto.getCifNifNie());


        ClientEntity updatedEntity = clientEntityMapper.toEntity(model);


        updatedEntity.setPK(originalEntity.getPK());
        updatedEntity.setSK(originalEntity.getSK());
        updatedEntity.setId(originalEntity.getId());
        updatedEntity.setStatus(originalEntity.getStatus());
        updatedEntity.setCreatedDate(originalEntity.getCreatedDate());
        updatedEntity.setGIndex2Pk(originalEntity.getGIndex2Pk());


        ClientEntity savedEntity = clientRepository.save(updatedEntity, false);

        return clientEntityMapper.toModel(savedEntity);
    }





}
