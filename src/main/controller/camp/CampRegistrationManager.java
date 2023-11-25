package main.controller.camp;

import main.boundary.mainpage.CommitteeMainPage;
import main.model.camp.Camp;
import main.model.user.Committee;
import main.model.user.Student;
import main.repository.camp.CampRepository;
import main.repository.user.CommitteeRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.Colors;

import java.util.Objects;
import java.util.Scanner;

/**
 * This is the camp registration manager
 */
public class CampRegistrationManager {

    /**
     * This is to register a student into a camp
     * @param s student who wants to register to the camp
     * @param campName the name of the camp the student wants to register to
     * @throws ModelNotFoundException if the camp or student are not found in the repositories
     * @throws ModelAlreadyExistsException if the student already exists in the camp
     * @throws PageBackException to go back to the main page
     */
    public static void RegisterCampAttendee(Student s, String campName) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        Scanner scanner = new Scanner(System.in);
        Camp c = CampRepository.getInstance().getByID(campName);
        if(c.getCampAttendeeSlots()>0){
            c.setCampAttendeeSlots(c.getCampAttendeeSlots()-1);
            c.setTotalSlots(c.getTotalSlots()-1);
            c.addCampAttendee(s.getID());
            s.addRegisteredCampAsAttendee(campName);
            CampRepository.getInstance().update(c);
            if(s instanceof Committee com){
                CommitteeRepository.getInstance().update(com);
            }
            StudentRepository.getInstance().update(s);
            System.out.println("You have been successfully registered to this camp.");
        }
        else{
            System.out.println("Sorry...The Camp Attendee Slots are filled up.");
            System.out.println("Do you want to register as a Camp Committee Member instead ? [Y/N]");
            String confirmation = scanner.nextLine();
            if(Objects.equals(confirmation, "Y")){
                RegisterCampCommittee(s,campName);
            }
        }

    }

    /**
     * This is to register a student into a camp as a committee member
     * @param s the student who wants to register
     * @param campName the name of the camp
     * @throws ModelNotFoundException if the camp / student is not found in their respective repositories
     * @throws ModelAlreadyExistsException is the student already exists in the camp
     * @throws PageBackException to return to the main page
     */
    public static void RegisterCampCommittee(Student s, String campName) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        Scanner scanner = new Scanner(System.in);
        if(s.isCampCommitteeMember()){
            System.out.println("You are already a Camp Committee Member for "+ Colors.green+s.getRegisteredCampAsCommittee()+Colors.reset);
            System.out.println("Do you want to register as a Camp Attendee instead ? [Y/N]");
            String confirmation = scanner.nextLine();
            if(Objects.equals(confirmation, "Y")){
                RegisterCampAttendee(s,campName);
                throw new PageBackException();
            }
            else{
                throw new PageBackException();
            }
        }
        Camp c = CampRepository.getInstance().getByID(campName);
        if(c.getCampCommitteeSlots()>0){
            c.setCampCommitteeSlots(c.getCampCommitteeSlots()-1);
            c.setTotalSlots(c.getTotalSlots()-1);
            c.addCampCommittee(s.getID());
            s.setCampCommitteeMember(true);
            s.setRegisteredCampAsCommittee(campName);
            Committee com = new Committee(s);
            CommitteeRepository.getInstance().add(com);
            CampRepository.getInstance().update(c);
            StudentRepository.getInstance().update(s);
            System.out.println("You have been successfully registered to this camp.");
            System.out.println();
            System.out.println(BoundaryStrings.separator);
            System.out.println(Colors.red+"We will be redirecting you to the Camp committee page where you can access special privileges!"+ Colors.reset);
            Committee committee = CommitteeRepository.getInstance().getByID(s.getStudentID());
            CommitteeMainPage.commiteeMainPage(committee);
        }
        else{
            System.out.println("Sorry...The Camp Committee Slots are filled up.");
            System.out.println("Do you want to register as a Camp Attendee instead ? [Y/N]");
            String confirmation = scanner.nextLine();
            if(Objects.equals(confirmation, "Y")){
                RegisterCampAttendee(s,campName);
                throw new PageBackException();
            }
            throw new PageBackException();
        }
    }
}
