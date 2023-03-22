package com.untold.untold.business;

import com.untold.untold.model.Admin;import com.untold.untold.model.Concert;
import com.untold.untold.model.Ticket;
import com.untold.untold.persistance.ConcertRepository;
import com.untold.untold.persistance.TicketRepository;
import com.untold.untold.business.service.Encrypt;
import com.untold.untold.business.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class Tests {



    private Encrypt encrypt = new Encrypt();

    private ConcertRepository concertRepository;

    private TicketRepository ticketRepository;

    @Test
    void sellTicketTest() {
        ticketRepository = mock(TicketRepository.class);
        concertRepository = mock(ConcertRepository.class);
        TicketService ticketService = new TicketService(ticketRepository, concertRepository);

        Concert concert = new Concert();
        concert.setId(1L);


        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setConcert(concert);
        ticket.setPerson("John Doe");
        ticket.setPrice(50.0);
        ticket.setSeats(12);
        concertRepository.save(concert);

        assertEquals("there are not enough tickets available for this concert", ticketService.sellTicket(ticket));
    }


    @Test
    void encryptTest(){

        Admin a = new Admin();
        a.setUsername("ana");
        a.setPassword(encrypt.encryptPassword("ana1"));
        assertEquals(a.getPassword(), "12ee195ec1324b1d7f4806da041f3573");
    }
}