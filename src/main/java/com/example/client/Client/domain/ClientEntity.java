package com.example.client.Client.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity extends MainTable {

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "surname")
    private String surname;

    @DynamoDBAttribute(attributeName = "cifNifNie")
    private String cifNifNie;

    @DynamoDBAttribute(attributeName = "phone")
    private String phone;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "GSIEmail")
    @DynamoDBAttribute(attributeName = "email")
    private String email;

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
