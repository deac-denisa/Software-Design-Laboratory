package com.labactivitytracking.repositories;

import com.labactivitytracking.model.Assignment;
import com.labactivitytracking.model.Laboratory;
import com.labactivitytracking.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaboratoryRepo extends JpaRepository<Laboratory, Long > {

    List<Laboratory> findAllByAttendanceContaining(Student student);
    List<Laboratory> findLaboratoryByAssignment(Assignment assignment);

}
