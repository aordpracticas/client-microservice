package com.example.client.Client.infrastructure.controller.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantOutputDto {

    private String id;
    private String name;
    private String address;
    private String merchantType;
    private  String clientId;

    private String status;
    private String createdDate;
}
