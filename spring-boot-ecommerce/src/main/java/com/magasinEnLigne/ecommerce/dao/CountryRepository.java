package com.magasinEnLigne.ecommerce.dao;

import com.magasinEnLigne.ecommerce.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCode(String code);

    boolean existsByCode(String code);
}