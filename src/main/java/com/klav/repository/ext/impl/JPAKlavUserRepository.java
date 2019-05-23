package com.klav.repository.ext.impl;

import com.klav.domain.KlavUser;
import com.klav.repository.ext.KlavUserRepositoryCustomQueries;
import com.klav.repository.ext.KlavUserRepositoryExt;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class JPAKlavUserRepository implements KlavUserRepositoryExt {

    private KlavUserRepositoryCustomQueries klavUserRepository;

    public JPAKlavUserRepository(KlavUserRepositoryCustomQueries klavUserRepository) {
        this.klavUserRepository = klavUserRepository;
    }


    @Override
    public Optional<KlavUser> createUser(KlavUser klavUser) {
        return Optional.of(klavUserRepository.save(klavUser));
    }

    @Override
    public Optional<KlavUser> findOneByPhoneNumber(String phoneNumber) {
        return klavUserRepository.findOneByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<KlavUser> findOneByEmailIgnoreCase(String email) {
        return klavUserRepository.findOneByEmailIgnoreCase(email);
    }

    @Override
    public void delete(KlavUser klavUser) {
        klavUserRepository.delete(klavUser);

    }

    @Override
    public void flush() {
        klavUserRepository.flush();

    }

    @Override
    public Iterable<KlavUser> findAllByActivatedIsFalseAndCreatedDateBefore(Instant instant) {
        return new ArrayList<>();
    }
}
