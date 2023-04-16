package com.labactivitytracking.business;

import com.labactivitytracking.model.Assignment;
import com.labactivitytracking.model.Laboratory;
import com.labactivitytracking.model.Student;
import com.labactivitytracking.repositories.LaboratoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratoryService {

    private final LaboratoryRepo laboratoryRepo;

    @Autowired
    public LaboratoryService(LaboratoryRepo laboratoryRepo) {
        this.laboratoryRepo = laboratoryRepo;
    }

    public void deleteAllAttendances(Student stud){
       List<Laboratory> labs = laboratoryRepo.findAllByAttendanceContaining(stud);
       for(Laboratory lab: labs ) {
           List<Student> attendance = lab.getAttendance();
           System.out.println(attendance.toString());

           long idToRemove = stud.getId();
           attendance.removeIf(student -> student.getId() == idToRemove);
           //attendance.remove(student);
           System.out.println(attendance.toString());
           lab.setAttendance(attendance);
           laboratoryRepo.save(lab);
       }
    }

    public List<Laboratory> getStudentLabs(Student student){
       return laboratoryRepo.findAllByAttendanceContaining(student);
    }

    public String createLaboratory(Laboratory laboratory){
        return "Created sucessfully: " + laboratoryRepo.save(laboratory);

    }

    public String updateLaboratory(Laboratory laboratory) {
        Laboratory oldLaboratory = laboratoryRepo.findById(laboratory.getId()).orElse(null);
        if (oldLaboratory != null) {
            // Do the update
            oldLaboratory.setWeekNumber(laboratory.getWeekNumber());
            oldLaboratory.setTitle(laboratory.getTitle());
            oldLaboratory.setDate(laboratory.getDate());
            oldLaboratory.setCurricula(laboratory.getCurricula());
            oldLaboratory.setDescription(laboratory.getDescription());
            oldLaboratory.setAttendance(laboratory.getAttendance());
            oldLaboratory.setAssignment(laboratory.getAssignment());
            laboratoryRepo.save(oldLaboratory);
            return "Laboratory updated: \n" + oldLaboratory;
        }
        return "Laboratory id not found";
    }

    public String deleteLaboratory(long id){
        Laboratory lab = laboratoryRepo.findById(id).orElse(null);
        if (lab != null) {
            laboratoryRepo.delete(lab);
            return "Laboratory deleted.";
        }
        return "Laboratory id not found";
    }

    public Laboratory getLaboratoryById(long id){
        Laboratory lab = laboratoryRepo.findById(id).orElse(null);
        return lab;
    }

    //CRUD on attendance for each lab
    public String createAttendance(long labId, List<Student> attendance){
        Laboratory lab = laboratoryRepo.findById(labId).orElse(null);
        if(lab == null){
            return "Laboratory id not found. Please give another id or create the laboratory";
        }
        lab.setAttendance(attendance);
        laboratoryRepo.save(lab);

        return "Attendance list created for laboratory number "+ labId;
    }

    public String retrieveAttendance(long id){
        Laboratory lab = laboratoryRepo.findById(id).orElse(null);
        if(lab == null){
            return "Laboratory id not found. Please give another id or create the laboratory";
        }

        return "Attendance list for laboratory "+ id +"\n" + lab.getAttendance().toString();
    }

    public String updateAttendance(long labId,  List<Student> newStudents){
        Laboratory lab = laboratoryRepo.findById(labId).orElse(null);
        if(lab == null){
            return "Laboratory id not found. Please give another id or create the laboratory";
        }
        List<Student> attendance =  lab.getAttendance();

        for(Student s: newStudents){
            if(attendance.contains(s)) {
                System.out.println("contains"+s);
            }
            else
            {
                attendance.add(s);
            }
        }
        lab.setAttendance(attendance);
        laboratoryRepo.save(lab);
        return "Attendance list was modified. The new students were added";
    }

    public String deleteAttendance(long labId,  List<Student> studentsToDelete){
        Laboratory lab = laboratoryRepo.findById(labId).orElse(null);
        if(lab == null){
            return "Laboratory id not found. Please give another id or create the laboratory";
        }
        List<Student> attendance = lab.getAttendance();
        for(Student s: studentsToDelete){
            attendance.remove(s);
        }
        lab.setAttendance(attendance);
        laboratoryRepo.save(lab);
        return "The given students were deleted from the attendance list.";
    }

    //assignments

    public String retrieveLabAssignment(long id){
        Laboratory lab = laboratoryRepo.findById(id).orElse(null);
        if(lab == null){
            return "Laboratory id not found. Please give another id or create the laboratory";
        }
        return "Assignment for lab "+id+"\n"+lab.getAssignment().toString();

    }

    public void deleteLabAssignment(Assignment assignment){
        List<Laboratory> labs = laboratoryRepo.findLaboratoryByAssignment(assignment);
        for(Laboratory lab: labs){
            if(lab.getAssignment().getId() == assignment.getId())
                lab.setAssignment(null);
                laboratoryRepo.save(lab);
        }
    }

}
