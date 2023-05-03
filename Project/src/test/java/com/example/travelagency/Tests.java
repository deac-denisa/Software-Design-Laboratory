package com.example.travelagency;

import com.example.travelagency.business.service.ClientService;
import com.example.travelagency.business.service.ReservationService;
import com.example.travelagency.business.service.StayService;
import com.example.travelagency.business.service.VacationService;
import com.example.travelagency.model.Client;
import com.example.travelagency.model.Reservation;
import com.example.travelagency.model.Vacation;
import com.example.travelagency.model.VacationType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class Tests {

    @MockBean
    private StayService stayServiceMock;

    @Mock
    private VacationService vacationService;

    @Mock
    private ClientService clientService;

    @MockBean
    private ReservationService reservationServiceMock;

    @Test
    public void deleteVacationTest() throws Exception {
        // create a mock vacation object
        Vacation vacation = new Vacation();
        vacation.setId(1L);
        vacation.setType(VacationType.STAY);
        vacation.setName("Test vacation");
        vacation.setDescription("Test description");
        vacation.setLocation("Test location");
        vacation.setPrice(100.0);
        vacation.setStartDate(LocalDate.now().plusDays(1));
        vacation.setEndDate(LocalDate.now().plusDays(7));

        // create a mock client
        Client client = new Client();
        client.setFullName("John Doe");
        client.setEmail("john.doe@example.com");
        client.setPassword("abc");

        // create a mock reservation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setVacation(vacation);

        // mock the stay service
        Mockito.when(stayServiceMock.deleteVacation(Mockito.anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = stayServiceMock.deleteVacation(vacation.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


    @Test
    public void updateReservationTest() throws Exception {

        // create a mock client object
        Client client = new Client();
        client.setId(1L);
        client.setEmail("test@test.com");
        client.setPassword("password");
        client.setFullName("Test Client");

        // create a mock vacation object
        Vacation vacation = new Vacation();
        vacation.setId(1L);
        vacation.setType(VacationType.STAY);
        vacation.setName("Test vacation");
        vacation.setDescription("Test description");
        vacation.setLocation("Test location");
        vacation.setPrice(100.0);
        vacation.setStartDate(LocalDate.now().plusDays(1));
        vacation.setEndDate(LocalDate.now().plusDays(7));

        // create a mock reservation object
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setVacation(vacation);
        reservation.setClient(client);


        // create a second mock vacation object
        Vacation newVacation = new Vacation();
        newVacation.setId(2L);
        newVacation.setType(VacationType.TOUR);
        newVacation.setName("Test tour");
        newVacation.setDescription("Test tour description");
        newVacation.setLocation("Test tour location");
        newVacation.setPrice(200.0);
        newVacation.setStartDate(LocalDate.now().plusDays(1));
        newVacation.setEndDate(LocalDate.now().plusDays(7));

        // create a mock reservation object
        Reservation newReservation = new Reservation();
        newReservation.setId(1L);
        newReservation.setVacation(newVacation);
        newReservation.setClient(client);

        // mock the client and vacation services
        Mockito.when(clientService.findClient(Mockito.any(Client.class))).thenReturn(true);
        Mockito.when(vacationService.findVacation(Mockito.any(Vacation.class))).thenReturn(true);

        //create the first reservation
        Mockito.when(reservationServiceMock.addReservation(Mockito.eq(reservation)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        Mockito.when(reservationServiceMock.modifyReservation(Mockito.eq(newReservation)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> response = reservationServiceMock.modifyReservation(newReservation);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}
