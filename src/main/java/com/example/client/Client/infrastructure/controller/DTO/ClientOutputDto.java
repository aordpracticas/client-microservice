package com.example.client.Client.infrastructure.controller.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientOutputDto {
    private String id;
    private String name;
    private String surname;
    private String cifNifNie;
    private String phone;
    private String email;
    private String status;
    private String createdDate;
}
