package main.controller.camp;

import main.model.camp.Camp;
import main.model.user.Committee;
import main.model.user.Student;
import main.repository.camp.CampRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelNotFoundException;

import java.util.Comparator;
import java.util.Objects;

/**
 * This is the Controller class for Camp Deregistration
 */
public class CampDeregistrationManager {
    /**
     * This method controls the logic behind the camp deregistration by the student
     * @param c camp object --> the camp the student is deregistering from
     * @param s studnet object --> the studnet who is withdrawing
     * @throws ModelNotFoundException
     */
    public static void deregisterFromCamp(Camp c, Student s) throws ModelNotFoundException {
        if(Objects.equals(s.getRegisteredCampAsCommittee(), c.getCampName())){
            System.out.println("You can not withdraw from this Camp because you are a Camp Committee Member");
            return;
        }
        else{
            if(Objects.equals(c.getCampName(), s.getRegisteredCampAsCommittee())){
                c.setCampCommitteeSlots(c.getCampCommitteeSlots()+1);
                c.removeCampCommittee(s.getStudentID());
                s.setRegisteredCampAsCommittee("");
                s.setCampCommitteeMember(false);
            }
            else{
                c.setCampAttendeeSlots(c.getCampAttendeeSlots()+1);
                c.removeCampAttendee(s.getStudentID());
                s.removeRegisteredCampAsAttendee(c.getCampName());
            }
            c.setTotalSlots(c.getTotalSlots()+1);
            s.addWithdrawnCamps(c.getCampName());
            CampRepository.getInstance().update(c);
            StudentRepository.getInstance().update(s);
        }
        System.out.println("You have been deregistered successfully");
        System.out.println();
    }
}
