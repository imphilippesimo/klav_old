package com.klav.service.ext;

import com.klav.repository.ext.TravelRepositoryExtended;
import com.klav.service.TravelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TravelServiceExtended extends TravelService {

    private final TravelRepositoryExtended travelRepository;

    public TravelServiceExtended(TravelRepositoryExtended travelRepository) {
        this.travelRepository = travelRepository;
    }
}
