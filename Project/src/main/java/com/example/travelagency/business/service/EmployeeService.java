package com.example.travelagency.business.service;

import com.example.travelagency.business.Encrypt;
import com.example.travelagency.model.Employee;
import com.example.travelagency.model.ResponseMessage;
import com.example.travelagency.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Encrypt encrypt;

    public EmployeeService(EmployeeRepository employeeRepository, Encrypt encrypt) {
        this.employeeRepository = employeeRepository;
        this.encrypt = encrypt;
    }

    public boolean validateEmployee(String email, String password) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee != null) {
            String passwordE = encrypt.encryptPassword(password);
            if (passwordE.equals(employee.getPassword())) {
                return true;
            }
            else{
                //passwords don't match
               return false;
            }
        } else
        {
            //email not found
            System.out.println(password+"\n"+ employee.getPassword());
            return false;
        }

    }

    public ResponseEntity<String> addEmployee(Employee employee) {
        if (employeeRepository.findEmployeeByEmail(employee.getEmail()) != null) {
            return new ResponseEntity<>(ResponseMessage.CLIENT_ALREADY_EXISTS.getMessage(), HttpStatus.BAD_REQUEST);
        }

        employeeRepository.save(employee);

        return new ResponseEntity<>(ResponseMessage.CLIENT_CREATED_SUCCESSFULLY.getMessage(), HttpStatus.CREATED);
    }

    public ResponseEntity<String> modifyEmployee(Employee employee) {
        Employee oldEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        if (oldEmployee == null) {
            return new ResponseEntity<>(ResponseMessage.EMPLOYEE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        } else {
            oldEmployee.setFullName(employee.getFullName());
            oldEmployee.setEmail(employee.getEmail());
            oldEmployee.setPassword(employee.getPassword());
            employeeRepository.save(oldEmployee);
            return new ResponseEntity<>(ResponseMessage.EMPLOYEE_UPDATED_SUCCESSFULLY.getMessage(), HttpStatus.OK);
        }
    }

    public ResponseEntity<String> deleteEmployee(Long id) {
       Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return new ResponseEntity<>(ResponseMessage.EMPLOYEE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }
        employeeRepository.delete(employee);

        return new ResponseEntity<>(ResponseMessage.EMPLOYEE_DELETED_SUCCESSFULLY.getMessage(), HttpStatus.OK);
    }
}

