package com.untold.untold.business.service;

import com.untold.untold.model.Admin;
import com.untold.untold.persistance.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepo;
    private Encrypt encrypt;

    @Autowired
    public AdminService(AdminRepository adminRepo, Encrypt encrypt) {
        this.adminRepo =adminRepo;
        this.encrypt = encrypt;
    }

    public boolean validateAdmin(String username, String password) {
        Admin admin = adminRepo.findAdminByUsername(username);
        if (admin != null) {
            String passwordE = encrypt.encryptPassword(password);
            if (passwordE.equals(admin.getPassword())) {
                System.out.println("connected as admin");
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


    //retreive
    public Admin findAdminByUsername(String username) {
        return adminRepo.findAdminByUsername(username);
    }

    //create
    public Admin createAdmin(Admin a) {
        if (adminRepo.findAdminByUsername(a.getUsername()) == null) {
            //encrypt password and add new admin
            String newPassword = encrypt.encryptPassword(a.getPassword());
            a.setPassword(newPassword);
            return adminRepo.save(a);
        }
        System.out.println("already existing username, try again");
        return null;
    }

}
