package com.magasinEnLigne.ecommerce.controller;

import com.magasinEnLigne.ecommerce.entity.State;
import com.magasinEnLigne.ecommerce.exception.StateAlreadyExistsException;
import com.magasinEnLigne.ecommerce.exception.StateNotFoundException;
import com.magasinEnLigne.ecommerce.service.StateService;
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
@RequestMapping("/api/admin/states")
public class StateController {

    private final StateService stateService;
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<State>> getAllStates() {
        List<State> states = stateService.getAllStates();
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable int id) {
        State state = stateService.getStateById(id);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addState(@Valid @RequestBody State state, BindingResult result) {
        try {
            State stateAdd = stateService.addState(state);
            return new ResponseEntity<>(stateAdd, HttpStatus.CREATED);
        } catch (StateAlreadyExistsException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>  updateStateById(@PathVariable int id, @Valid @RequestBody State state, BindingResult result) {
//        if (result.hasErrors()) {
//            return handleValidationErrors(result);
//        }
//        stateService.updateState(id, state);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        try {
            stateService.updateState(id, state);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (StateNotFoundException e) {
            // Gérer l'exception de l'état non trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (StateAlreadyExistsException e) {
            // Gérer l'exception de l'état déjà existant
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<State> deleteStateById(@PathVariable int id) {
        stateService.deleteStateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<?> handleValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
