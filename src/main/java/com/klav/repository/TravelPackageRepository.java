package com.klav.repository;

import com.klav.domain.TravelPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TravelPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {

}
