package com.example.client.Client.domain.mappers;

import com.example.client.Client.aplication.ClientModel;
import com.example.client.Client.infrastructure.controller.DTO.ClientInputDto;
import com.example.client.Client.infrastructure.controller.DTO.ClientOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface ClientDtoMapper {

    ClientModel toModel(ClientInputDto dto);

    @Mapping(source = "createdDate", target = "createdDate", qualifiedByName = "dateToString")
    ClientOutputDto toOutputDto(ClientModel model);

    @Named("dateToString")
    static String mapDateToString(Date date) {
        if (date == null) return null;

        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(date);
    }
}