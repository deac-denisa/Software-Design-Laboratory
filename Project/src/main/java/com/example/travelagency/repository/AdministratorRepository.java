package com.example.travelagency.repository;

import com.example.travelagency.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Administrator findAdministratorByEmail(String email);
}
