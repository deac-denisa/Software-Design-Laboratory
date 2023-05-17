package com.example.travelagency.business.notification;

import com.example.travelagency.model.Reservation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReceiptObserver implements NotificationObserver{

    public ReceiptObserver() {
    }

    @Override
    public void update(Reservation reservation) {
        try {
            writeReservationReceipt(reservation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeReservationReceipt(Reservation reservation) throws IOException {
        String receipt = "Reservation Receipt\n" +
                "-----------------------\n" +
                "Reservation ID: " + reservation.getId() + "\n" +
                "CLIENT DATA ----------------------- :\nClient ID: " + reservation.getClient().getId() + "\n" +
                "Client Name: " + reservation.getClient().getFullName() + "\n" +
                "Client Email: " + reservation.getClient().getEmail() + "\n" +
                "VACATION DATA ---------------------:\nVacation ID: " + reservation.getVacation().getId() + "\n" +
                "Vacation Type: " + reservation.getVacation().getType() + "\n" +
                "Vacation Name: " + reservation.getVacation().getName() + "\n" +
                "Vacation Description: " + reservation.getVacation().getDescription() + "\n" +
                "Location: " + reservation.getVacation().getLocation() + "\n" +
                "Start Date: " + reservation.getVacation().getStartDate() + "\n" +
                "End Date: " + reservation.getVacation().getEndDate() + "\n"+
                "Price: " + reservation.getVacation().getPrice() + "\n";

        String filePath = "D:\\an3\\SEM 2\\SD\\Project\\travel-agency\\src\\main\\resources\\receipt.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(receipt);
        } catch (IOException e) {
            throw e;
        }
    }
}
