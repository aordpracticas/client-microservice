package com.example.client.Client.infrastructure.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.client.Client.domain.ClientEntity;
import com.example.client.Client.infrastructure.controller.DTO.ClientInputDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class ClientRepository {

    private final DynamoDBMapper dynamoDBMapper;




    public ClientEntity save(ClientEntity client, boolean isNew) {
        if (isNew) {
            client.iniciarCampos();
        }
        dynamoDBMapper.save(client);
        return client;
    }




    public ClientEntity findById(String id) {
        String pk = "#Client" + id;
        return dynamoDBMapper.load(ClientEntity.class, pk, id);
    }



    public ClientEntity findByEmail(String email) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":email", new AttributeValue().withS(email));


        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#email", "email");

        DynamoDBQueryExpression<ClientEntity> query = new DynamoDBQueryExpression<ClientEntity>()
                .withIndexName("GSIEmail")
                .withKeyConditionExpression("#email = :email")
                .withExpressionAttributeValues(expressionValues)
                .withExpressionAttributeNames(expressionAttributeNames)
                .withConsistentRead(false);

        List<ClientEntity> results = dynamoDBMapper.query(ClientEntity.class, query);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }


    public List<ClientEntity> findByName(String name) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":gIndex2Pk", new AttributeValue().withS("Client")); // Valor hash del GSI
        expressionValues.put(":name", new AttributeValue().withS(name.toLowerCase())); // Valor del filtro

        Map<String, String> expressionNames = new HashMap<>();
        expressionNames.put("#gIndex2Pk", "gIndex2Pk");
        expressionNames.put("#normalizedName", "normalizedName");

        DynamoDBQueryExpression<ClientEntity> query = new DynamoDBQueryExpression<ClientEntity>()
                .withIndexName("GSIgIndex2Pk")
                .withKeyConditionExpression("#gIndex2Pk = :gIndex2Pk")
                .withFilterExpression("contains(#normalizedName, :name)")
                .withExpressionAttributeNames(expressionNames)
                .withExpressionAttributeValues(expressionValues)
                .withConsistentRead(false);

        return dynamoDBMapper.query(ClientEntity.class, query);
    }

    public boolean deleteById(String id) {
        String pk = "#Client" + id;
        ClientEntity client = dynamoDBMapper.load(ClientEntity.class, pk, id);
        if (client != null) {
            dynamoDBMapper.delete(client);
            return true;
        }
        return false;
    }





}
