package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.dto.CountryDTO;
import com.luv2code.ecommerce.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();

    Country getCountryByCode(String code);

    Country addCountry(CountryDTO countryDTO);

    void updateCountry(int id, CountryDTO countryDTO);

    void deleteCountryById(int id);

}