package com.klav.repository.ext;

import com.klav.domain.KlavUser;
import com.klav.repository.KlavUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KlavUserRepositoryCustomQueries extends KlavUserRepository {

    Optional<KlavUser> findOneByPhoneNumber(String phoneNumber);

    Optional<KlavUser> findOneByEmailIgnoreCase(String email);

    Optional<KlavUser> findOneByActivationKey(String activationKey);
}
