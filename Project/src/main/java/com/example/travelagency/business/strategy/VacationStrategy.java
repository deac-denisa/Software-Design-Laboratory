package com.example.travelagency.business.strategy;

import com.example.travelagency.model.Vacation;
import org.springframework.http.ResponseEntity;

public interface VacationStrategy<T> {
    ResponseEntity<String> addVacation(T vacation);
    ResponseEntity<String> updateVacation(T vacation);
    ResponseEntity<String> deleteVacation(long id);
}
