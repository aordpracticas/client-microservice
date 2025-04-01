package com.example.client.Client.aplication;
import com.example.client.Client.aplication.port.ClientGet;
import com.example.client.Client.domain.ClientEntity;
import com.example.client.Client.domain.mappers.ClientEntityMapper;
import com.example.client.Client.infrastructure.Repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientGetImpl implements ClientGet {

    private final ClientRepository clientRepository;
    private final ClientEntityMapper clientEntityMapper;

    @Override
    public ClientModel findClientById(String id, boolean simpleOutput) {
        ClientEntity clientEntity = clientRepository.findById(id);
        if (clientEntity == null) return null;
        return clientEntityMapper.toModel(clientEntity);
    }

    @Override
    public List<ClientModel> findClientByName(String name) {
        String normalized = normalize(name);
        return clientRepository.findByName(normalized)
                .stream()
                .map(clientEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public ClientModel findClientByEmail(String email) {
        ClientEntity clientEntity = clientRepository.findByEmail(email);
        if (clientEntity == null) return null;
        return clientEntityMapper.toModel(clientEntity);
    }

    private String normalize(String name) {
        return name.toLowerCase()
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[ñ]", "n")
                .replaceAll("[^a-z0-9]", "");
    }
}
