package com.example.client.Client.aplication;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ClientModel {

    private String id;
    private String name;
    private String surname;
    private String cifNifNie;
    private String phone;
    private String email;

    private String status;
    private Date createdDate;
}

