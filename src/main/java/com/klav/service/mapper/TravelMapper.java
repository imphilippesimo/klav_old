package com.klav.service.mapper;

import com.klav.domain.Address;
import com.klav.domain.Travel;
import com.klav.repository.ext.AddressRepositoryExtended;
import com.klav.service.dto.TravelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class TravelMapper {



    @Autowired
    protected AddressRepositoryExtended addressRepository;

    public abstract Travel travelDTOToTravel(TravelDTO travelDTO);

    public abstract TravelDTO travelToTravelDTO(Travel travel);

    public abstract List<TravelDTO> travelsToTravelDTOs(List<Travel> travels);

    public abstract List<Travel> travelDTOsToTravels(List<TravelDTO> travelDTOs);

    public Address addressFromId(Long id) {
        if (id == null)
            return null;
        Optional<Address> result = addressRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    public Long IdFromAddress(Address address){
        if(address==null)
            return null;
        return address.getId();
    }
}
