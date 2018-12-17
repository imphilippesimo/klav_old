package com.klav.repository;

import com.klav.domain.TrustIndex;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TrustIndex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrustIndexRepository extends JpaRepository<TrustIndex, Long> {

}
