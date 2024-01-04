package com.dchasanidis.simplespringauthentication.model.dtos.responses;

import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toDto(final UserEntity userEntity);
}
