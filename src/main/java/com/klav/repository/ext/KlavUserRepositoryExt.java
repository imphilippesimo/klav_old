package com.klav.repository.ext;

import com.klav.domain.KlavUser;
import com.klav.repository.KlavUserRepository;

import java.time.Instant;
import java.util.Optional;

/**
 * This interface provides methods that can could be overridden by any other
 * user repository management system.
 */
public interface KlavUserRepositoryExt {

    Optional<KlavUser> createUser(final KlavUser klavUser);

    Optional<KlavUser> findOneByPhoneNumber(String phoneNumber);

    Optional<KlavUser> findOneByEmailIgnoreCase(String email);

    void delete(KlavUser klavUser);

    void flush();

    Iterable<KlavUser> findAllByActivatedIsFalseAndCreatedDateBefore(Instant instant);
}
