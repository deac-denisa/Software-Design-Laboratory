package com.example.travelagency.repository;

import com.example.travelagency.model.Cruise;
import com.example.travelagency.model.Reservation;
import com.example.travelagency.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByVacationId(Long id);

    List<Reservation> findReservationsByVacationId(Long id);
}
