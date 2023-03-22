package com.untold.untold.controller;

import com.untold.untold.business.service.*;
import com.untold.untold.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdministratorController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CashierService cashierService;
    @Autowired
    private ConcertService concertService;
    @Autowired
    private BandService bandService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private Factory factory;

    @PostMapping("/{user}/{passw}/add")
    private Admin newAdmin(@PathVariable String user, @PathVariable String passw, @RequestBody Admin admin) {
        if( adminService.validateAdmin(user, passw) ) {
            System.out.println("admin found");
            return adminService.createAdmin(admin);
        }
        return  null;
    }

    //crud on cashier
    @PostMapping("/{user}/{passw}/cashier/add")
    private Cashier newCashier(@PathVariable String user, @PathVariable String passw, @RequestBody Cashier cashier){
        if( adminService.validateAdmin(user, passw) ) {
            System.out.println((cashier + "\n"));
            return cashierService.createCashier(cashier);
        }
        return null;
    }

    @GetMapping("/{user}/{passw}/cashier/get")
    private String getCashier(@PathVariable String user, @PathVariable String passw, @RequestBody Cashier cashier)
    {
        if( adminService.validateAdmin(user, passw) ) {
            Cashier c = cashierService.getCashier(cashier);
            if ( c != null)
                return "cashier found: "+ c.toString();
            return "cashier not found";
        }
       return "invalid admin credentials";
    }

    @PutMapping("/{user}/{passw}cashier/update")
    private Cashier updateCashier(@PathVariable String user, @PathVariable String passw, @RequestBody Cashier c){
        if( adminService.validateAdmin(user, passw) )
            return cashierService.updateCashier(c);
        return null;
    }

    @DeleteMapping("/{user}/{passw}/cashier/delete")
    private String deleteCashier(@PathVariable String user, @PathVariable String passw, @RequestBody long id){
        if( adminService.validateAdmin(user, passw) ) {
            cashierService.deleteCashier(id);
            return "cashier deleted successfully";
        }
        return " invalid admin credentials ";
    }


    //crud on concert
    @PostMapping("/{user}/{passw}/concert/add")
    private Concert newConcert(@PathVariable String user, @PathVariable String passw, @RequestBody Concert concert){
        if( adminService.validateAdmin(user, passw) )
            return concertService.createConcert(concert);
        return null;
    }

    @GetMapping("/{user}/{passw}/concert/get/{id}")
    private Concert getConcert(@PathVariable String user, @PathVariable String passw, @PathVariable long id){
        if( adminService.validateAdmin(user, passw) )
            return concertService.getConcertById(id);
        return null;
    }

    @PutMapping("/{user}/{passw}/concert/update")
    private Concert updateConcert(@PathVariable String user, @PathVariable String passw, @RequestBody Concert concert){
        if( adminService.validateAdmin(user, passw) )
            return concertService.updateConcert(concert);
        return null;
    }

    @DeleteMapping("/{user}/{passw}/concert/delete/{id}")
    private String deleteConcert(@PathVariable String user, @PathVariable String passw, @PathVariable long id){
        if( adminService.validateAdmin(user, passw) ){
            //delete all tickets for that concert
            List<Ticket> tickets = ticketService.getTicketsByConcertId(id);
            for( Ticket t: tickets){
                ticketService.cancelTicket(t.getId());
            }
            concertService.deleteConcert(id);
            return "concert deleted";
        }
        return "invalid admin credentials";
    }


    //crud on band
    @PostMapping("/{user}/{passw}/band/add")
    private Band newBand(@PathVariable String user, @PathVariable String passw, @RequestBody Band band){
        if( adminService.validateAdmin(user, passw) )
            return bandService.createBand(band);
        return null;
    }

    @GetMapping("/{user}/{passw}/band/get/{id}")
    private Band getBand(@PathVariable String user, @PathVariable String passw, @PathVariable long id){
        if( adminService.validateAdmin(user, passw) )
            return bandService.getBandById(id);
        return null;
    }

    @PutMapping("/{user}/{passw}/band/update")
    private Band updateBand(@PathVariable String user, @PathVariable String passw, @RequestBody Band band){
        if( adminService.validateAdmin(user, passw) )
            return bandService.updateBand(band);
        return null;
    }

    @DeleteMapping("/{user}/{passw}/band/delete/{id}")
    private String deleteBand(@PathVariable String user, @PathVariable String passw, @PathVariable long id){
        if( adminService.validateAdmin(user, passw) ) {
            //first delete from concert
            List<Concert> concerts = concertService.findConcertByBand(id);
            Band b = bandService.getBandById(id);
            for (Concert c : concerts) {
                c.getBands().remove(b);
                concertService.updateConcert(c);
            }
            bandService.deleteBandById(id);
            return "band deleted";
        }
        return "invalid amin credentials";
    }


    //list of tickets
    @GetMapping("/{user}/{passw}/tickets/{id}")
    private  List<Ticket> exportTickets(@PathVariable String user, @PathVariable String passw, @PathVariable long id){
        if( adminService.validateAdmin(user, passw) ) {
            List<Ticket> tickets = ticketService.getTicketsByConcertId(id);
            String header = "id,price,concert,person,seat";
            try {
                factory.writeToCsv(tickets, header);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tickets;
        }
        return null;
    }

}
