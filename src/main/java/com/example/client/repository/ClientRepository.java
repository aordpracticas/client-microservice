package com.example.client.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
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
        dynamoDBMapper.save(client);
        return client;
    }


    public ClientEntity findById(String id) {
        ClientEntity clientKey = new ClientEntity();
        clientKey.setPK(id);

        DynamoDBQueryExpression<ClientEntity> query = new DynamoDBQueryExpression<ClientEntity>()
                .withHashKeyValues(clientKey)
                .withLimit(1);

        List<ClientEntity> results = dynamoDBMapper.query(ClientEntity.class, query);
        return results.isEmpty() ? null : results.get(0);
    }


    public List<ClientEntity> findByName(String name) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":name", new AttributeValue().withS(name.toLowerCase()));

        // Usamos HashMap en lugar de Map.of para hacerlo compatible con Java 8
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#name", "name");

        DynamoDBQueryExpression<ClientEntity> query = new DynamoDBQueryExpression<ClientEntity>()
                .withIndexName("GSINombre")  // Usamos el GSI "GSINombre"
                .withKeyConditionExpression("#name = :name")
                .withExpressionAttributeValues(expressionValues)
                .withExpressionAttributeNames(expressionAttributeNames);

        return dynamoDBMapper.query(ClientEntity.class, query);
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
