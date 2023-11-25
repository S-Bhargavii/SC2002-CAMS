package main.boundary.mainpage;

import main.controller.enquiry.ReplyEnquiry;
import main.controller.enquiry.ReplyEnquiryForCommittee;
import main.controller.report.ReportGenerator;
import main.controller.report.TXTReportGenerator;
import main.controller.suggestion.SuggestionManager;
import main.model.enquiry.EnquiriesViewerForCommittee;
import main.model.enquiry.EnquiryViewer;
import main.model.suggestion.Suggestion;
import main.model.suggestion.SuggestionsViewerByUser;
import main.model.suggestion.SuggestionsViewerForCommittee;
import main.repository.suggestion.SuggestionRepository;
import main.utils.ui.AttributeGetter;
import main.boundary.account.ChangeAccountPassword;
import main.boundary.account.Logout;
import main.model.camp.Camp;
import main.model.user.Committee;
import main.model.user.User;
import main.model.user.UserType;
import main.repository.camp.CampRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * this class is the Committee Main page. It is what the camp committee sees, they have special privileges
 * than a camp attendee.
 */

public class CommitteeMainPage {
    /**
     * takes in the User object from the login page
     * @param user the user object which in this case is a committee member
     * @throws PageBackException is thrown everytime the user needs to go back to the committee Main page
     */
    public static void commiteeMainPage(User user) throws PageBackException {
        if(user instanceof Committee committee){
            System.out.println();
            System.out.println(BoundaryStrings.separator);
            System.out.println("Welcome " + committee.getUserName() + " ! What would you like to do today ? ");
            System.out.println(BoundaryStrings.separatorThin);
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
            System.out.println("\t11. View MY Enquiries");
            System.out.println("\t12. View OVERSEEING CAMP'S Enquiries");
            System.out.println("\t13. Reply to Enquiries");
            System.out.println("\t14. Submit Suggestions");
            System.out.println("\t15. View Suggestions");
            System.out.println("\t16. Edit Suggestions");
            System.out.println("\t17. Delete Suggestions");
            System.out.println("\t18. Generate Student Report");
            System.out.println("\t19. Logout");
            System.out.println(BoundaryStrings.separator);
            System.out.println();
            System.out.println("Please enter your choice: ");
            int choice = AttributeGetter.readInt();
            try{
                switch (choice){
                    case 1-> ChangeAccountPassword.changePassword(UserType.COMMITTEE, committee.getID());
                    case 2-> committee.displayStudentProfile();
                    case 3-> StudentMainPage.displayAllVisibleCamps(committee);
                    case 4-> StudentMainPage.displayCampsRegistered(committee);
                    case 5-> StudentMainPage.displayUpcomingRegisteredCamps(committee);
                    case 6-> StudentMainPage.registerForCamp(committee);
                    case 7-> StudentMainPage.withdrawFromCamp(committee);
                    case 8-> StudentMainPage.submitEnquiries(committee);
                    case 11-> StudentMainPage.viewAllEnquiries(committee);
                    case 12-> viewOverSeeingCampsEnquiries(committee);
                    case 13-> replyToEnquiries(committee);
                    case 14-> submitSuggestions(committee);
                    case 15-> viewMySuggestions(committee);
                    case 16-> editSuggestions(committee);
                    case 17-> deleteSuggestion(committee);
                    case 18-> generateStudentList(committee);
                    case 19-> Logout.logout();
                }
            }
            catch(PageBackException e){
                CommitteeMainPage.commiteeMainPage(committee);
            } catch (ModelNotFoundException | ModelAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Generates the student list for the camp that he is overseeing
     * @param committee the committee object that is trying to generate the student list
     * @throws ModelNotFoundException is thrown when the camp that the committee is a committee member of does not exist
     * @throws PageBackException is thrown when the committee wants to go back to the committee main page
     */

    private static void generateStudentList(Committee committee) throws ModelNotFoundException, PageBackException {
        Camp camp = CampRepository.getInstance().getByID(committee.getRegisteredCampAsCommittee());
        String listType = AttributeGetter.listTypeGetterForCom();
        ReportGenerator txtReportGenerator = new TXTReportGenerator();
        switch (listType){
            case "attendee"->  txtReportGenerator.generateAttendeeList(camp);
            case "committee"-> txtReportGenerator.generateCommitteeList(camp);
            case "all"-> txtReportGenerator.generateStudentsList(camp);
        }
        System.out.println("Would you like to generate another report? [Y/N]");
        String desc = AttributeGetter.ynGetter();
        if(Objects.equals(desc, "Y")){
            generateStudentList(committee);
        }
        else{
            System.out.println("Enter enter to go back to the Main Menu! ");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
    }

    /**
     * Displays the enquiries and their replies to the camp that they oversee
     * @param committee the committee member who wants to see the overseeing camps enquiries and their replies
     * @throws ModelNotFoundException is thrown when the commitee member doesn't exist in the committee repository when trying to access the overseeing camp id
     * @throws PageBackException is thrown when the committee member wants to go back to the committee main page
     */
    private static void viewOverSeeingCampsEnquiries(Committee committee) throws ModelNotFoundException, PageBackException {
        EnquiryViewer enquiryViewerForCommittee = new EnquiriesViewerForCommittee();
        enquiryViewerForCommittee.displayEnquiryInfo(committee);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method is to reply to the queries posted by camp attendees
     * @param committee the committee member who wants to reply to the enquiries to the camps they are overseeing
     * @throws PageBackException is thrown when the committee member wants to go back to the committee main page
     * @throws ModelNotFoundException is thrown when the commitee member doesn't exist in the committee repository when trying to access the overseeing camp id
     * @throws ModelAlreadyExistsException is thrown when the committee tries to overwrite an already existing enquiry
     */
    public static void replyToEnquiries(Committee committee) throws PageBackException, ModelNotFoundException, ModelAlreadyExistsException {
        ReplyEnquiry replyEnquiryForCommittee = new ReplyEnquiryForCommittee();
        replyEnquiryForCommittee.replyEnquiry(committee);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * this method is to submit suggestions regarding the camp to the staff in charge of the camp
     * @param committee the committee member
     * @throws ModelNotFoundException is thrown when the commitee member doesn't exist in the committee repository when trying to access the overseeing camp id
     * @throws ModelAlreadyExistsException is thrown when the committee member tries to overwrite an already existing suggestions object
     * @throws PageBackException is thrown when the committee member wants to go back to the committee main page
     */
    private static void submitSuggestions(Committee committee) throws ModelNotFoundException, ModelAlreadyExistsException, PageBackException {
        ChangePage.changePage();
        Camp camp = CampRepository.getInstance().getByID(committee.getRegisteredCampAsCommittee());
        System.out.println(camp.getCampPreview(committee));
        System.out.println();
        System.out.println("Please enter your suggestion ( What would you like to change the description to ?) ");
        Scanner scanner = new Scanner(System.in);
        String newDescription = scanner.nextLine();
        Suggestion suggestion = new Suggestion(camp.getCampName(),newDescription,committee.getUserName());
        System.out.println(suggestion.getDisplayableString());
        boolean confirmation = AttributeGetter.confirmationGetter();
        if(confirmation){
            SuggestionManager.submitSuggestion(suggestion);
        }
        else{
            System.out.println("Suggestion request has been cancelled!");
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method is to view the suggestions and the status of the suggestions
     * that have been posted by the camp committee member thus far
     * @param committee the committee member object who wants to view suggestions that they have submitted to the staff
     * @throws ModelNotFoundException is thrown when the suggestions object is not found in the repository
     * @throws PageBackException is thrown when the committee member wants to go back to the committee main page
     */
    private static void viewMySuggestions(Committee committee) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        SuggestionsViewerByUser suggestionsViewerForCommittee = new SuggestionsViewerForCommittee();
        suggestionsViewerForCommittee.getAllSuggestionsByUser(committee);
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method is for the committee member to edit suggestions that have not been processed yet.
     * It simply takes input from the committee member and redirects the user to the enquiry manager page
     * @param committee the committee member who wants to view edit the suggestions
     * @throws ModelNotFoundException is thrown when the suggestions object is not found in the repository
     * @throws PageBackException is thrown when the committee member wants to go back to the committee main page
     */
    private static void editSuggestions(Committee committee) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        SuggestionsViewerByUser suggestionsViewerForCommittee = new SuggestionsViewerForCommittee();
        List<Integer> optionsAvailable = suggestionsViewerForCommittee.getNonProcessedByUser(committee);
        if(optionsAvailable.size()==0){
            System.out.println("Enter enter to go back to the Main Menu! ");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        System.out.println("Select A Suggestion To Edit ");
        System.out.println("Please Enter the Suggestion ID ( the number on the top row ) of the Enquiry");
        int choice = AttributeGetter.optionGetter(optionsAvailable);
        Suggestion suggestion = SuggestionRepository.getInstance().getByID(Integer.toString(choice));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Edit Your Suggestion ( Type in your edited camp description suggestion ) : ");
        String newCampDesc = scanner.nextLine();
        boolean decision = AttributeGetter.confirmationGetter();
        if(decision){
            suggestion.setSuggestion(newCampDesc);
            SuggestionManager.editSuggestion(suggestion);
        }
        else{
            System.out.println("Edit request has been cancelled!");
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * This method is to allow the committee member to delete suggestions which have not been processed yet.
     *
     * @param committee the committee member who wants to view delete the suggestions
     * @throws ModelNotFoundException is thrown when the suggestions object is not found in the repository
     * @throws PageBackException is thrown when the committee member wants to go back to the committee main page
     */
    private static void deleteSuggestion(Committee committee) throws ModelNotFoundException, PageBackException {
        ChangePage.changePage();
        SuggestionsViewerByUser suggestionsViewerForCommittee = new SuggestionsViewerForCommittee();
        List<Integer> optionsAvailable = suggestionsViewerForCommittee.getNonProcessedByUser(committee);
        if(optionsAvailable.size()==0){
            System.out.println("Enter enter to go back to the Main Menu! ");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        System.out.println("Select A Suggestion To Edit ");
        System.out.println("Please Enter the Suggestion ID ( the number on the top row ) of the Enquiry");
        int choice = AttributeGetter.optionGetter(optionsAvailable);
        Suggestion suggestion = SuggestionRepository.getInstance().getByID(Integer.toString(choice));
        System.out.println(suggestion.getDisplayableString());
        boolean decision = AttributeGetter.confirmationGetter();
        if(decision){
            SuggestionManager.deleteSuggestion(suggestion);
        }
        else{
            System.out.println("Delete request cancelled!");
        }
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

}
