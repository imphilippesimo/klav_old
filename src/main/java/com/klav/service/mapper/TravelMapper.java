package com.klav.service.mapper;

import com.klav.domain.Address;
import com.klav.domain.File;
import com.klav.domain.Travel;
import com.klav.repository.ext.AddressRepositoryExtended;
import com.klav.service.dto.TravelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class, IdToEntityMapper.class})
public abstract class TravelMapper {


    @Autowired
    protected AddressRepositoryExtended addressRepository;

    public abstract Travel travelDTOToTravel(TravelDTO travelDTO);

    public abstract TravelDTO travelToTravelDTO(Travel travel);

    public abstract List<TravelDTO> travelsToTravelDTOs(List<Travel> travels);

    public abstract List<Travel> travelDTOsToTravels(List<TravelDTO> travelDTOs);


}
