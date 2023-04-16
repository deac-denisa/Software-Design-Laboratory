package com.labactivitytracking.business;

import com.labactivitytracking.model.Laboratory;
import com.labactivitytracking.model.Teacher;
import com.labactivitytracking.repositories.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepo teacherRepo;
    private final Encrypt encrypt;

    @Autowired
    public TeacherService(TeacherRepo teacherRepo, Encrypt encrypt) {
        this.teacherRepo = teacherRepo;
        this.encrypt = encrypt;
    }

    public int validateTeacher(String email, String password){
      Teacher teacher = teacherRepo.findTeacherByEmail(email);
      if (teacher != null) {
            String passwordE = encrypt.encryptPassword(password);
            if (passwordE.equals(teacher.getPassword())) {
                return 0;
            }
            else{
                return -1;
            }
        } else
        {
            return -2;
        }
    }

    public void deleteTeacherLaboratories(Laboratory lab){
        List<Teacher> teachers = teacherRepo.findAllByLabsContaining(lab);
        for(Teacher t: teachers) {
            List<Laboratory> labs = t.getLabs();
            labs.remove(lab);
            t.setLabs(labs);
            teacherRepo.save(t);
        }
    }
}
