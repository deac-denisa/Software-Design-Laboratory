package com.untold.untold.controller;

import com.untold.untold.model.Ticket;
import com.untold.untold.business.service.CashierService;
import com.untold.untold.business.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cashier")
@CrossOrigin
public class CashierController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private CashierService cashierService;

    @PostMapping("/{user}/{passw}/sell")
    public String sellTicket(@PathVariable String user, @PathVariable String passw, @RequestBody Ticket ticket) {
         if( cashierService.validateCashier(user, passw) ) {
             ticketService.sellTicket(ticket);
             return "successful transaction" + ticket.toString();
         }
         return "invalid cashier credentials";
    }

    @PutMapping("/{user}/{passw}/edit")
    public String editTicket(@PathVariable String user, @PathVariable String passw, @RequestBody Ticket ticket) {
        if( cashierService.validateCashier(user, passw) ) {
            return  ticketService.editTicket(ticket);
        }
        else
            return null;
    }

    @DeleteMapping("/{user}/{passw}/cancel/{id}")
    public String  cancelTicket(@PathVariable String user, @PathVariable String passw, @PathVariable long id) {
        if( cashierService.validateCashier(user, passw) ) {
            ticketService.cancelTicket(id);
            return "ticket canceled";
        }
        return "cancellation failed";
    }
}
