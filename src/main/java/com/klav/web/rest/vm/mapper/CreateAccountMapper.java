package com.klav.web.rest.vm.mapper;

import com.klav.service.dto.KlavUserDTO;
import com.klav.web.rest.vm.CreateAccountVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateAccountMapper {

    KlavUserDTO vmToDto(CreateAccountVM createAccountVM);
}
