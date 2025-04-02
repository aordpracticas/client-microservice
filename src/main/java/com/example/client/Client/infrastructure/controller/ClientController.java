package com.example.client.Client.infrastructure.controller;

import com.example.client.Client.aplication.ClientModel;
import com.example.client.Client.aplication.port.ClientCreate;
import com.example.client.Client.aplication.port.ClientDelete;
import com.example.client.Client.aplication.port.ClientGet;
import com.example.client.Client.aplication.port.ClientUpdate;
import com.example.client.Client.domain.mappers.ClientDtoMapper;
import com.example.client.Client.infrastructure.controller.DTO.ClientInputDto;
import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;
import com.example.client.Client.infrastructure.controller.DTO.MerchantOutputDto;
import com.example.client.feign.MerchantFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientCreate clientCreate;
    private final ClientUpdate clientUpdate;
    private final ClientGet clientGet;
    private final ClientDtoMapper clientDtoMapper;
    private final ClientDelete clientDelete;
    private final MerchantFeignClient merchantFeignClient;

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody @Valid ClientInputDto clientDto, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError("email") != null ?
                    result.getFieldError("email").getDefaultMessage() :
                    "Error en la validación de los campos.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        ClientModel model =  clientDtoMapper.toModel(clientDto);

        ClientOutputDto dto = clientDtoMapper.toOutputDto(clientCreate.createClient(model));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findClientById(@RequestParam String id, @RequestParam(required = false) Boolean simpleOutput) {
        ClientModel model = clientGet.findClientById(id, simpleOutput != null && simpleOutput);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hemos encontrado el cliente con ese ID");
        }

        ClientOutputDto dto = clientDtoMapper.toOutputDto(model);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<ClientOutputDto>> findClientByName(@RequestParam String name) {
        List<ClientModel> models = clientGet.findClientByName(name);
        List<ClientOutputDto> dtos = models.stream().map(clientDtoMapper::toOutputDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<?> findClientByEmail(@RequestParam String email) {
        ClientModel model = clientGet.findClientByEmail(email);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado cliente con ese email.");
        }
        return ResponseEntity.ok(clientDtoMapper.toOutputDto(model));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@RequestParam String id, @RequestBody @Valid ClientInputDto clientDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos de entrada");
        }


        ClientModel model = clientDtoMapper.toModel(clientDto);


        ClientModel updatedModel = clientUpdate.updateClient(id, model);
        if (updatedModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }


        ClientOutputDto dto = clientDtoMapper.toOutputDto(updatedModel);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClientById(@RequestParam String id) {
        boolean deleted = clientDelete.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con ese ID no encontrado.");
        }
    }

    @GetMapping("/merchants")
    public ResponseEntity<?> getMerchantsForClient(@RequestParam String clientId) {
        try {
            List<MerchantOutputDto> merchants = merchantFeignClient.getMerchantsByClientId(clientId);

            if (merchants == null || merchants.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No hay merchants asociados al clientId proporcionado.");
            }

            return ResponseEntity.ok(merchants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener merchants del cliente.");
        }
    }


}