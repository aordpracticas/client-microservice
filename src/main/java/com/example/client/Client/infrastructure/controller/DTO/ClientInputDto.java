package com.example.client.Client.infrastructure.controller.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class ClientInputDto {
    private String name;
    private String surname;
    private String cifNifNie;
    private String phone;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email no válido")
    private String email;
}