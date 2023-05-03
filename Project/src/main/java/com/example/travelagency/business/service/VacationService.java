package com.example.travelagency.business.service;

import com.example.travelagency.model.Client;
import com.example.travelagency.model.ResponseMessage;
import com.example.travelagency.model.Vacation;
import com.example.travelagency.repository.VacationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationService {

    private final VacationRepository vacationRepository;

    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public boolean findVacation(Vacation vacation){
        return vacationRepository.findVacationById(vacation.getId()) != null;
    }

    public ResponseEntity<String> getAllVacations(){
        List<Vacation> vacationList = vacationRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for(Vacation v: vacationList){
            sb.append("name: ").append(v.getName()).append(" |  location").append(v.getLocation()).append(" |  date: ").append(v.getStartDate()).append(" - ").append(v.getEndDate()).append("\n");
        }
        return new ResponseEntity<>(String.valueOf(sb) ,HttpStatus.ACCEPTED);

    }

}
