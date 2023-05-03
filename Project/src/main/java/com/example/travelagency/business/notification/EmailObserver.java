package com.example.travelagency.business.notification;

import com.example.travelagency.model.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailObserver implements NotificationObserver {

   @Autowired
   private final EmailService emailService;

    @Autowired
    public EmailObserver(EmailService emailService) {

        this.emailService = emailService;
    }

    @Override
    public void update(Reservation reservation) {
        sendEmail(reservation);
        System.out.println("Email sent successfully.");
    }

    private void sendEmail(Reservation reservation){
        String emailTo = reservation.getClient().getEmail();
        String subject = "Receipt for new Reservation";
        String text = "Reservation Receipt\n" +
                "---------------------------------------------------------------------------\n" +
                "Reservation ID: " + reservation.getId() + "\n" +
                "CLIENT INFORMATION ---------------------------------------------------------------\n" +
                "Client ID: " + reservation.getClient().getId() + "\n" +
                "Client Name: " + reservation.getClient().getFullName() + "\n" +
                "Client Email: " + reservation.getClient().getEmail() + "\n" +
                "VACATION INFORMATION -------------------------------------------------------------\n" +
                "Vacation ID: " + reservation.getVacation().getId() + "\n" +
                "Vacation Type: " + reservation.getVacation().getType() + "\n" +
                "Vacation Name: " + reservation.getVacation().getName() + "\n" +
                "Vacation Description: " + reservation.getVacation().getDescription() + "\n" +
                "Location: " + reservation.getVacation().getLocation() + "\n" +
                "Start Date: " + reservation.getVacation().getStartDate() + "\n" +
                "End Date: " + reservation.getVacation().getEndDate() + "\n"+
                "Price: " + reservation.getVacation().getPrice() + "\n";
        emailService.sendSimpleMessage(emailTo, subject, text);

    }
}
