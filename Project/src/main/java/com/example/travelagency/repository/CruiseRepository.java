package com.example.travelagency.repository;

import com.example.travelagency.model.Cruise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {
}
