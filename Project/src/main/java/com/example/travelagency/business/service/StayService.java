package com.example.travelagency.business.service;

import com.example.travelagency.business.strategy.VacationStrategy;
import com.example.travelagency.model.ResponseMessage;
import com.example.travelagency.model.Stay;
import com.example.travelagency.repository.StayRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StayService implements VacationStrategy<Stay> {

    private final StayRepository stayRepository;

    public StayService(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }


    // add/modify/delete stay
    @Override
    public ResponseEntity<String> addVacation(Stay vacation) {
        try {
            stayRepository.save(vacation);
            return new ResponseEntity<>(ResponseMessage.VACATION_CREATED_SUCCESSFULLY.getMessage(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseMessage.VACATION_CREATION_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateVacation(Stay vacation) {
        Stay oldStay = stayRepository.findById(vacation.getId()).orElse(null);

        if (oldStay == null) {
            return new ResponseEntity<>(ResponseMessage.VACATION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }


        // Check if the start date is before the end date
        if (vacation.getStartDate().isAfter(vacation.getEndDate())) {
            return new ResponseEntity<>(ResponseMessage.INVALID_DATE_RANGE.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // Update the old stay with the new information
        oldStay.setName(vacation.getName());
        oldStay.setPrice(vacation.getPrice());
        oldStay.setStartDate(vacation.getStartDate());
        oldStay.setEndDate(vacation.getEndDate());
        oldStay.setDescription(vacation.getDescription());
        oldStay.setLocation(vacation.getLocation());
        // oldStay.setSightseeingTrips(vacation.getSightseeingTrips());

        stayRepository.save(oldStay);
        return new ResponseEntity<>(ResponseMessage.VACATION_UPDATED_SUCCESSFULLY.getMessage(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteVacation(long id) {
        Stay stay = stayRepository.findById(id).orElse(null);

        if (stay == null) {
            return new ResponseEntity<>(ResponseMessage.VACATION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }

        stayRepository.delete(stay);

        return new ResponseEntity<>(ResponseMessage.VACATION_DELETED_SUCCESSFULLY.getMessage(), HttpStatus.OK);
    }
}
