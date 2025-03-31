package com.example.client.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.client.model.ClientEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public ClientRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }


    public ClientEntity save(ClientEntity client) {
        client.iniciarCampos();
        dynamoDBMapper.save(client);
        return client;
    }


    public ClientEntity findById(String id) {
        String pk = "#Client" + id;
        return dynamoDBMapper.load(ClientEntity.class, pk, id);
    }



    /* public List<ClientEntity> findByName(String name) {
        ClientEntity clientKey = new ClientEntity();
        clientKey.setName(name);

        DynamoDBQueryExpression<ClientEntity> query = new DynamoDBQueryExpression<ClientEntity>()
                .withIndexName("GSINombre")
                .withHashKeyValues(clientKey)
                .withConsistentRead(false);

        return dynamoDBMapper.query(ClientEntity.class, query);
    } */

    public List<ClientEntity> findByName(String fragment) {
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#name", "name");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":name", new AttributeValue().withS(fragment.toLowerCase()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(#name, :name)")
                .withExpressionAttributeNames(expressionAttributeNames)
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.scan(ClientEntity.class, scanExpression);
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
}
