package main.boundary.mainpage;

import main.controller.camp.CampDeregistrationManager;
import main.controller.camp.CampRegistrationManager;
import main.model.enquiry.EnquiriesViewerForStudent;
import main.utils.ui.AttributeGetter;
import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.controller.enquiry.EnquiryManager;
import main.model.camp.Camp;
import main.model.camp.CampViewer;
import main.model.enquiry.Enquiry;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.model.user.UserType;
import main.repository.camp.CampRepository;
import main.repository.enquiry.EnquiryRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.ui.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the student main page --> home page for the student
 */
public class StudentMainPage {
    public static void studentMainPage(User user) throws PageBackException {
        if (user instanceof Student student) {
            System.out.println();
            System.out.println(BoundaryStrings.separator);
            System.out.println("Welcome " + user.getUserName() + " ! What would you like to do today ? ");
            System.out.println(BoundaryStrings.separatorThin);
            ChangePage.changePage();
            System.out.println("Choose What you want to do today");
            System.out.println("\t1. Change Password");
            System.out.println("\t2. View Profile");
            System.out.println("\t3. View Camp List");
            System.out.println("\t4. View All Registered Camps Info");
            System.out.println("\t5. View Upcoming Registered Camps Info");
            System.out.println("\t6. Register for a Camp");
            System.out.println("\t7. Withdraw from a Camp");
            System.out.println("\t8. Submit Enquiries");
            System.out.println("\t9. Edit Enquiries");
            System.out.println("\t10. Delete Enquiries");
            System.out.println("\t11. View Status of Enquiries");
            System.out.println("\t12. Logout");
            System.out.println(BoundaryStrings.separator);

            System.out.println();
            System.out.println("Please enter your choice: ");
            int choice = AttributeGetter.readInt();
            try {
                student = StudentRepository.getInstance().getByID(student.getID());
            } catch (ModelNotFoundException e) {
                e.printStackTrace();
            }

            try {
                switch (choice) {
                    case 1 -> ChangeAccountPassword.changePassword(UserType.STUDENT, student.getID());
                    case 2 -> student.displayStudentProfile();
                    case 3 -> displayAllVisibleCamps(student);
                    case 4 -> displayCampsRegistered(student);
                    case 5 -> displayUpcomingRegisteredCamps(student);
                    case 6-> registerForCamp(student);
                    case 7-> withdrawFromCamp(student);
                    case 8-> submitEnquiries(student);
                    case 9-> editEnquiries(student);
                    case 10-> deleteEnquiries(student);
                    case 11-> viewAllEnquiries(student);
                    case 12-> Logout.logout();
                }
            } catch (ModelNotFoundException | ModelAlreadyExistsException e) {
                throw new RuntimeException(e);
            } catch (PageBackException e) {
                StudentMainPage.studentMainPage(student);
            }

        }
    }

    /**
     * This is to view visible camps according to the filter that the user chooses
     * @param student
     * @throws PageBackException
     * @throws ModelNotFoundException
     */
    public static void displayAllVisibleCamps(Student student) throws PageBackException, ModelNotFoundException {
        CampViewer.displayAllVisibleCampsByFilter(student);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This displays all the camps that the student has registered for
     * @param student
     * @throws ModelNotFoundException
     * @throws PageBackException
     */
    public static void displayCampsRegistered(Student student) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        student.displayCampsEnrolled();
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This is to view all the upcoming registered camps that the student has registered for
     * @param student
     * @throws PageBackException
     * @throws ModelNotFoundException
     */
    public static void displayUpcomingRegisteredCamps(Student student) throws PageBackException, ModelNotFoundException {
        ChangePage.changePage();
        CampViewer.displayUpcomingRegisteredCamps(student);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This is for the user to register for camps
     * It displays all the registrable camps for the student
     * Takes in the student input and registers for the camp
     * @param student
     * @throws PageBackException
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     */
    public static void registerForCamp(Student student) throws PageBackException, ModelNotFoundException, ModelAlreadyExistsException {
        ChangePage.changePage();
        Scanner s = new Scanner(System.in);
        List<Integer> optionsAvailable = CampViewer.registrableCamps(student);
        if(optionsAvailable.size()==0){
            System.out.println("There aren't any camps you can register for right now...");
            System.out.println("Please come back later...");
            System.out.println();
            System.out.println("Press enter to go back to the Main Menu! ");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        System.out.println("Enter the CampID ( the number on the top row that you want to register for : ");
        int choice = AttributeGetter.optionGetter(optionsAvailable);
        int campid = choice;
        Camp c = CampRepository.getInstance().findByRules(camp->camp.getCampId()==campid).get(0);
        if(student.getWithdrawnCamps().contains(c.getCampName())){
            System.out.println("You have withdrawn from this camp once, hence you can't register for it again");
            System.out.println("Is There Any Other Camp you want to register for ? [Y/N]");
            String confirmation = s.nextLine();
            if(Objects.equals(confirmation, "Y")){
                registerForCamp(student);
            }
            else{
                throw new PageBackException();
            }
        }
        System.out.println(c.getDisplayableString());
        System.out.println("Do you want to register as : ");
        System.out.println("\t1. Camp Attendee");
        System.out.println("\t2. Camp Committee");
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        choice = AttributeGetter.optionGetter(integerList);
        if(choice==1){
            CampRegistrationManager.RegisterCampAttendee(student, c.getCampName());
            System.out.println("Do You Want to Register for any Other Camps ? [Y/N]");
            String confirmation = s.nextLine();
            if(Objects.equals(confirmation, "Y")){
                registerForCamp(student);
            }
            else{
                throw new PageBackException();
            }
        }
        else{
            CampRegistrationManager.RegisterCampCommittee(student,c.getCampName());
        }
    }

    /**
     * Withdraw the student from the camp selected
     * @param student
     * @throws PageBackException
     * @throws ModelNotFoundException
     */
    public static void withdrawFromCamp(Student student) throws PageBackException, ModelNotFoundException {
        ChangePage.changePage();
        List<Integer> optionsAvailable= student.displayCampsEnrolled();
        System.out.println("Select A Camp to withdraw from.... BeWare that you won't be able to register for this camp again...");
        int choice = AttributeGetter.optionGetter(optionsAvailable);
        Camp c = CampRepository.getInstance().findByRules(camp->camp.getCampId()==choice).get(0);
        CampDeregistrationManager.deregisterFromCamp(c,student);
        System.out.println("Press enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * submit enquries to any camp that the student can see
     * @param student
     * @throws PageBackException
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     */
    public static void submitEnquiries(Student student) throws PageBackException, ModelNotFoundException, ModelAlreadyExistsException {
        List<Integer> optionsAvailable= CampViewer.displayAllVisibleCamps(student);
        System.out.println("Select A Camp to Submit An Enquiry To : ");
        System.out.println("Enter the CampID ( the number on the top row ) of the Camp ");
        int choice = AttributeGetter.optionGetter(optionsAvailable);
        Camp c = CampRepository.getInstance().findByRules(camp->camp.getCampId()==choice).get(0);
        String campName = c.getCampName();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Enter Your Question");
        String question = scanner.nextLine();
        Enquiry e1 = new Enquiry(student.getUserName(),campName,question);
        EnquiryManager.askEnquiry(e1);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * edit any non processed enquiries submitted by the student
     * @param student
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     * @throws PageBackException
     */

    private static void editEnquiries(Student student) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        ChangePage.changePage();
        List<Enquiry> enquiryList = EnquiryRepository.getInstance().findByRules(e-> !e.getAnswered(), e-> Objects.equals(e.getAskedByStudentId(), student.getUserName()));
        List<Integer> optionsAvailable = new ArrayList<>();
        for(Enquiry e:enquiryList){
            optionsAvailable.add(e.getEnquiryID());
            e.displayEnquiryInfo();
        }
        if(optionsAvailable.size()==0){
            System.out.println("You dont have non processed enquiries to edit");
            throw new PageBackException();
        }
        System.out.println("Select An Enquiry To Edit ");
        System.out.println("Please Enter the Enquiry ID ( the number on the top row ) of the Enquiry");
        int choice = AttributeGetter.optionGetter(optionsAvailable);
        Enquiry e = EnquiryRepository.getInstance().getByID(Integer.toString(choice));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Edit Your Enquiry ( Type in your edited query) : ");
        String question = scanner.nextLine();
        boolean decision = AttributeGetter.confirmationGetter();
        if(decision){
            e.setQuestion(question);
            EnquiryManager.editEnquiry(e);
        }
        else{
            System.out.println("Edit request cancelled!");
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * delete any non processed enquiries submitted by the student
     * @param student
     * @throws ModelNotFoundException
     * @throws PageBackException
     */
    private static void deleteEnquiries(Student student) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        List<Enquiry> enquiryList = EnquiryRepository.getInstance().findByRules(e-> !e.getAnswered(), e-> Objects.equals(e.getAskedByStudentId(), student.getUserName()));
        List<Integer> optionsAvailable = new ArrayList<>();
        for(Enquiry e:enquiryList){
            optionsAvailable.add(e.getEnquiryID());
            e.displayEnquiryInfo();
        }
        if(optionsAvailable.size()==0){
            System.out.println("You don't have any non processed enquiries to delete");
            throw new PageBackException();
        }
        System.out.println("Select An Enquiry To Delete ");
        System.out.println("Please Enter the Enquiry ID ( the number on the top row ) of the Enquiry");
        int choice = AttributeGetter.optionGetter(optionsAvailable);
        Enquiry e = EnquiryRepository.getInstance().getByID(Integer.toString(choice));
        boolean decision = AttributeGetter.confirmationGetter();
        if(decision){
            EnquiryManager.deleteEnquiry(e);
        }
        else{
            System.out.println("Delete request cancelled!");
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * this is to view all the enquiries submitted by the student and to view their status and answers
     * @param student
     * @throws ModelNotFoundException
     * @throws PageBackException
     */
    public static void viewAllEnquiries(Student student) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        EnquiriesViewerForStudent enquiriesViewerForStudent = new EnquiriesViewerForStudent();
        enquiriesViewerForStudent.displayEnquiryInfo(student);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }



}




