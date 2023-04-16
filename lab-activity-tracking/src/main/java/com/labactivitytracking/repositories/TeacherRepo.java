package com.labactivitytracking.repositories;

import com.labactivitytracking.model.Laboratory;
import com.labactivitytracking.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepo extends JpaRepository<Teacher, Long > {

    Teacher findTeacherByEmail(String email);
    List<Teacher> findAllByLabsContaining(Laboratory laboratory);
}
