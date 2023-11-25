package main.utils.ui;

import main.model.camp.Camp;
import main.model.user.Student;
import main.repository.camp.CampRepository;
import main.utils.exception.ModelNotFoundException;

import java.time.LocalDate;
import java.util.Objects;

public class DateClash {
    public static boolean dateClash(Camp c, Student s) throws ModelNotFoundException {
        if(s.isCampCommitteeMember()){
            if(isOverlap(c.getStartDate(), c.getEndDate(), CampRepository.getInstance().getByID(s.getRegisteredCampAsCommittee()).getStartDate(), CampRepository.getInstance().getByID(s.getRegisteredCampAsCommittee()).getEndDate())){
                return true;
            }
        }
        else{
            if(s.getRegisteredCampsAsAttendee()!=null){
            for(String campName:s.getRegisteredCampsAsAttendee()){
                if (Objects.equals(campName, "")) {
                    continue;
                }
                Camp campReg = CampRepository.getInstance().getByID(campName);
                if(isOverlap(c.getStartDate(),c.getEndDate(),campReg.getStartDate(),campReg.getEndDate())){
                    return true;
                }
            }
            }
        }
        return false;
    }
    private static boolean isOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !end1.isBefore(start2) && !start1.isAfter(end2);
    }
}
