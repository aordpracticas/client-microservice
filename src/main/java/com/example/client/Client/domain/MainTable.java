package com.example.client.Client.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import feign.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "MainTable")
public class MainTable {

    @DynamoDBHashKey(attributeName = "PK")
    private String PK;

    @DynamoDBRangeKey(attributeName = "SK")
    private String SK;

    @DynamoDBAttribute(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "status")
    private String status;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "gIndex2Pk")
    @DynamoDBAttribute(attributeName = "gIndex2Pk" )
    private String gIndex2Pk;

    @DynamoDBAttribute(attributeName = "createdDate")
    private Date createdDate;



    public void inicializarBase(String tipoEntidad) {
        if (this.id == null || this.id.isEmpty()) {
            String generatedId = UUID.randomUUID().toString();
            this.id = generatedId;
            this.PK = "#" + tipoEntidad + generatedId;
            this.SK = generatedId;
            this.gIndex2Pk = "Client";
        }

        if (this.status == null) {
            this.status = "ACTIVE";
        }

        if (this.createdDate == null) {
            this.createdDate = new Date();
        }
    }
}
