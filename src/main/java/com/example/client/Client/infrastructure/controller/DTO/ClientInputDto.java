package com.example.client.Client.infrastructure.controller.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "DTO de entrada para crear o actualizar un cliente")
public class ClientInputDto {

    @ApiModelProperty(value = "Nombre del cliente", example = "Juan", required = true)
    private String name;

    @ApiModelProperty(value = "Apellidos del cliente", example = "Pérez Gómez", required = true)
    private String surname;

    @ApiModelProperty(value = "NIF, CIF o NIE del cliente", example = "12345678Z", required = true)
    private String cifNifNie;

    @ApiModelProperty(value = "Número de teléfono del cliente", example = "600123456", required = true)
    private String phone;

    @ApiModelProperty(value = "Correo electrónico del cliente", example = "juan.perez@example.com", required = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email no válido")
    private String email;
}
