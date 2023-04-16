package com.labactivitytracking.repositories;

import com.labactivitytracking.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long > {
    Student findStudentByEmail(String email);
}
