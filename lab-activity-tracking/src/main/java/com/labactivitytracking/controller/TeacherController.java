package com.labactivitytracking.controller;


import com.labactivitytracking.business.*;
import com.labactivitytracking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@CrossOrigin
public class TeacherController {

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


    @GetMapping("/{email}/{password}")
    private String logIn(@PathVariable String email, @PathVariable String password){
        int responce = teacherService.validateTeacher(email, password);

        if( responce == 0){
            return "Successfully logged in as teacher.";
        }else if( responce == -1){
            return "Failed authentication, passwords don't match, try again";
        }else if( responce == -2){
            return "Failed authentication, username not found, try again";
        }
        return "Method Failed";
    }

    //student CRUD
    @PostMapping("/{email}/{password}/student/create")
    private String createStudent(@PathVariable String email, @PathVariable String password, @RequestBody Student student){
        int login = teacherService.validateTeacher(email, password);
        if(login < 0){
            return "Invalid credentials";
        }

        return studentService.createStudent(student);
    }

    @GetMapping("/{email}/{password}/student/retrieve/{id}")
    private String retrieveStudent(@PathVariable String email, @PathVariable String password, @PathVariable long id){
        int login = teacherService.validateTeacher(email, password);
        if(login < 0){
            return "Invalid credentials";
        }
        return studentService.retrieveStudent(id);
    }

    @PutMapping("/{email}/{password}/student/update")
    private String updateStudent(@PathVariable String email, @PathVariable String password, @RequestBody Student student){
        int login = teacherService.validateTeacher(email, password);
        if(login < 0){
            return "Invalid credentials";
        }
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{email}/{password}/student/delete/{id}")
    private String deleteStudent(@PathVariable String email, @PathVariable String password, @PathVariable long id){
        int login = teacherService.validateTeacher(email, password);
        if(login < 0){
            return "Invalid credentials";
        }

        //delete all appearances
        Student student = studentService.getStudent(id);
        laboratoryService.deleteAllAttendances(student);
        List<Submission> submissionsToDelete = submissionService.getSubmissionsContainingStudent(student);
        assignmentService.deleteAssignmentSubmission(submissionsToDelete);
        submissionService.deleteStudentSubmission(student);

        return studentService.deleteStudent(id);
    }


    @PostMapping("/{email}/{password}/lab/create")
    private String createLab(@PathVariable String email, @PathVariable String password, @RequestBody Laboratory laboratory){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }

        return laboratoryService.createLaboratory(laboratory);
    }

    @PutMapping("/{email}/{password}/lab/update")
    private String updateLab(@PathVariable String email, @PathVariable String password, @RequestBody Laboratory laboratory) {
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        return laboratoryService.updateLaboratory(laboratory);

    }

    @DeleteMapping("/{email}/{password}/lab/delete/{id}")
    private String deleteLab(@PathVariable String email, @PathVariable String password,@PathVariable long id){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        //first delete the laboratory from the teacher's list
        Laboratory lab = laboratoryService.getLaboratoryById(id);
        if(lab == null){
            return "Laboratory id not found";
        }
        teacherService.deleteTeacherLaboratories(lab);
        return laboratoryService.deleteLaboratory(id);
    }

    @PostMapping("/{email}/{password}/attendance/create/{id}")
    private String createAttendance(@PathVariable String email, @PathVariable String password,
                                    @PathVariable long id,  @RequestBody List<Student> students){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        return laboratoryService.createAttendance(id, students);
    }

    @DeleteMapping("/{email}/{password}/attendance/delete/{id}")
    private String deleteAttendance(@PathVariable String email, @PathVariable String password,
                                    @PathVariable long id,  @RequestBody List<Student> studentsToDelete){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        return laboratoryService.deleteAttendance(id, studentsToDelete);
    }

    @GetMapping("{email}/{password}/attendance/retrieve/{id}")
    private String retrieveAttendance(@PathVariable String email, @PathVariable String password,
                                      @PathVariable long id){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        return laboratoryService.retrieveAttendance(id);
    }

    @PutMapping("{email}/{password}/attendance/update/{id}")
    private String updateAttendance(@PathVariable String email, @PathVariable String password,
                                @PathVariable long id, @RequestBody List<Student> newStudents){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }

        return laboratoryService.updateAttendance(id, newStudents);
    }

    //CRUD Assignment

    @PostMapping("{email}/{password}/assignment/create")
    private String createAssignment(@PathVariable String email, @PathVariable String password,
                                    @RequestBody Assignment assignment){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        return assignmentService.createAssignment(assignment);
    }

    @GetMapping("{email}/{password}/assignment/retrieve/{id}")
    private String retrieveLabAssignment(@PathVariable String email, @PathVariable String password,@PathVariable long id){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
       return laboratoryService.retrieveLabAssignment(id);
    }

    @GetMapping("{email}/{password}/assignment/retrieveAll")
    private String retrieveAllAssignments(@PathVariable String email, @PathVariable String password){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        return assignmentService.retrieveAllAssignments();
    }

    @PutMapping("{email}/{password}/assignment/update")
    private String updateAssignment(@PathVariable String email, @PathVariable String password,
                                    @RequestBody Assignment assignment){
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        return assignmentService.updateAssignment(assignment);
    }

    @DeleteMapping("{email}/{password}/assignment/delete/{id}")
    private String deleteAssignment(@PathVariable String email, @PathVariable String password,@PathVariable long id) {
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }

        Assignment assignment = assignmentService.findAssignmentById(id);
        laboratoryService.deleteLabAssignment(assignment);
        return assignmentService.deleteAssignment(id);
    }
    @PostMapping("{email}/{password}/grade/{id}")
    private String gradeSubmission(@PathVariable String email, @PathVariable String password,
                                   @PathVariable long id, @RequestBody double grade) {
        int login = teacherService.validateTeacher(email, password);
        if (login < 0) {
            return "Invalid credentials";
        }
        return submissionService.gradeSubmission(id, grade);
    }
 }
