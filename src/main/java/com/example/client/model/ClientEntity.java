package com.example.client.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientEntity extends MainTable {

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "GSINombre")
    private String name;

    @DynamoDBAttribute
    private String surname;

    @DynamoDBAttribute
    private String cifNifNie;

    @DynamoDBAttribute
    private String phone;

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "GSIEmail")
    private String email;

    public  void iniciarCampos(){
        this.inicializarBase("Client");

    }
}
