package com.example.travelagency.repository;

import com.example.travelagency.model.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Long> {

}
