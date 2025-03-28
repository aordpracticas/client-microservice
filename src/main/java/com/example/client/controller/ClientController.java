package com.example.client.controller;

import com.example.client.dto.ClientInputDto;
import com.example.client.dto.ClientOutputDto;
import com.example.client.dto.MerchantDto;
import com.example.client.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody @Valid ClientInputDto clientDto, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError("email") != null ?
                    result.getFieldError("email").getDefaultMessage() :
                    "Error en la validación de los campos.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        ClientOutputDto newClient = clientService.createClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findClientById(@RequestParam String id, @RequestParam(required = false) Boolean simpleOutput) {
        ClientOutputDto client = clientService.findClientById(id, simpleOutput != null && simpleOutput);

        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hemos encontrado el cliente con ese ID");
        }

        return ResponseEntity.ok(client);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<ClientOutputDto>> findClientByName(@RequestParam String name) {
        return ResponseEntity.ok(clientService.findClientByName(name));
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<ClientOutputDto> findClientByEmail(@RequestParam String email) {
        return ResponseEntity.ok(clientService.findClientByEmail(email));
    }

    @GetMapping("/merchants/{clientId}")
    public ResponseEntity<List<MerchantDto>> findMerchantsOfClient(@PathVariable String clientId) {
        return ResponseEntity.ok(clientService.findMerchantsOfClient(clientId));
    }
}
