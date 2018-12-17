package com.klav.repository;

import com.klav.domain.KlavUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the KlavUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlavUserRepository extends JpaRepository<KlavUser, Long> {

    @Query(value = "select distinct klav_user from KlavUser klav_user left join fetch klav_user.chats",
        countQuery = "select count(distinct klav_user) from KlavUser klav_user")
    Page<KlavUser> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct klav_user from KlavUser klav_user left join fetch klav_user.chats")
    List<KlavUser> findAllWithEagerRelationships();

    @Query("select klav_user from KlavUser klav_user left join fetch klav_user.chats where klav_user.id =:id")
    Optional<KlavUser> findOneWithEagerRelationships(@Param("id") Long id);

}
