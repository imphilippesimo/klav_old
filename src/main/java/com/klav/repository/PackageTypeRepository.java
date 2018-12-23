package com.klav.repository;

import com.klav.domain.PackageType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PackageType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackageTypeRepository extends JpaRepository<PackageType, Long> {

}
