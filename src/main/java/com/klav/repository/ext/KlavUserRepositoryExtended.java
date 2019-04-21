package com.klav.repository.ext;

import com.klav.domain.KlavUser;
import com.klav.domain.User;
import com.klav.repository.KlavUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface KlavUserRepositoryExtended extends KlavUserRepository {

    Optional<KlavUser> findDistinctByPersonId(Long id);

    Optional<KlavUser> findDistinctByPersonLogin(String login);

    Page<KlavUser> findAllByPersonLoginNot(Pageable pageable, String login);


    List<KlavUser> findAllByPersonActivatedIsFalseAndPersonCreatedDateBefore(Instant datetime);
}


