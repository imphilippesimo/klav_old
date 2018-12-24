package com.klav.service.ext;

import com.klav.domain.Travel;
import com.klav.repository.ext.TravelRepositoryExtended;
import com.klav.service.TravelService;
import com.klav.service.dto.TravelDTO;
import com.klav.service.mapper.TravelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Service
@Transactional
public class TravelServiceExtended extends TravelService {

    private final TravelRepositoryExtended travelRepository;


    TravelMapper mapper;

    public TravelServiceExtended(TravelRepositoryExtended travelRepository,TravelMapper mapper) {
        this.travelRepository = travelRepository;
        this.mapper = mapper;
    }

    public TravelDTO createOrUpdateTravel(TravelDTO travelDTO) {

        Travel travel = travelRepository.save(mapper.travelDTOToTravel(travelDTO));

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
