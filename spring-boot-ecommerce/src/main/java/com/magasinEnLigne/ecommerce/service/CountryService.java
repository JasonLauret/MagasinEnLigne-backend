package com.magasinEnLigne.ecommerce.service;

import com.magasinEnLigne.ecommerce.dto.CountryDTO;
import com.magasinEnLigne.ecommerce.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();

    Country getCountryByCode(String code);

    Country addCountry(CountryDTO countryDTO);

    void updateCountry(int id, CountryDTO countryDTO);

    void deleteCountryById(int id);

}