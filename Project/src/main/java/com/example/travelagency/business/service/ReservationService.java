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
            reservation.setVacation(vacation);
            reservation.setClient(client);
            reservationRepository.save(reservation);
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

        // Update the old reservation with the new information
        oldReservation.setClient(modifiedReservation.getClient());
        oldReservation.setVacation(modifiedReservation.getVacation());


        reservationRepository.save(oldReservation);
        return new ResponseEntity<>(ResponseMessage.VACATION_UPDATED_SUCCESSFULLY.getMessage(), HttpStatus.OK);
    }



}
