package com.example.travelagency.controller;

import com.example.travelagency.business.service.ClientService;
import com.example.travelagency.business.service.VacationService;
import com.example.travelagency.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController {

    @Autowired
    private VacationService vacationService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/{email}/{password}/reservations")
    public ResponseEntity<?> getReservations(@PathVariable String email, @PathVariable String password) {
        boolean login = clientService.validateClient(email, password);
        if (!login) {
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return vacationService.getAllVacations();
    }

}