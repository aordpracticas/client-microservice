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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Client Management", description = "Operaciones relacionadas con el cliente")
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

    @ApiOperation(value = "Crear un nuevo cliente", notes = "Este endpoint permite crear un cliente en el sistema.")
    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody @Valid ClientInputDto clientDto, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError("email") != null ?
                    result.getFieldError("email").getDefaultMessage() :
                    "Error en la validación de los campos.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        ClientModel model = clientDtoMapper.toModel(clientDto);
        ClientOutputDto dto = clientDtoMapper.toOutputDto(clientCreate.createClient(model));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @ApiOperation(value = "Obtener cliente por ID", notes = "Este endpoint devuelve un cliente dado su ID.")
    @GetMapping("/findById")
    public ResponseEntity<?> findClientById(@RequestParam String id, @RequestParam(required = false) Boolean simpleOutput) {
        ClientModel model = clientGet.findClientById(id, simpleOutput != null && simpleOutput);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hemos encontrado el cliente con ese ID");
        }
        ClientOutputDto dto = clientDtoMapper.toOutputDto(model);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Buscar cliente por nombre", notes = "Este endpoint devuelve una lista de clientes cuyo nombre coincida con el parámetro proporcionado.")
    @GetMapping("/findByName")
    public ResponseEntity<List<ClientOutputDto>> findClientByName(@RequestParam String name) {
        List<ClientModel> models = clientGet.findClientByName(name);
        List<ClientOutputDto> dtos = models.stream().map(clientDtoMapper::toOutputDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @ApiOperation(value = "Buscar cliente por email", notes = "Este endpoint devuelve un cliente dado su correo electrónico.")
    @GetMapping("/findByEmail")
    public ResponseEntity<?> findClientByEmail(@RequestParam String email) {
        ClientModel model = clientGet.findClientByEmail(email);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado cliente con ese email.");
        }
        return ResponseEntity.ok(clientDtoMapper.toOutputDto(model));
    }

    @ApiOperation(value = "Actualizar cliente", notes = "Este endpoint permite actualizar los datos de un cliente.")
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

    @ApiOperation(value = "Eliminar cliente", notes = "Este endpoint elimina un cliente dado su ID.")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClientById(@RequestParam String id) {
        boolean deleted = clientDelete.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con ese ID no encontrado.");
        }
    }

    @ApiOperation(value = "Obtener merchants para un cliente", notes = "Este endpoint devuelve todos los merchants asociados a un cliente dado su ID.")
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
