package com.labactivitytracking.repositories;

import com.labactivitytracking.model.Assignment;
import com.labactivitytracking.model.Student;
import com.labactivitytracking.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepo extends JpaRepository<Assignment, Long > {
    List<Assignment> findAllBySubmissionsContaining(Submission s);
}
