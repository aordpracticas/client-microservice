package com.example.client.mappers;

import com.example.client.dto.ClientInputDto;
import com.example.client.dto.ClientOutputDto;
import com.example.client.model.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.Instant;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientEntity toEntity(ClientInputDto dto);

    @Mappings({
            @Mapping(target = "createdDate", expression = "java(dateToString(entity.getCreatedDate()))")
    })
    ClientOutputDto toDto(ClientEntity entity);

    default String dateToString(Date date) {
        return date != null ? date.toInstant().toString() : null;
    }

    default Date stringToDate(String dateStr) {
        return dateStr != null ? Date.from(Instant.parse(dateStr)) : null;
    }
}
