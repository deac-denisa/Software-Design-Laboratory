package com.labactivitytracking.business;

import com.labactivitytracking.model.Student;
import com.labactivitytracking.repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    private final Encrypt encrypt;

    @Autowired
    public StudentService(StudentRepo studentRepo, Encrypt encrypt) {
        this.studentRepo = studentRepo;
        this.encrypt = encrypt;
    }

    private String createToken(){
        // Create a secure random number generator
        SecureRandom random = new SecureRandom();

        // Generate 128 random bytes
        byte[] bytes = new byte[128];
        random.nextBytes(bytes);

        // Encode the bytes using Base64 encoding
        String token = Base64.getEncoder().encodeToString(bytes);

        // Remove any non-alphanumeric characters from the token
        token = token.replaceAll("[^a-zA-Z0-9]", "");

        // Truncate the token to 128 characters
        token = token.substring(0, 128);
        return token;
    }

    public String createPassword(String email, String token, String password){
        Student student = studentRepo.findStudentByEmail(email);
        if(!student.isUsedToken() && student.getToken().equals(token)){
            String encrypted = encrypt.encryptPassword(password);
            student.setPassword(encrypted);
            student.setUsedToken(true);
            studentRepo.save(student);
            return "Student signed-in. Token is correct and password is set.";
        }
        return "Token already used or token does not match.";
    }

    public int validateStudent(String email, String password){
        Student student = studentRepo.findStudentByEmail(email);
        if (student != null) {
            String passwordE = encrypt.encryptPassword(password);
            if(student.getPassword() == null){
                return -3;
            }
            else if (passwordE.equals(student.getPassword())) {
                return 0;
            } else {
                System.out.println(passwordE+" "+ student.getPassword());
                return -1;
             }
        } else {
            return -2;
        }
    }

    public Student getStudent( long id){
       return studentRepo.getReferenceById(id);
    }

    public Student getStudentByEmail(String email){return studentRepo.findStudentByEmail(email);}

    public String createStudent(Student student){
        //check if the student doesn't already exist in the database
        if(studentRepo.findStudentByEmail(student.getEmail()) == null){
            String token = createToken();
            student.setToken(token);
            student.setUsedToken(false);
            studentRepo.save(student);
            return "Student and the corespondent token were created successfully \n"+ student;
        }
        return "Student with this email already exists.";
    }

    public String retrieveStudent(long id){
        Student student = studentRepo.getReferenceById(id);
        return "Student with given id was found: \n"+ student;
    }

    public String updateStudent(Student student){

        Student oldStudent = studentRepo.findById(student.getId()).orElse(null);
        if(oldStudent != null){
            //do the update
            oldStudent.setName(student.getName());
            oldStudent.setEmail(student.getEmail());
            oldStudent.setToken(student.getToken());
            oldStudent.setUsedToken(student.isUsedToken());
            oldStudent.setGroupNumber(student.getGroupNumber());
            oldStudent.setHobby(student.getHobby());
            oldStudent.setPassword(student.getPassword());
            studentRepo.save(oldStudent);
            return "Student updated: \n"+oldStudent;
        }
        return "Student id not found";
    }

    public String deleteStudent(long id){
        Student student = studentRepo.findById(id).orElse(null);
        if(student != null){
            studentRepo.delete(student);
            return "Student deleted successfully.";
        }
        return "Student id not found";
    }



}
