
package com.example.travelagency.controller;

//import com.example.travelagency.business.notification.service.*;
import com.example.travelagency.business.service.*;
import com.example.travelagency.business.strategy.Context;
import com.example.travelagency.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CruiseService cruiseService;
    @Autowired
    private TourService tourService;
    @Autowired
    private StayService stayService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VacationService vacationService;


    //client
    @PostMapping("/{email}/{password}/client/add")
    public ResponseEntity<?> addClient(@PathVariable String email, @PathVariable String password,
                                       @RequestBody Client client){
        boolean login = employeeService.validateEmployee(email,password);
        if(!login){
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        else
            return clientService.addClient(client);
    }

    @PutMapping("/{email}/{password}/client/update")
    public ResponseEntity<?> updateClient(@PathVariable String email, @PathVariable String password,
                                       @RequestBody Client client){
        boolean login = employeeService.validateEmployee(email,password);
        if(!login){
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        else
            return clientService.modifyClient(client);
    }


    //vacation
    @PostMapping("/{email}/{password}/vacation/add")
    public ResponseEntity<?> createVacation(@PathVariable String email, @PathVariable String password, @RequestBody Vacation vacation) {
        boolean login = employeeService.validateEmployee(email,password);
        if(!login){
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        VacationType type = vacation.getType();
        switch (type) {
            case CRUISE:
                Cruise cruise = new Cruise(vacation.getName(), vacation.getDescription(),
                        vacation.getLocation(), vacation.getPrice(), vacation.getStartDate(), vacation.getEndDate(), vacation.getAvailableSeats());
                Context<Cruise> contextC = new Context<>(cruiseService);
                return contextC.executeAddStrategy(cruise);
            case STAY:
                Stay stay = new Stay(vacation.getName(), vacation.getType(), vacation.getDescription(),
                        vacation.getLocation(), vacation.getPrice(), vacation.getStartDate(), vacation.getEndDate(), vacation.getAvailableSeats());
                Context<Stay> contextS = new Context<>(stayService);
                return contextS.executeAddStrategy(stay);
            case TOUR:
                Tour tour = new Tour(vacation.getName(), vacation.getType(), vacation.getDescription(),
                        vacation.getLocation(), vacation.getPrice(), vacation.getStartDate(), vacation.getEndDate(), vacation.getAvailableSeats());
                Context<Tour> contextT = new Context<>(tourService);
                return contextT.executeAddStrategy(tour);
            default:
                return new ResponseEntity<>(ResponseMessage.VACATION_INVALID_TYPE.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{email}/{password}/vacation/update")
    public ResponseEntity<?> updateVacation(@PathVariable String email, @PathVariable String password,
                                          @RequestBody Vacation vacation){
        boolean login = employeeService.validateEmployee(email,password);
        if(!login){
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        VacationType type = vacation.getType();
        switch (type) {
            case CRUISE:
                Cruise cruise = new Cruise(vacation.getName(), vacation.getDescription(),
                        vacation.getLocation(), vacation.getPrice(), vacation.getStartDate(), vacation.getEndDate(), vacation.getAvailableSeats());
                cruise.setId(vacation.getId());
                Context<Cruise> contextC = new Context<>(cruiseService);
                return contextC.executeUpdateStrategy(cruise);
            case STAY:
                Stay stay = new Stay(vacation.getName(), vacation.getType(), vacation.getDescription(),
                        vacation.getLocation(), vacation.getPrice(), vacation.getStartDate(), vacation.getEndDate(), vacation.getAvailableSeats());
                stay.setId(vacation.getId());
                Context<Stay> contextS = new Context<>(stayService);
                return contextS.executeUpdateStrategy(stay);
            case TOUR:
                Tour tour = new Tour(vacation.getName(), vacation.getType(), vacation.getDescription(),
                        vacation.getLocation(), vacation.getPrice(), vacation.getStartDate(), vacation.getEndDate(),vacation.getAvailableSeats());
                tour.setId(vacation.getId());
                Context<Tour> contextT = new Context<>(tourService);
                return contextT.executeUpdateStrategy(tour);
            default:
                return new ResponseEntity<>(ResponseMessage.VACATION_INVALID_TYPE.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{email}/{password}/vacation/delete")
    public ResponseEntity<?> deleteVacation(@PathVariable String email, @PathVariable String password,
                                            @RequestBody Vacation vacation){
        boolean login = employeeService.validateEmployee(email,password);
        if(!login){
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        VacationType type = vacation.getType();
        switch (type) {
            case CRUISE:
                //delete reservations
                System.out.println(vacation.getId());
                reservationService.deleteAllReservWithVac(vacation.getId());
                Context<Cruise> contextC = new Context<>(cruiseService);
                return contextC.executeDeleteStrategy(vacation.getId());
            case STAY:
                reservationService.deleteAllReservWithVac(vacation.getId());
                Context<Stay> contextS = new Context<>(stayService);
                return contextS.executeDeleteStrategy(vacation.getId());
            case TOUR:
                reservationService.deleteAllReservWithVac(vacation.getId());
                Context<Tour> contextT = new Context<>(tourService);
                return contextT.executeDeleteStrategy(vacation.getId());
            default:
                return new ResponseEntity<>(ResponseMessage.VACATION_INVALID_TYPE.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{email}/{password}/reservation/add")
    public ResponseEntity<?> createReservation(@PathVariable String email, @PathVariable String password, @RequestBody Reservation reservation) {
        boolean login = employeeService.validateEmployee(email, password);
        if (!login) {
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        //verify if the Client and the vacation exist
        if (vacationService.findVacation(reservation.getVacation()) && clientService.findClient(reservation.getClient())) {
            return reservationService.addReservation(reservation);
        }
        return new ResponseEntity<>(ResponseMessage.INVALID_CLIENT_VACATION.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/{email}/{password}/reservation/update")
    public ResponseEntity<?> updateReservation(@PathVariable String email, @PathVariable String password, @RequestBody Reservation reservation) {
        boolean login = employeeService.validateEmployee(email, password);
        if (!login) {
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        //verify if the Client and the vacation exist
        if (vacationService.findVacation(reservation.getVacation()) && clientService.findClient(reservation.getClient())) {
            return reservationService.modifyReservation(reservation);
        }
        return new ResponseEntity<>(ResponseMessage.INVALID_CLIENT_VACATION.getMessage(), HttpStatus.BAD_REQUEST);

    }



}
