package com.example.travelagency.business.strategy;

import org.springframework.http.ResponseEntity;

public class Context<T> {

    private VacationStrategy<T> vacationStrategy;

    public Context(VacationStrategy<T> vacationStrategy) {
        this.vacationStrategy = vacationStrategy;
    }

    public ResponseEntity<?> executeAddStrategy(T vacation){
        return vacationStrategy.addVacation(vacation);
    }

    public ResponseEntity<?> executeUpdateStrategy(T vacation){
        return vacationStrategy.updateVacation(vacation);
    }

    public ResponseEntity<?> executeDeleteStrategy(Long id){
        return vacationStrategy.deleteVacation(id);
    }
}
