package com.example.travelagency.business.service;

import com.example.travelagency.business.strategy.VacationStrategy;
import com.example.travelagency.model.ResponseMessage;
import com.example.travelagency.model.Tour;
import com.example.travelagency.repository.TourRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TourService implements VacationStrategy<Tour> {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }


    // add/modify/delete tour
    @Override
    public ResponseEntity<String> addVacation(Tour vacation) {
        try {
            tourRepository.save(vacation);
            return new ResponseEntity<>(ResponseMessage.VACATION_CREATED_SUCCESSFULLY.getMessage(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseMessage.VACATION_CREATION_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> updateVacation(Tour vacation) {
        Tour oldTour = tourRepository.findById(vacation.getId()).orElse(null);

        if (oldTour == null) {
            return new ResponseEntity<>(ResponseMessage.VACATION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }


        // Check if the start date is before the end date
        if (vacation.getStartDate().isAfter(vacation.getEndDate())) {
            return new ResponseEntity<>(ResponseMessage.INVALID_DATE_RANGE.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // Update the old tour with the new information
        oldTour.setName(vacation.getName());
        oldTour.setPrice(vacation.getPrice());
        oldTour.setStartDate(vacation.getStartDate());
        oldTour.setEndDate(vacation.getEndDate());
        oldTour.setDescription(vacation.getDescription());
        oldTour.setLocation(vacation.getLocation());

        tourRepository.save(oldTour);
        return new ResponseEntity<>(ResponseMessage.VACATION_UPDATED_SUCCESSFULLY.getMessage(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteVacation(long id) {
        Tour tour = tourRepository.findById(id).orElse(null);

        if (tour == null) {
            return new ResponseEntity<>(ResponseMessage.VACATION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }

        tourRepository.delete(tour);

        return new ResponseEntity<>(ResponseMessage.VACATION_DELETED_SUCCESSFULLY.getMessage(), HttpStatus.OK);
    }
}
