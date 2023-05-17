package com.example.travelagency.business.service;

import com.example.travelagency.business.Encrypt;
import com.example.travelagency.model.Administrator;
import com.example.travelagency.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    private final AdministratorRepository administratorRepository;
    private final Encrypt encrypt;


    public AdministratorService(AdministratorRepository administratorRepository, Encrypt encrypt) {
        this.administratorRepository = administratorRepository;
        this.encrypt = encrypt;
    }

    public boolean validateAdministrator(String email, String password) {
        Administrator administrator = administratorRepository.findAdministratorByEmail(email);
        if (administrator != null) {
            String passwordE = encrypt.encryptPassword(password);
            if (passwordE.equals(administrator.getPassword())) {
                return true;
            }
            else{
                //passwords don't match
                return false;
            }
        } else
        {
            //email not found
            System.out.println(password+"\n"+ administrator.getPassword());
            return false;
        }
    }



}
