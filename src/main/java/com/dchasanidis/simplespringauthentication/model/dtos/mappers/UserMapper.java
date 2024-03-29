package com.dchasanidis.simplespringauthentication.model.dtos.mappers;

import com.dchasanidis.simplespringauthentication.model.User;
import com.dchasanidis.simplespringauthentication.model.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toDto(final UserEntity userEntity);
}
