package com.untold.untold.business.service;

import com.untold.untold.model.Concert;
import com.untold.untold.model.Ticket;
import com.untold.untold.persistance.ConcertRepository;
import com.untold.untold.persistance.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ConcertRepository concertRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ConcertRepository concertRepository) {
        this.ticketRepository = ticketRepository;
        this.concertRepository = concertRepository;
    }

    public List<Ticket> getTicketsByConcertId(Long concertId) {
        return ticketRepository.findByConcertId(concertId);
    }

    //sell = create ticket and modify stock of tickets
    public Ticket sellTicket( Ticket t){
        Concert concert = concertRepository.getReferenceById(t.getConcert().getId() );
        t.setConcert(concert);

        int maxTickets = concert.getMaxTickets();
        int diff = maxTickets - t.getSeats();
        if(diff >= 0 ){
            concert.setMaxTickets( diff );
            concertRepository.save(concert);    //update stock
            System.out.println("sucessfull tranzaction");
            return ticketRepository.save(t);
        }
        else
            System.out.println("there are not enough tickets available for this concert");
        return null;
    }

    //cancel reservation/ticket = delete and redo stock
    public void cancelTicket( long id){
        Ticket ticket = ticketRepository.getReferenceById(id);
        Concert concert = concertRepository.getReferenceById( id );
        int newStock = concert.getMaxTickets() + ticket.getSeats();
        concert.setMaxTickets(newStock);
        concertRepository.save(concert);
        ticketRepository.delete(ticket);
        System.out.println("Sock restored. Ticket canceled\n");
    }

    public String editTicket( Ticket t) {
        Ticket oldTicket = ticketRepository.getReferenceById(t.getId());
        Concert oldConcert = concertRepository.getReferenceById(oldTicket.getConcert().getId());
        int oldSeats = oldTicket.getSeats();
        int oldMaxT = oldConcert.getMaxTickets();

        Concert newConcert = concertRepository.getReferenceById(t.getConcert().getId());
        System.out.println(newConcert.getId()  + " "+ newConcert.getName());
        int maxT = newConcert.getMaxTickets();

        // modify the concert
        if(newConcert.getId() != oldConcert.getId()){
            //refill stock for old concert ticket
            oldConcert.setMaxTickets(oldMaxT+oldSeats);
            concertRepository.save(oldConcert);
            //modify stock for new concert
            if( !verifyStock(maxT, t.getSeats())) {
                return  "Not enough tickets. Remaining: "+ maxT;
            }
            newConcert.setMaxTickets(maxT-t.getSeats());
            concertRepository.save(newConcert);
            //make sure to save all new data of the ticket
            oldTicket.setConcert(newConcert);
            oldTicket.setPerson(t.getPerson());
            oldTicket.setPrice(t.getPrice());
            oldTicket.setSeats(t.getSeats());
            ticketRepository.save(oldTicket);
            return "edited successfully. New Ticket: "+ oldTicket.toString2();
        }
        else{       //same concert, different data/seats
            //check the nr of seats to update stock if needed
            // grater nr of seats than before
            if(oldSeats < t.getSeats()){
                int tDiff = t.getSeats() - oldSeats;
                //modif/decrease stock for remaining tickets, but first check if there are available tickets
                if(verifyStock(oldMaxT, tDiff)){
                    oldConcert.setMaxTickets(oldMaxT-tDiff);
                    concertRepository.save(oldConcert);
                }
                else {
                    return "Not enough tickets. Remaining: "+ oldMaxT;
                }
            }//smaller nr of seats than before
            else if( oldSeats > t.getSeats()){
                //refill/increase stock
                int tDiff = oldSeats - t.getSeats() ;
                oldConcert.setMaxTickets(oldMaxT+tDiff);
                concertRepository.save(oldConcert);
            }
            //continue to update the rest of data
            oldTicket.setPerson(t.getPerson());
            oldTicket.setPrice(t.getPrice());
            oldTicket.setSeats(t.getSeats());
            oldTicket.setConcert(oldConcert);
            ticketRepository.save(oldTicket);
            return "edited successfully. New Ticket: "+ oldTicket.toString2();
        }

    }

    private boolean verifyStock( int oldStock, int nrSeats ){
        return oldStock - nrSeats >= 0;
    }


}

