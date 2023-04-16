package com.labactivitytracking.controller;


import com.labactivitytracking.business.*;
import com.labactivitytracking.model.Assignment;
import com.labactivitytracking.model.Laboratory;
import com.labactivitytracking.model.Student;
import com.labactivitytracking.model.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private LaboratoryService laboratoryService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private AssignmentService assignmentService;


    @PostMapping("/")
    private String signIn(@RequestParam String email, @RequestParam String token, @RequestBody String password ){
        return studentService.createPassword(email, token,password);
    }

    @GetMapping("/{email}/{password}")
    private String logIn(@PathVariable String email, @PathVariable String password){
        int responce = studentService.validateStudent(email, password);

        if( responce == 0){
            return "Successfully logged in as student.";
        }else if( responce == -1){
            return "Failed authentication, passwords don't match, try again";
        }else if( responce == -2){
            return "Failed authentication, username not found, try again";
        }else if( responce == -3) {
            return "Student has not signed-in yet with the given token.";
        }else{
            return "Method Failed";
        }
    }

    @GetMapping("/{email}/{password}/labs/{id}")
    private String laboratoryList(@PathVariable String email, @PathVariable String password, @PathVariable long id){
        int login = studentService.validateStudent(email, password);
        if(login < 0){
            return "Invalid credentials";
        }

        Student student = studentService.getStudent(id);
        List<Laboratory> labs = laboratoryService.getStudentLabs(student);
        StringBuilder sb = new StringBuilder();
        for(Laboratory l: labs){
            sb.append(l.getTitle()+"\n");
        }
        return String.valueOf(sb);
    }


    @GetMapping("/{email}/{password}/assignment/{id}")
    private String getAssignment(@PathVariable String email, @PathVariable String password, @PathVariable long id){
        int login = studentService.validateStudent(email, password);
        if(login < 0){
            return "Invalid credentials";
        }
        Student student = studentService.getStudentByEmail(email);
        Laboratory laboratory = laboratoryService.getLaboratoryById(id);
        //check if the student is in the students list of the laboratory
        List<Student> attendance = laboratory.getAttendance();
        if(attendance.contains(student)){
            return laboratory.getAssignment().toString();
        }
        else
            return "Student does not have assignment for the given laboratory id";
    }

    @PostMapping("/{email}/{password}/submission/{id}")
    private String makeSubmission(@PathVariable String email, @PathVariable String password,
                                  @PathVariable long id, @RequestBody Submission submission){
        int login = studentService.validateStudent(email, password);
        if(login < 0){
            return "Invalid credentials";
        }

        Student student = studentService.getStudentByEmail(email);
        return assignmentService.addSubmission(submission, student, id);

    }
}
