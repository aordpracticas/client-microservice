package com.example.client.Client.infrastructure.controller.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "DTO de salida que representa a un cliente")
public class ClientOutputDto {

    @ApiModelProperty(value = "ID del cliente", example = "1234", required = true)
    private String id;

    @ApiModelProperty(value = "Nombre del cliente", example = "Juan", required = true)
    private String name;

    @ApiModelProperty(value = "Apellidos del cliente", example = "Pérez Gómez", required = true)
    private String surname;

    @ApiModelProperty(value = "NIF, CIF o NIE del cliente", example = "12345678Z", required = true)
    private String cifNifNie;

    @ApiModelProperty(value = "Número de teléfono del cliente", example = "600123456", required = true)
    private String phone;

    @ApiModelProperty(value = "Correo electrónico del cliente", example = "juan.perez@example.com", required = true)
    private String email;

    @ApiModelProperty(value = "Estado del cliente", example = "ACTIVE", required = true)
    private String status;

    @ApiModelProperty(value = "Fecha de creación del cliente", example = "2025-04-02T10:38:13+02:00", required = true)
    private String createdDate;
}
