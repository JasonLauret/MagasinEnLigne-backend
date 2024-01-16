package com.magasinEnLigne.ecommerce.service;

import com.magasinEnLigne.ecommerce.entity.State;

import java.util.List;

public interface StateService {

    List<State> getAllStates();

    State getStateById(int id);

    State addState(State state);

    void updateState(int id, State state);

    void deleteStateById(int id);

    List<State> getByCountryCode(String code);
}
