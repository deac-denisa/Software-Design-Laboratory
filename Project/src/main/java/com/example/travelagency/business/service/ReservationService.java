package com.example.travelagency.business.service;

import com.example.travelagency.business.notification.EmailObserver;
import com.example.travelagency.business.notification.NotificationSubject;
import com.example.travelagency.business.notification.ReceiptObserver;
import com.example.travelagency.model.Client;
import com.example.travelagency.model.Reservation;
import com.example.travelagency.model.ResponseMessage;
import com.example.travelagency.model.Vacation;
import com.example.travelagency.repository.ClientRepository;
import com.example.travelagency.repository.ReservationRepository;
import com.example.travelagency.repository.VacationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService extends NotificationSubject {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final VacationRepository vacationRepository;
    private final ReceiptObserver receiptObserver;
    private EmailObserver emailObserver;

    public ReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository, VacationRepository vacationRepository, EmailObserver emailObserver) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.vacationRepository = vacationRepository;
        receiptObserver = new ReceiptObserver();
        this.emailObserver = emailObserver;
        addObserver(receiptObserver);
        addObserver(emailObserver);

    }

    public void deleteAllReservWithVac(Long id) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation r : reservations) {
            if (Objects.equals(r.getVacation().getId(), id)) {
                reservationRepository.delete(r);
            }
        }

    }

    public ResponseEntity<String> addReservation(Reservation reservation) {
        try {
            Client client = clientRepository.getReferenceById(reservation.getClient().getId());
            Vacation vacation = vacationRepository.findVacationById(reservation.getVacation().getId());
            reservation.setClient(client);
            reservationRepository.save(reservation);
            //subtract nr seats
            int seats = vacation.getAvailableSeats() - reservation.getNrSeats();
            if( seats >= 0){
                vacation.setAvailableSeats(seats);
                vacationRepository.save(vacation);
                reservation.setVacation(vacation);
            }
            else{
                return new ResponseEntity<>(ResponseMessage.RESERVATION_NOT_ENOUGH_SEATS.getMessage(), HttpStatus.BAD_REQUEST);
            }
            notifyObservers(reservation); // notify observers
            return new ResponseEntity<>(ResponseMessage.RESERVATION_CREATED_SUCCESSFULLY.getMessage(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseMessage.RESERVATION_CREATION_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> modifyReservation(Reservation modifiedReservation) {
        Reservation oldReservation = reservationRepository.findById(modifiedReservation.getId()).orElse(null);

        if (oldReservation == null) {
            return new ResponseEntity<>(ResponseMessage.VACATION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }

        //check if there are available seats, modify them for given concert
        int oldSeats = oldReservation.getNrSeats();
        int oldVac = oldReservation.getVacation().getAvailableSeats();
        int newASeats = modifiedReservation.getVacation().getAvailableSeats();
        Vacation vacation = oldReservation.getVacation();
        //diff concert
        if (oldReservation.getId() != vacation.getId()) {
            //refil stock for old
            vacation.setAvailableSeats(oldSeats + oldVac);
            vacationRepository.save(vacation);

            if (!verifyStock(newASeats, modifiedReservation.getNrSeats())) {
                return new ResponseEntity<>(ResponseMessage.RESERVATION_NOT_ENOUGH_SEATS.getMessage(), HttpStatus.BAD_REQUEST);
            }
            vacation.setAvailableSeats(newASeats - modifiedReservation.getNrSeats());
            vacationRepository.save(vacation);

            // Update the old reservation with the new information
            oldReservation.setClient(modifiedReservation.getClient());
            oldReservation.setVacation(modifiedReservation.getVacation());
            oldReservation.setNrSeats(modifiedReservation.getNrSeats());
            reservationRepository.save(oldReservation);
            return new ResponseEntity<>(ResponseMessage.RESERVATION_UPDATED_SUCCESSFULLY.getMessage(), HttpStatus.OK);

        } else {
            //same vacation different seats
            if (oldSeats < modifiedReservation.getNrSeats()) {
                int tDiff = modifiedReservation.getNrSeats() - oldSeats;
                //modif/decrease stock for remaining tickets, but first check if there are available tickets
                if (verifyStock(oldVac, tDiff)) {
                    vacation.setAvailableSeats(oldVac - tDiff);
                    vacationRepository.save(vacation);
                } else {
                    return new ResponseEntity<>(ResponseMessage.RESERVATION_NOT_ENOUGH_SEATS.getMessage(), HttpStatus.BAD_REQUEST);

                }
            }
            //smaller nr of seats than before
            else if(oldSeats > modifiedReservation.getNrSeats()){
                //refill/increase stock
                int tDiff = oldSeats - modifiedReservation.getNrSeats();
                vacation.setAvailableSeats(oldVac - tDiff);
                vacationRepository.save(vacation);
            }
            //continue to update the rest of data
            oldReservation.setClient(modifiedReservation.getClient());
            oldReservation.setVacation(modifiedReservation.getVacation());
            oldReservation.setNrSeats(modifiedReservation.getNrSeats());
            return new ResponseEntity<>(ResponseMessage.RESERVATION_UPDATED_SUCCESSFULLY.getMessage(), HttpStatus.OK);

        }

    }

    private boolean verifyStock( int oldStock, int nrSeats ){
        return oldStock - nrSeats >= 0;
    }
}
