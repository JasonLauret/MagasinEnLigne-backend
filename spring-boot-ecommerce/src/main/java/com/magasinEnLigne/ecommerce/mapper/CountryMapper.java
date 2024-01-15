package com.magasinEnLigne.ecommerce.mapper;

import com.magasinEnLigne.ecommerce.dto.CountryDTO;
import com.magasinEnLigne.ecommerce.entity.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    private CountryDTO convertToDto(Country country) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCode(country.getCode());
        countryDTO.setName(country.getName());
        return countryDTO;
    }

    public Country convertToCountry(CountryDTO countryDto) {
        Country country = new Country();
        country.setCode(countryDto.getCode());
        country.setName(countryDto.getName());

        return country;
    }
}