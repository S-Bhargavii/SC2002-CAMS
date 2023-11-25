package main.boundary.mainpage;

import main.controller.enquiry.ReplyEnquiry;
import main.controller.enquiry.ReplyEnquiryForStaff;
import main.controller.report.ReportGenerator;
import main.controller.report.TXTReportGenerator;
import main.controller.suggestion.SuggestionManager;
import main.model.camp.CampViewer;
import main.model.enquiry.EnquiriesViewerForStaff;
import main.model.suggestion.Suggestion;
import main.model.suggestion.SuggestionsViewerByUser;
import main.model.suggestion.SuggestionsViewerForStaff;
import main.repository.suggestion.SuggestionRepository;
import main.utils.ui.AttributeGetter;
import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.controller.camp.CampManager;
import main.model.camp.Camp;
import main.model.user.Staff;
import main.model.user.User;
import main.model.user.UserType;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * after the staff logs in, they are directed to this page
 */
public class StaffMainPage {
    public static void staffMainPage(User user) {
        if (user instanceof Staff staff) {
            System.out.println();
            System.out.println(BoundaryStrings.separator);
            System.out.println("Welcome " + user.getUserName() + " ! What would you like to do today ? ");
            System.out.println(BoundaryStrings.separatorThin);
            System.out.println("\t1. Change Password");
            System.out.println("\t2. Create Camp");
            System.out.println("\t3. Edit Camp Info");
            System.out.println("\t4. Toggle Visibility of My Camp");
            System.out.println("\t5. Delete Camp");
            System.out.println("\t6. View All Camps");
            System.out.println("\t7. View My Camps");
            System.out.println("\t8. View Enquiries");
            System.out.println("\t9. Reply to Enquiries");
            System.out.println("\t10. View Suggestions");
            System.out.println("\t11. Approve or Reject Suggestions");
            System.out.println("\t12. Generate Student/ Enquiry List");
            System.out.println("\t13. Generate Performance Report");
            System.out.println("\t14. Logout");
            System.out.println(BoundaryStrings.separator);
            System.out.println();
            System.out.println("Please enter your choice: ");
            int choice = AttributeGetter.readInt();
            try {
                /**
                 * Gets the staff by the ID ( the part before the @ in the email )
                 */
                staff = StaffRepository.getInstance().getByID(staff.getID());
            } catch (ModelNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                switch (choice) {
                    case 1 -> ChangeAccountPassword.changePassword(UserType.STAFF, staff.getID());
                    case 2 -> staffCreateCamp(staff);
                    case 3 -> staffEditCamp(staff);
                    case 4 -> staffToggleVisibility(staff);
                    case 5 -> staffDeleteCamp(staff);
                    case 6 -> {
                        CampViewer.displayAllCampInfo();
                        System.out.println("Enter enter to go back to the Main Menu! ");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                    }
                    case 7 -> {
                        staff.displayCampsEnrolled();
                        System.out.println("Enter enter to go back to the Main Menu! ");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                    }
                    case 8->viewEnquiries(staff);
                    case 9->replyToEnquiries(staff);
                    case 10-> viewSuggestions(staff);
                    case 11-> approveSuggestions(staff);
                    case 12-> generateStudentList(staff);
                    case 13-> generatePerformanceList(staff);
                    case 14-> Logout.logout();
                }
            } catch (PageBackException | ModelAlreadyExistsException e) {
                staffMainPage(staff);
            } catch (ModelNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method allows the user to generate the performance list
     * It simply takes input from the staff and then calls methods from the ReportGenerator Manager
     * @param staff the staff object who wants to generate the performance list
     * @throws ModelNotFoundException
     * @throws PageBackException
     * @throws ModelAlreadyExistsException
     */
    private static void generatePerformanceList(Staff staff) throws ModelNotFoundException, PageBackException, ModelAlreadyExistsException {
        List<Integer> options;
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        options = staff.displayCampsEnrolled();
        if (options.size() == 0) {
            System.out.println("You have not created any camps yet...");
            System.out.println("Would you like to create one ? [Y/N]");
            String confirmation = scanner.nextLine();
            if (Objects.equals(confirmation, "Y")) {
                staffCreateCamp(staff);
            }
            else{
                throw new PageBackException();
            }
        } else {
            System.out.println("Enter the CampID ( the number on the top row ) of the Camp you want to generate the student list for");
            choice = AttributeGetter.optionGetter(options);
        }
        int campNumber = choice;
        Camp camp = CampRepository.getInstance().findByRules(c -> c.getCampId() == campNumber).get(0);
        ReportGenerator txtReportGenerator = new TXTReportGenerator();
        txtReportGenerator.generatePerformanceReport(camp);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method allows the user to generate the student list
     * It simply takes input from the staff regarding the type of list they want to generate
     * and then calls methods from the ReportGenerator Manager
     * @param staff
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     * @throws PageBackException
     */
    private static void generateStudentList(Staff staff) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        List<Integer> options;
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        options = staff.displayCampsEnrolled();
        if (options.size() == 0) {
            System.out.println("You have not created any camps yet...");
            System.out.println("Would you like to create one ? [Y/N]");
            String confirmation = scanner.nextLine();
            if (Objects.equals(confirmation, "Y")) {
                staffCreateCamp(staff);
            }
            else{
                throw new PageBackException();
            }
        } else {
            System.out.println("Enter the CampID ( the number on the top row ) of the Camp you want to generate the student list for");
            choice = AttributeGetter.optionGetter(options);
        }
        int campNumber = choice;
        Camp camp = CampRepository.getInstance().findByRules(c -> c.getCampId() == campNumber).get(0);
        String listType = AttributeGetter.listTypeGetterForStaff();
        ReportGenerator txtReportGenerator = new TXTReportGenerator();
        switch (listType){
            case "attendee"->  txtReportGenerator.generateAttendeeList(camp);
            case "committee"-> txtReportGenerator.generateCommitteeList(camp);
            case "all"-> txtReportGenerator.generateStudentsList(camp);
            case "enquiry"-> txtReportGenerator.generateEnquiryReport(camp);
        }
        System.out.println("Would you like to generate another report? [Y/N]");
        String desc = AttributeGetter.ynGetter();
        if(Objects.equals(desc, "Y")){
            generateStudentList(staff);
        }
        else{
            System.out.println("Enter enter to go back to the Main Menu! ");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }

    }

    /**
     * This method allows the staff to view the suggestions of all the camps created by him/her.
     * @param staff
     * @throws ModelNotFoundException
     * @throws PageBackException
     */
    private static void viewSuggestions(Staff staff) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        SuggestionsViewerByUser suggestionsViewerForStaff = new SuggestionsViewerForStaff();
        suggestionsViewerForStaff.getAllSuggestionsByUser(staff);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method allows the staff to approve and reject suggestions posted to the camp created by him/ her.
     * @param staff
     * @throws ModelNotFoundException
     * @throws PageBackException
     */
    private static void approveSuggestions(Staff staff) throws ModelNotFoundException, PageBackException {
        SuggestionsViewerByUser suggestionsViewerForStaff = new SuggestionsViewerForStaff();
        List<Integer> optionsList = suggestionsViewerForStaff.getNonProcessedByUser(staff);
        if(optionsList.size()==0){
            System.out.println("There are no pending suggestions for processing!");
            System.out.println("Enter enter to go back to the Main Menu! ");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        System.out.println("Do you want to approve any suggestions? [Y/N]");
        String choice = AttributeGetter.ynGetter();
        if(Objects.equals(choice, "Y")){
            System.out.println("Enter the ID ( the number on the top row ) of the suggestion you want to approve");
            int option = AttributeGetter.optionGetter(optionsList);
            Suggestion suggestion = SuggestionRepository.getInstance().getByID(Integer.toString(option));
            SuggestionManager.approveSuggestion(suggestion);
            System.out.println("Do you want to approve other suggestions?");
            choice = AttributeGetter.ynGetter();
            if(Objects.equals(choice, "Y")){
                approveSuggestions(staff);
            }
            optionsList.removeIf(element -> element.equals(option));
        }
        System.out.println("Do you want to automatically reject all the other suggestions? [Y/N]");
        choice = AttributeGetter.ynGetter();
        if(Objects.equals(choice, "Y")){
            for(int i : optionsList){
                Suggestion suggestion = SuggestionRepository.getInstance().getByID(Integer.toString(i));
                SuggestionManager.rejectSuggestion(suggestion);
            }
        }
        else{
            rejectSuggestions(staff);
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();

    }

    /**
     * This method is for the staff to reject suggestions and is called from within the approvesuggestions method
     * @param staff
     * @throws ModelNotFoundException
     */
    private static void rejectSuggestions(Staff staff) throws ModelNotFoundException{
        System.out.println("Do you want to reject any suggestions [Y/N]");
        String choice = AttributeGetter.ynGetter();
        if(Objects.equals(choice, "N")){
            return;
        }
        System.out.println("Enter the ID( the number on the top row ) of the suggestion you want to reject");
        SuggestionsViewerByUser suggestionsViewerForStaff = new SuggestionsViewerForStaff();
        List<Integer> optionsList = suggestionsViewerForStaff.getNonProcessedByUser(staff);
        int option = AttributeGetter.optionGetter(optionsList);
        Suggestion suggestion = SuggestionRepository.getInstance().getByID(Integer.toString(option));
        SuggestionManager.rejectSuggestion(suggestion);
        System.out.println("Do you want to reject other suggestions?");
        choice = AttributeGetter.ynGetter();
        if(Objects.equals(choice, "Y")){
            rejectSuggestions(staff);
        }
    }

    /**
     * This method is to take inout from the staff to create camps
     * @param staff
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     * @throws PageBackException
     */
    public static void staffCreateCamp(Staff staff) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        Scanner scanner = new Scanner(System.in);
        /**
         *  getting the camp name
         */
        System.out.println("Creating a camp....");
        System.out.println("Please enter the Camp Name: ");
        String campName = scanner.nextLine();
        try {
            while (CampRepository.getInstance().getByID(campName) != null) {
                System.out.println("Sorry, there is another camp with that name....");
                System.out.println("Please enter another Name: ");
                campName = scanner.nextLine();
            }
        } catch (ModelNotFoundException e) {
            /**
             * dont do anything... proceed with the function
             */
        }

        /**
         *  getting the start date
         */
        System.out.println("Please enter the Start Date: ");
        LocalDate startDate = AttributeGetter.getFutureDate();

        /**
         *  getting the end date
         */
        System.out.println("Please enter the End Date: ");
        LocalDate endDate = AttributeGetter.getFutureDate();
        while (endDate.isBefore(startDate)) {
            System.out.println("End Date must be AFTER the Start Date...");
            System.out.println("Please enter another date: ");
            endDate = AttributeGetter.getFutureDate();
        }

        /**
         *  getting the last date for registration
         */
        System.out.println("Please enter the Last Date for registration : ");
        LocalDate registrationDate = AttributeGetter.getFutureDate();
        while (registrationDate.isAfter(startDate)) {
            System.out.println("Registration Date must be Before the Start Date...");
            System.out.println("Please enter another date: ");
            registrationDate = AttributeGetter.getFutureDate();
        }

        /**
         *  getting the camp visibility
         */
        boolean visibility = AttributeGetter.visibilityGetter();

        /**
         *  getting which faculties the camp is visible to
         */
        String faculty = AttributeGetter.facultyGetter();

        System.out.println("Please enter the Location : ");
        String location = scanner.nextLine();

        System.out.println("Please enter the Total Number of Slots ( Camp Attendees + Camp Committee ) : ");
        int totalSlots = AttributeGetter.readInt();
        while (totalSlots <= 0) {
            System.out.println("Total Number of Slots can't be less than or equal to zero...");
            System.out.println("Please try again");
            System.out.println("Please enter the Total Number of Slots ( Camp Attendees + Camp Committee ) : ");
            totalSlots = AttributeGetter.readInt();
        }

        System.out.println("Please enter the Total Number of Committee Slots ( max is 10 ) : ");
        int committeeSlots = AttributeGetter.readInt();
        while (committeeSlots > 10 || committeeSlots < 0 || committeeSlots>totalSlots) {
            if (committeeSlots > 10) {
                System.out.println("The maximum number of slots is 10...");
            } else if(committeeSlots<0) {
                System.out.println("Camp Committee Slots can't be less than zero...");
            } else{
                System.out.println("Committee Slots cant be greater than the total number of slots...");
            }
            System.out.println("Please try again");
            System.out.println("Please enter the Total Number of Committee Slots ( max is 10 ) : ");
            committeeSlots = AttributeGetter.readInt();
        }

        System.out.println("Please enter the description of the camp : ");
        String description = scanner.nextLine();
        System.out.println();
        System.out.println("Thank you for entering the Camp Information");
        System.out.println("Here is the preview of your Camp Info");

        Camp c = new Camp(campName, startDate, endDate, registrationDate, visibility, faculty, location, totalSlots, committeeSlots, description, staff.getID());
        System.out.println(c.getDisplayableString());
        System.out.println();
        boolean confirmation = AttributeGetter.confirmationGetter();
        if (confirmation) {
            try {
                CampManager.createCamp(c);
                System.out.println("Camp Created!");
            } catch (ModelAlreadyExistsException e) {
                throw new ModelAlreadyExistsException();
            }
        } else {
            System.out.println("Camp Information Destroyed...");
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
}

    /**
     * This method takes input from the staff to edit camp info ( description of the camp ) for the camps that he/she created
     * @param staff
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     * @throws PageBackException
     */
    private static void staffEditCamp(Staff staff) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        ChangePage.changePage();
        List<Integer> options;
        int choice = 0;
        Scanner scanner2 = new Scanner(System.in);
        options = staff.displayCampsEnrolled();
        if (options.size() == 0) {
            System.out.println("You have not created any camps yet...");
            System.out.println("Would you like to create one ? [Y/N]");
            String confirmation = scanner2.nextLine();
            if (Objects.equals(confirmation, "Y")) {
                staffCreateCamp(staff);
            }
        } else {
            System.out.println("Enter the CampID ( the number on the top row ) of the Camp you want to edit");
            choice = AttributeGetter.optionGetter(options);
        }
        int campNumber = choice;
        Camp c = CampRepository.getInstance().findByRules(camp -> camp.getCampId() == campNumber).get(0);
        System.out.println("Please enter the Description : ");
        String description = scanner2.nextLine();
        System.out.println();
        System.out.println("Here is your changed description : ");
        System.out.println(description);
        boolean confirmation = AttributeGetter.confirmationGetter();
        if(!confirmation){
            System.out.println("Edit cancelled!");
        }
        else{
            c.setDescription(description);
            CampManager.editCamp(c);
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method is to toggle the visibility of the camps that the staff created.
     * @param staff
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     * @throws PageBackException
     */
    private static void staffToggleVisibility(Staff staff) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        ChangePage.changePage();
        List<Integer> options;
        int choice = 0;
        Scanner scanner2 = new Scanner(System.in);
        options = staff.displayCampsEnrolled();
        if (options.size() == 0) {
            System.out.println("You have not created any camps yet...");
            System.out.println("Would you like to create one ? [Y/N]");
            String confirmation = scanner2.nextLine();
            if (Objects.equals(confirmation, "Y")) {
                staffCreateCamp(staff);
            }
        } else {
            System.out.println("Enter the CampID ( the number on the top row ) of the Camp you want to change the visibility for");
            choice = AttributeGetter.optionGetter(options);
        }
        int campNumber = choice;
        Camp c = CampRepository.getInstance().findByRules(camp -> camp.getCampId() == campNumber).get(0);
        boolean visibility = AttributeGetter.visibilityGetter();
        boolean confirmation = AttributeGetter.confirmationGetter();
        if(!confirmation){
            System.out.println("Change in visibility cancelled...");
        }
        else{
            c.setVisibility(visibility);
            CampManager.editCamp(c);
//            System.out.println("Visibility turned to : "+Boolean.toString(visibility));
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This is to delete the camps that the staff has created
     * @param staff
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     * @throws PageBackException
     */
    private static void staffDeleteCamp(Staff staff) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        ChangePage.changePage();
        List<Integer> options;
        int choice = 0;
        Scanner scanner2 = new Scanner(System.in);
        options = staff.displayCampsEnrolled();
        if (options.size() == 0) {
            System.out.println("You have not created any camps yet...");
            System.out.println("Would you like to create one ? [Y/N]");
            String confirmation = scanner2.nextLine();
            if (Objects.equals(confirmation, "Y")) {
                staffCreateCamp(staff);
            }
        } else {
            System.out.println("Enter the CampID ( the number on the top row ) of the Camp you want to delete");
            choice = AttributeGetter.optionGetter(options);
        }
        int campNumber = choice;
        Camp c = CampRepository.getInstance().findByRules(camp -> camp.getCampId() == campNumber).get(0);
        boolean confirmation = AttributeGetter.confirmationGetter();
        if(!confirmation){
            System.out.println("Delete Request cancelled...");
        }
        else{
            CampManager.deleteCamp(c);
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method is to view enquiries posted to the camps that the staff has generated
     * @param staff
     * @throws ModelNotFoundException
     * @throws PageBackException
     */
    public static void viewEnquiries(Staff staff) throws ModelNotFoundException, PageBackException {
        EnquiriesViewerForStaff enquiriesViewerForStaff = new EnquiriesViewerForStaff();
        enquiriesViewerForStaff.displayEnquiryInfo(staff);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method is for the staff to reply to the enquiries that have been posted to the camps created
     * @param staff
     * @throws PageBackException
     * @throws ModelNotFoundException
     * @throws ModelAlreadyExistsException
     */
    public static void replyToEnquiries(Staff staff) throws PageBackException, ModelNotFoundException, ModelAlreadyExistsException {
        ReplyEnquiry replyEnquiryForStaff = new ReplyEnquiryForStaff();
        replyEnquiryForStaff.replyEnquiry(staff);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

}
