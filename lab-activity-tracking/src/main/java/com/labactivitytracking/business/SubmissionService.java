package com.labactivitytracking.business;

import com.labactivitytracking.model.Student;
import com.labactivitytracking.model.Submission;
import com.labactivitytracking.repositories.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepo submissionRepo;

    @Autowired
    public SubmissionService(SubmissionRepo submissionRepo) {
        this.submissionRepo = submissionRepo;
    }

    public void deleteStudentSubmission(Student student){
        List<Submission> submissions = submissionRepo.findAllByStudent(student);
        submissionRepo.deleteAll(submissions);
    }

    public List<Submission> getSubmissionsContainingStudent(Student student){
        return submissionRepo.findAllByStudent(student);
    }

    public String gradeSubmission(long id, double grade){
        Submission submission = submissionRepo.findById(id).orElse(null);
        if( submission == null){
            return "Submission id not found";
        }
        if(submission.getSubmissionLink() != null){
            submission.setGrade(grade);
            submissionRepo.save(submission);
            return "Submission number "+ id +" was graded successfully with "+ grade +" .";
        }
        else{
            return "Submission does not have a link.";
        }
    }
}
