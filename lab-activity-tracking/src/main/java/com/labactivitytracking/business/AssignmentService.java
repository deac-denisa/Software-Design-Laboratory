package com.labactivitytracking.business;

import com.labactivitytracking.model.Assignment;
import com.labactivitytracking.model.Student;
import com.labactivitytracking.model.Submission;
import com.labactivitytracking.repositories.AssignmentRepo;
import com.labactivitytracking.repositories.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepo assignmentRepo;
    private final SubmissionRepo submissionRepo;

    @Autowired
    public AssignmentService(AssignmentRepo assignmentRepo, SubmissionRepo submissionRepo) {
        this.assignmentRepo = assignmentRepo;
        this.submissionRepo = submissionRepo;
    }

    public Assignment findAssignmentById(long id){
        return assignmentRepo.getReferenceById(id);
    }

    public void deleteAssignmentSubmission(List<Submission> submissions){
        for(Submission s: submissions) {
            List<Assignment> assignments = assignmentRepo.findAllBySubmissionsContaining(s);
            for(Assignment a: assignments){
                List<Submission> sub = a.getSubmissions();
                sub.remove(s);
                a.setSubmissions(sub);
                assignmentRepo.save(a);
            }
        }
    }

    public String createAssignment(Assignment a){
        assignmentRepo.save(a);
        return "Assignment created successfully.";
    }

    public String retrieveAllAssignments(){
        List<Assignment> assignments = assignmentRepo.findAll();
        StringBuilder sb = new StringBuilder();
        for(Assignment a: assignments){
            sb.append(a.getName());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String updateAssignment(Assignment newAssignment){
       Assignment oldAssignment = assignmentRepo.findById(newAssignment.getId()).orElse(null);
       if(oldAssignment == null){
           return "Assignment id not found";
       }
       oldAssignment.setName(newAssignment.getName());
       oldAssignment.setDescription(newAssignment.getDescription());
       oldAssignment.setDeadline(newAssignment.getDeadline());
       assignmentRepo.save(oldAssignment);
       return "Assignment updated successfully";
    }

    public String deleteAssignment( long id){
        Assignment assignment = assignmentRepo.findById(id).orElse(null);
        if(assignment == null){
            return "Assignment id not found";
        }
        assignmentRepo.delete(assignment);
        return "Assignment with id "+id+" was deleted successfully.";
    }

    public String addSubmission(Submission submission, Student student, long aId){
        Assignment assignment = assignmentRepo.getReferenceById(aId);
        List<Submission> submissions = assignment.getSubmissions();

        LocalDate date = LocalDate.now();
        String link = submission.getSubmissionLink();
        String comment = submission.getComment();

        Submission newSubmission = new Submission() ;
        newSubmission.setStudent(student);
        newSubmission.setSubmissionLink(link);
        newSubmission.setSubmissionDate(date);
        newSubmission.setComment(comment);
        newSubmission.setGrade(0);  //default grade
        submissionRepo.save(newSubmission);
        submissions.add(newSubmission);
        assignment.setSubmissions(submissions);
        assignmentRepo.save(assignment);
        return "Submission added with success.";
    }

}
