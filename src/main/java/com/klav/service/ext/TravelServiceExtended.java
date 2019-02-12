package com.klav.service.ext;

import com.klav.domain.KlavUser;
import com.klav.domain.PackageType;
import com.klav.domain.Travel;
import com.klav.repository.ext.PackageTypeRepositoryExtended;
import com.klav.repository.ext.TravelRepositoryExtended;
import com.klav.service.TravelService;
import com.klav.service.dto.PackageTypeDTO;
import com.klav.service.dto.TravelDTO;
import com.klav.service.mapper.TravelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TravelServiceExtended extends TravelService {

    private final TravelRepositoryExtended travelRepository;
    private final PackageTypeRepositoryExtended packageTypeRepository;


    TravelMapper mapper;

    public TravelServiceExtended(TravelRepositoryExtended travelRepository, PackageTypeRepositoryExtended packageTypeRepository, TravelMapper mapper) {
        this.travelRepository = travelRepository;
        this.packageTypeRepository = packageTypeRepository;
        this.mapper = mapper;
    }

    public TravelDTO createOrUpdateTravel(TravelDTO travelDTO) {

        Travel travel = mapper.travelDTOToTravel(travelDTO);

        //if the package type doesnt yet exist (i.e dont have an existing id), we simply create it as a new one
        for (PackageType packageType : travel.getAcceptedPackageTypes()) {
            if (packageType.getId() == null)
                packageTypeRepository.save(packageType);
        }


        //TODO: Extend the UserService  :UserServiceExtended to create/update/read/delete and Co. while doing these OPS on a User
        //TODO: get the current logged user from UserServiceExtended and add it to the travel
        KlavUser traveller;


        travel = travelRepository.save(travel);

        return mapper.travelToTravelDTO(travel);


    }

    public List<TravelDTO> getAllTravels() {
        List<Travel> travels = travelRepository.findAll();
        return mapper.travelsToTravelDTOs(travels);

    }


    public Optional<TravelDTO> getTravel(Long id) {

        Optional<Travel> travel = travelRepository.findById(id);

        if (!travel.isPresent())
            return Optional.empty();

        return Optional.of(mapper.travelToTravelDTO(travel.get()));


    }

    public void deleteTravel(Long id) {
        travelRepository.deleteById(id);
    }

    /**
     * @param id
     * @return the travel access code
     */
    public String getTravelAccessCode(Long id) {
        Optional<Travel> result = travelRepository.findById(id);
        if (result.isPresent())
            return result.get().getAccessCode();
        else
            return null;

    }

    private TravelDTO accept(Travel travel1) {
        return mapper.travelToTravelDTO(travel1);

    }

    //TODO implement List<PackageTypeDTO> getAcceptedPackageTypes

    //TODO implement List<FileDTO> getTravelProofs
}
