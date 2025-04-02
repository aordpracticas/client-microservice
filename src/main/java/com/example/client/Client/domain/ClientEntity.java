package com.example.client.Client.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Entidad que representa a un cliente")
public class ClientEntity extends MainTable {

    @ApiModelProperty(value = "Nombre del cliente", example = "Juan")
    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @ApiModelProperty(value = "Apellidos del cliente", example = "Pérez Gómez")
    @DynamoDBAttribute(attributeName = "surname")
    private String surname;

    @ApiModelProperty(value = "NIF, CIF o NIE del cliente", example = "12345678Z")
    @DynamoDBAttribute(attributeName = "cifNifNie")
    private String cifNifNie;

    @ApiModelProperty(value = "Número de teléfono del cliente", example = "600123456")
    @DynamoDBAttribute(attributeName = "phone")
    private String phone;

    @ApiModelProperty(value = "Correo electrónico del cliente", example = "juan.perez@example.com")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "GSIEmail")
    @DynamoDBAttribute(attributeName = "email")
    private String email;

    @ApiModelProperty(value = "Nombre del cliente normalizado para búsquedas", example = "juanperez")
    @DynamoDBAttribute(attributeName = "normalizedName")
    private String normalizedName;

    public void iniciarCampos() {
        this.inicializarBase("Client");
    }

    @DynamoDBHashKey(attributeName = "PK")
    @Override
    public String getPK() {
        return super.getPK();
    }

    @DynamoDBRangeKey(attributeName = "SK")
    @Override
    public String getSK() {
        return super.getSK();
    }
}
