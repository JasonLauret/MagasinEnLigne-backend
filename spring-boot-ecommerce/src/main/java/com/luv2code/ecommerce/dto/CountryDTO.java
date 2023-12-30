package com.luv2code.ecommerce.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data // Lombok génère automatiquement les getters, setters, toString, equals et hashCode
public class CountryDTO {

    @Size(min = 2, max = 2, message = "Le code du pays doit avoir exactement 2 caractères")
    private String code; // Code du pays, souvent utilisé pour identification

    @Size(min = 2, message = "Le nom du pays doit avoir au moins 2 caractères")
    private String name; // Nom du pays
}