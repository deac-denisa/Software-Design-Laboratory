package com.example.travelagency.business.xml;

import com.example.travelagency.business.service.*;
import com.example.travelagency.model.*;
import com.example.travelagency.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;


@Component
public class UpdateXml {

    @Autowired
    private final ClientService clientsService;
    @Autowired
    private final VacationService vacationService;
    @Autowired
    private final CruiseService cruiseService;
    @Autowired
    private final TourService tourService;
    @Autowired
    private final StayService stayService;
    @Autowired
    private final VacationRepository vacationRepository;


    public UpdateXml(ClientService clientsService, VacationService vacationService, CruiseService cruiseService, TourService tourService, StayService stayService, VacationRepository vacationRepository) {
        this.clientsService = clientsService;
        this.vacationService = vacationService;
        this.cruiseService = cruiseService;
        this.tourService = tourService;
        this.stayService = stayService;
        this.vacationRepository = vacationRepository;
    }

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void updateClients() {

        // Parse the XML file to extract the updated client information
        List<Client> clientList = parseClientsXml();

        // Update each client with the new information
        for (Client client : clientList) {
            if(client.getId() != null) {
                System.out.println(client.getFullName());
                clientsService.modifyClient(client);
            }
            else{
                clientsService.addClient(client);
            }
        }
    }



    public List<Client> parseClientsXml(){

        File xmlFile = new File("D:\\an3\\SEM 2\\SD\\Project\\travel-agency\\src\\main\\resources\\clients.xml");
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ClientList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ClientList clientList = (ClientList) jaxbUnmarshaller.unmarshal(xmlFile);

            return clientList.getClients();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

//    @Scheduled(fixedRate = 60000) // run every minute
//    public void updateVacations() {
//
//        // Parse the XML file to extract the updated client information
//        List<Vacation> vacationList = parseVacationsXml();
//
//        // Update each client with the new information
//        for (Vacation vacation: vacationList) {
//            System.out.println(vacation.getName());
//
//            Vacation oldVacation = vacationRepository.findVacationById(vacation.getId());
//
//            VacationType type = vacation.getType();
//            switch (type) {
//                case CRUISE:
//                    Cruise cruise = new Cruise(vacation.getName(), vacation.getDescription(),
//                            vacation.getLocation(), vacation.getPrice(), oldVacation.getStartDate(), oldVacation.getEndDate(), vacation.getAvailableSeats());
//                    cruise.setId(vacation.getId());
//                    Context<Cruise> contextC = new Context<>(cruiseService);
//                    contextC.executeUpdateStrategy(cruise);
//                case STAY:
//                    Stay stay = new Stay(vacation.getName(), vacation.getType(), vacation.getDescription(),
//                            vacation.getLocation(), vacation.getPrice(), oldVacation.getStartDate(), oldVacation.getEndDate(), vacation.getAvailableSeats());
//                    stay.setId(vacation.getId());
//                    Context<Stay> contextS = new Context<>(stayService);
//                    contextS.executeUpdateStrategy(stay);
//                case TOUR:
//                    Tour tour = new Tour(vacation.getName(), vacation.getType(), vacation.getDescription(),
//                            vacation.getLocation(), vacation.getPrice(), oldVacation.getStartDate(), oldVacation.getEndDate(),vacation.getAvailableSeats());
//                    tour.setId(vacation.getId());
//                    Context<Tour> contextT = new Context<>(tourService);
//                    contextT.executeUpdateStrategy(tour);
//            }
//        }
//    }



    public List<Vacation> parseVacationsXml(){

        File xmlFile = new File("D:\\an3\\SEM 2\\SD\\Project\\travel-agency\\src\\main\\resources\\vacations.xml");
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(VacationList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            VacationList vacationList = (VacationList) jaxbUnmarshaller.unmarshal(xmlFile);

            return vacationList.getVacations();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}