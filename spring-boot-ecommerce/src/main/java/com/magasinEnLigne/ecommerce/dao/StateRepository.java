package com.magasinEnLigne.ecommerce.dao;

import com.magasinEnLigne.ecommerce.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StateRepository extends JpaRepository<State, Integer> {
        boolean existsByName(String name);

        List<State> findByCountryCode(@Param("code") String code);

}