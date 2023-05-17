package com.example.travelagency.business.service;

import com.example.travelagency.business.strategy.VacationStrategy;
import com.example.travelagency.model.ResponseMessage;
import com.example.travelagency.model.Cruise;
import com.example.travelagency.repository.CruiseRepository;
import com.example.travelagency.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CruiseService implements VacationStrategy<Cruise> {

    private final CruiseRepository cruiseRepository;
    private final ReservationRepository reservationRepository;

    public CruiseService(CruiseRepository cruiseRepository, ReservationRepository reservationRepository) {
        this.cruiseRepository = cruiseRepository;
        this.reservationRepository = reservationRepository;
    }

    
    // add/modify/delete cruise
    @Override
    public ResponseEntity<String> addVacation(Cruise vacation) {
        try {
            cruiseRepository.save(vacation);
            return new ResponseEntity<>(ResponseMessage.VACATION_CREATED_SUCCESSFULLY.getMessage(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseMessage.VACATION_CREATION_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateVacation(Cruise vacation) {
        Cruise oldCruise = cruiseRepository.findById(vacation.getId()).orElse(null);

        if (oldCruise == null) {
            return new ResponseEntity<>(ResponseMessage.VACATION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }


        // Check if the start date is before the end date
        if (vacation.getStartDate().isAfter(vacation.getEndDate())) {
            return new ResponseEntity<>(ResponseMessage.INVALID_DATE_RANGE.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // Update the old cruise with the new information
        oldCruise.setName(vacation.getName());
        oldCruise.setPrice(vacation.getPrice());
        oldCruise.setStartDate(vacation.getStartDate());
        oldCruise.setEndDate(vacation.getEndDate());
        oldCruise.setDescription(vacation.getDescription());
        oldCruise.setLocation(vacation.getLocation());

        cruiseRepository.save(oldCruise);
        return new ResponseEntity<>(ResponseMessage.VACATION_UPDATED_SUCCESSFULLY.getMessage(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteVacation(long id) {
        Cruise cruise = cruiseRepository.findById(id).orElse(null);

        if (cruise == null) {
            return new ResponseEntity<>(ResponseMessage.VACATION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }

        cruiseRepository.delete(cruise);

        return new ResponseEntity<>(ResponseMessage.VACATION_DELETED_SUCCESSFULLY.getMessage(), HttpStatus.OK);

    }
}
