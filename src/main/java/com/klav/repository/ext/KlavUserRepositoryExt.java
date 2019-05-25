package com.klav.repository.ext;

import com.klav.domain.KlavUser;

import java.time.Instant;
import java.util.Optional;

/**
 * This interface provides methods that can could be overridden by any other
 * user repository management system.
 */
public interface KlavUserRepositoryExt {

    Optional<KlavUser> createUser(final KlavUser klavUser);

    Optional<KlavUser> updateUser(final KlavUser klavUser);

    Optional<KlavUser> findOneByPhoneNumber(String phoneNumber);

    Optional<KlavUser> findOneByEmailIgnoreCase(String email);

    Optional<KlavUser> findOneByActivationKey(String activationKey);

    void delete(KlavUser klavUser);

    void flush();

    Iterable<KlavUser> findAllByActivatedIsFalseAndCreatedDateBefore(Instant instant);
}
