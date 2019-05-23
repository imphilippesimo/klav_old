package com.klav.service.mapper;

import com.klav.domain.KlavUser;
import com.klav.service.dto.KlavUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IdToEntityMapper.class})
public abstract class KlavUserMapper {

    @Mapping(target = "id", ignore = true)
    public abstract KlavUser klavUserDTOToKlavUser(KlavUserDTO klavUserDTO);

    @Mapping(target = "password", ignore = true)
    public abstract KlavUserDTO klavUserToKlavUserDTO(KlavUser klavUser);

    public abstract List<KlavUser> klavUserDTOsToKlavUsers(List<KlavUserDTO> klavUserDTOs);

    public abstract List<KlavUserDTO> klavUsersToKlavUsersDTOs(List<KlavUser> klavUsers);


}


