package com.labactivitytracking.repositories;

import com.labactivitytracking.model.Student;
import com.labactivitytracking.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepo extends JpaRepository<Submission, Long > {
    List<Submission> findAllByStudent(Student student);
}
