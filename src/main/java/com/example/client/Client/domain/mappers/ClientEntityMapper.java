package com.example.client.Client.domain.mappers;

import com.example.client.Client.aplication.ClientModel;
import com.example.client.Client.domain.ClientEntity;
import org.mapstruct.*;

import java.util.Date;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ClientEntityMapper {

   /*

   Esta funcionalidad lo que me hace es rellenar el atributo normalizedName, source seria de donde saca los nombres y demas

    */

   @Mapping(target = "normalizedName", source = "name", qualifiedByName = "normalize")
   ClientEntity toEntity(ClientModel model);

   ClientModel toModel(ClientEntity entity);

   @Named("normalize")
   static String normalizeName(String name) {
      if (name == null) return null;
      return name
              .toLowerCase()
              .replaceAll("[áàäâ]", "a")
              .replaceAll("[éèëê]", "e")
              .replaceAll("[íìïî]", "i")
              .replaceAll("[óòöô]", "o")
              .replaceAll("[úùüû]", "u")
              .replaceAll("[ñ]", "n")
              .replaceAll("[^a-z0-9]", "");
   }

   @AfterMapping
   default void afterMapping(@MappingTarget ClientEntity entity) {
      entity.iniciarCampos();
   }


}
