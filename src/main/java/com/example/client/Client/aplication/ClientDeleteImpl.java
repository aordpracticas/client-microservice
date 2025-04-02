package com.example.client.Client.aplication;


import com.example.client.Client.aplication.port.ClientDelete;
import com.example.client.Client.infrastructure.Repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientDeleteImpl  implements ClientDelete {

    private final ClientRepository clientRepository;

    @Override
    public boolean deleteById(String id) {
        return clientRepository.deleteById(id);
    }
}
