package com.klav.service.mapper;

import com.klav.domain.KlavUser;
import com.klav.service.dto.KlavUserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, IdToEntityMapper.class})
public abstract class KlavUserMapper {

    public abstract KlavUser klavUserDTOToKlavUser(KlavUserDTO klavUserDTO);

    public abstract KlavUserDTO klavUserToKlavUserDTO(KlavUser klavUser);

    public abstract List<KlavUser> klavUserDTOsToKlavUsers(List<KlavUserDTO> klavUserDTOs);

    public abstract List<KlavUserDTO> klavUsersToKlavUsersDTOs(List<KlavUser> klavUsers);


}


