package com.luv2code.ecommerce.mapper;

import com.luv2code.ecommerce.dto.CountryDTO;
import com.luv2code.ecommerce.entity.Country;
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