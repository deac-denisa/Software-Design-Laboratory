package com.untold.untold.business.service;

import com.untold.untold.model.Cashier;
import com.untold.untold.persistance.CashierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CashierService {

    private final CashierRepository cashierRepo;
    private final Encrypt encrypt;


    @Autowired
    public CashierService(CashierRepository cashierRepo, Encrypt encrypt) {
        this.cashierRepo = cashierRepo;
        this.encrypt = encrypt;
    }
    //credential validation by username
    public boolean validateCashier(String username, String password) {
        Cashier cashier = cashierRepo.findCashierByUsername(username);
        if (cashier != null) {
            String passwordE = encrypt.encryptPassword(password);
            if (passwordE.equals(cashier.getPassword())) {
                System.out.println("connected as cashier");
                return true;
            }
            else{
                System.out.println("fail authentication, passwords don't match, try again");
            }
        } else
        {
            System.out.println("fail authentication, username not found, try again");
            return false;
        }

        return false;
    }

    //cashier CRUD
    //create
    public Cashier createCashier(Cashier c) {

        if (cashierRepo.findCashierByUsername(c.getUsername()) == null) {
            //encrypt password and add new cashier
            String newPassword = encrypt.encryptPassword(c.getPassword());
            c.setPassword(newPassword);
            return cashierRepo.save(c);
        }
        System.out.println("already existing username, try again");
        return null;
    }

    //retreive - returns true if the cashier data is correct and exists
    public Cashier getCashier(Cashier c) {
        Cashier cashier = (Cashier) cashierRepo.findById(c.getId()).orElse(null);
        if (cashier != null) {
            String password = encrypt.encryptPassword(c.getPassword());
            if (password.equals(cashier.getPassword())) {
                return cashier;
            }
        } else
            return null;
        return null;
    }


    //update
    public Cashier updateCashier(Cashier c) {
        Cashier existingCashier = (Cashier) cashierRepo.findById(c.getId()).orElse(null);
        if (existingCashier != null) {
            existingCashier.setUsername(c.getUsername());
            existingCashier.setPassword(c.getPassword());
            return cashierRepo.save(existingCashier);
        }
        return null;
    }

    //delete
    public void deleteCashier(Long id) {
        cashierRepo.deleteById(id);
    }



}
