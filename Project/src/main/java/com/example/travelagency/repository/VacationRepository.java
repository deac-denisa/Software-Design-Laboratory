package com.example.travelagency.repository;

import com.example.travelagency.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Vacation findVacationById(Long id);
}
