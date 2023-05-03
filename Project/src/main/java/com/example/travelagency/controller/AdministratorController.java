package com.example.travelagency.controller;

import com.example.travelagency.business.service.AdministratorService;
import com.example.travelagency.business.service.EmployeeService;
import com.example.travelagency.model.Employee;
import com.example.travelagency.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdministratorController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AdministratorService administratorService;

    @PostMapping("/{email}/{password}/employee/add")
    public ResponseEntity<?> addEmployee(@PathVariable String email, @PathVariable String password,
                                         @RequestBody Employee employee){
        boolean login = administratorService.validateAdministrator(email,password);
        if(!login){
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        else
            return employeeService.addEmployee(employee);
    }

    @PutMapping("/{email}/{password}/employee/modify")
    public ResponseEntity<?> modifyEmployee(@PathVariable String email, @PathVariable String password,
                                            @RequestBody Employee employee) {
        boolean login = administratorService.validateAdministrator(email, password);
        if (!login) {
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        } else {
            return employeeService.modifyEmployee(employee);
        }
    }

    @DeleteMapping("/{email}/{password}/employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String email, @PathVariable String password,
                                            @PathVariable Long id) {
        boolean login = administratorService.validateAdministrator(email, password);
        if (!login) {
            return new ResponseEntity<>(ResponseMessage.INVALID_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        } else {
            return employeeService.deleteEmployee(id);
        }
    }

    //xml file???
}
