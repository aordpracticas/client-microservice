package com.example.client.Client.infrastructure.controller;

import com.example.client.Client.aplication.port.ClientCreate;
import com.example.client.Client.aplication.port.ClientGet;
import com.example.client.Client.aplication.port.ClientUpdate;
import com.example.client.Client.infrastructure.controller.DTO.ClientInputDto;
import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private  final ClientCreate clientCreate;
    private  final ClientUpdate clientUpdate;
    private  final ClientGet clientGet;


    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody @Valid ClientInputDto clientDto, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError("email") != null ?
                    result.getFieldError("email").getDefaultMessage() :
                    "Error en la validación de los campos.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        ClientOutputDto newClient = clientCreate.createClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findClientById(@RequestParam String id, @RequestParam(required = false) Boolean simpleOutput) {
        ClientOutputDto client = clientGet.findClientById(id, simpleOutput != null && simpleOutput);

        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hemos encontrado el cliente con ese ID");
        }

        return ResponseEntity.ok(client);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<ClientOutputDto>> findClientByName(@RequestParam String name) {
        return ResponseEntity.ok(clientGet.findClientByName(name));
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<ClientOutputDto> findClientByEmail(@RequestParam String email) {
        return ResponseEntity.ok(clientGet.findClientByEmail(email));
    }
    @PutMapping("/update")

    public ResponseEntity<?> updateClient(@RequestParam String id, @RequestBody @Valid ClientInputDto clientDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos de entrada");
        }

        ClientOutputDto updatedClient = clientUpdate.updateClient(id, clientDto);

        if (updatedClient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }

        return ResponseEntity.ok(updatedClient);
    }





}
