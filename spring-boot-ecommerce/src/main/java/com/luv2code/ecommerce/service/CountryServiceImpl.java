package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.dao.CountryRepository;
import com.luv2code.ecommerce.dto.CountryDTO;
import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.exception.CountryAlreadyExistsException;
import com.luv2code.ecommerce.exception.CountryNotFoundException;
import com.luv2code.ecommerce.mapper.CountryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService{

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        if (countries.isEmpty()) {
            throw new CountryNotFoundException("Aucun pays trouvé");
        }

        return countries;
    }

    @Override
    public Country getCountryByCode(String code) {
        Optional<Country> countryOptional = countryRepository.findByCode(code);
        return countryOptional.orElseThrow(() ->
                new CountryNotFoundException("Pays non trouvé pour le code : " + code)
        );
    }

    @Override
    public Country addCountry(CountryDTO countryDTO) {
        if (countryRepository.existsByCode(countryDTO.getCode())) {
            throw new CountryAlreadyExistsException("Le pays existe déjà en base de données");
        }
        Country country = countryMapper.convertToCountry(countryDTO);

        return countryRepository.save(country);
    }

    @Override
    public void updateCountry(int id, CountryDTO countryDTO) {
        Country countryToUpdate = countryRepository.findById(id)

                .orElseThrow(() -> new CountryNotFoundException("Aucun pays avec l'ID: " + id));

        if (countryRepository.existsByCode(countryDTO.getCode())) {
            throw new CountryAlreadyExistsException("Le pays existe déjà en base de données");
        }

        countryToUpdate.setName(countryDTO.getName());
        countryToUpdate.setCode(countryDTO.getCode());

        countryRepository.save(countryToUpdate);
    }

    @Override
    public void deleteCountryById(int id) {
        Country country = countryRepository.findById(id)

                .orElseThrow(() -> new CountryNotFoundException("Aucun pays avec l'ID: " + id));
        countryRepository.delete(country);
    }

}