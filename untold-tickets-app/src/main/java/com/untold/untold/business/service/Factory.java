package com.untold.untold.business.service;

import com.untold.untold.model.Ticket;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class Factory {

    public void writeToCsv(List<Ticket> ticketList, String header) throws IOException {
        PrintWriter writer = new PrintWriter("D:\\an3\\SEM 2\\SD\\Assignment1\\untold-tickets-app\\src\\main\\resources/tickets.csv");
        writer.println(header);

        for (Ticket ticket : ticketList) {
            writer.println(ticket.toString());
        }
        writer.close();
        System.out.println("tickets exported");
    }
}
