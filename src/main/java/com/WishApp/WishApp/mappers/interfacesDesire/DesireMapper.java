package com.WishApp.WishApp.mappers.interfacesDesire;

import com.WishApp.WishApp.entities.Desire;
import com.WishApp.WishApp.http.request.DesireRequestDTO;
import com.WishApp.WishApp.http.response.DesireResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DesireMapper {

    DesireMapper INSTANCE = Mappers.getMapper(DesireMapper.class);

    Desire toEntity(DesireRequestDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    DesireResponseDTO toDTO(Desire entity);
}
