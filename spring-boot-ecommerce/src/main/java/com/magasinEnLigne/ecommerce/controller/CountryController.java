package com.magasinEnLigne.ecommerce.controller;

import com.magasinEnLigne.ecommerce.dto.CountryDTO;
import com.magasinEnLigne.ecommerce.entity.Country;
import com.magasinEnLigne.ecommerce.exception.CountryAlreadyExistsException;
import com.magasinEnLigne.ecommerce.exception.CountryNotFoundException;
import com.magasinEnLigne.ecommerce.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/admin/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Country> getCountryByCode(@PathVariable String code) {
        Country country = countryService.getCountryByCode(code);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCountry(@Valid @RequestBody CountryDTO countryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return handleValidationErrors(result);
        }

        Country country = countryService.addCountry(countryDTO);
        return new ResponseEntity<>(country, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>  updateCountryById(@PathVariable int id,
                                                @Valid @RequestBody CountryDTO countryDTO,
                                                BindingResult result) {
        if (result.hasErrors()) {
            return handleValidationErrors(result);
        }

        countryService.updateCountry(id, countryDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountryById(@PathVariable int id) {
        countryService.deleteCountryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Erreur validator
    private ResponseEntity<?> handleValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Exeception
    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCountryServiceException(CountryNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CountryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleCountryAlreadyExistsException(CountryAlreadyExistsException e) {
        return e.getMessage();
    }
}