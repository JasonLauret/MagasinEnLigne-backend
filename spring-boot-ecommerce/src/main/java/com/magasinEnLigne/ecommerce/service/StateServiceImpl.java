package com.magasinEnLigne.ecommerce.service;

import com.magasinEnLigne.ecommerce.dao.StateRepository;
import com.magasinEnLigne.ecommerce.entity.State;
import com.magasinEnLigne.ecommerce.exception.StateAlreadyExistsException;
import com.magasinEnLigne.ecommerce.exception.StateNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public List<State> getAllStates() {
        List<State> states = stateRepository.findAll();
        if (states.isEmpty()) {
            throw new StateNotFoundException("Aucun état trouvé");
        }
        return states;
    }

    @Override
    public List<State> getByCountryCode(String code) {
        List<State> states = stateRepository.findByCountryCode(code);
        if (states.isEmpty()) {
            throw new StateNotFoundException("Aucun état trouvé");
        }
        return states;
    }

    @Override
    public State getStateById(int id) {
        Optional<State> stateOptional = stateRepository.findById(id);
        return stateOptional.orElseThrow(() ->
                new StateNotFoundException("Etat non trouvé pour l'id : " + id)
        );
    }

    @Override
    public State addState(State state) {
        if (stateRepository.existsByName(state.getName())) {
            throw new StateAlreadyExistsException("Cette état existe déjà");
        }
        return stateRepository.save(state);
    }

    @Override
    public void updateState(int id, State state) {
        State stateToUpdate = stateRepository.findById(id)
                .orElseThrow(() -> new StateNotFoundException("Aucun état avec l'ID: " + id));
        if (stateRepository.existsByName(state.getName())) {
            throw new StateAlreadyExistsException("Cette état existe déjà");
        }
        stateToUpdate.setId(state.getId());
        stateToUpdate.setName(state.getName());
        stateRepository.save(stateToUpdate);

//        State stateToUpdate = stateRepository.findById(id)
//                .orElseThrow(() -> new StateNotFoundException("Aucun état avec l'ID: " + id));
//
//        if (stateRepository.existsByName(state.getName())) {
//            throw new StateAlreadyExistsException("Cette état existe déjà");
//        }
//
//        stateToUpdate.setId(state.getId());
//        stateToUpdate.setName(state.getName());
//
//        try {
//            stateRepository.save(stateToUpdate);
//        } catch (DataIntegrityViolationException ex) {
//            // Interceptez l'exception de violation de l'intégrité des données (conflit de nom)
//            throw new StateAlreadyExistsException("Le nom de l'état est déjà utilisé.");
//        }
    }

    @Override
    public void deleteStateById(int id) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new StateNotFoundException("Aucun état avec l'ID: " + id));
        stateRepository.delete(state);
    }
}
