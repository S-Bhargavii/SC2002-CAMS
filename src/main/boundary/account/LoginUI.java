package main.boundary.account;

import main.boundary.mainpage.CommitteeMainPage;
import main.boundary.mainpage.StaffMainPage;
import main.boundary.mainpage.StudentMainPage;
import main.controller.account.AccountManager;
import main.controller.account.user.UserFinder;
import main.model.user.Student;
import main.model.user.User;
import main.model.user.UserType;
import main.repository.user.CommitteeRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.ui.AttributeGetter;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;
import main.utils.ui.Colors;

import java.util.Scanner;

/**
 * This class provides a user interface (UI) for the user to login.
 */
public class LoginUI {
    /**
     * Displays a login page.
     *
     * @throws PageBackException if the user chooses to go back to the previous page.
     */
    public static void login() throws PageBackException, ModelNotFoundException {
        ChangePage.changePage();
        UserType domain = AttributeGetter.getDomain();
        String userID = AttributeGetter.getUserID();
        if (userID.equals("")) {
            try {
                ForgetUserID.forgotUserID();
            } catch (PageBackException e) {
                login();
            }
        }
        if(domain== UserType.COMMITTEE){
            try{
                Student student1 = StudentRepository.getInstance().getByID(userID);
                if(!student1.isCampCommitteeMember()){
                    System.out.println(BoundaryStrings.separatorThin);
                    System.out.println(Colors.red +"YOU ARE NOT A CAMP COMMITTEE MEMBER!");
                    System.out.println("PLEASE LOGIN AS A STUDENT!"+Colors.reset);
                    System.out.println(BoundaryStrings.separator);
                    throw new PageBackException();
                }
            } catch (ModelNotFoundException e) {
                System.out.println("User not found.");
                System.out.println("Enter [b] to go back, or any other key to try again.");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();
                if (choice.equals("b")) {
                    throw new PageBackException();
                } else {
                    System.out.println("Please try again.");
                    login();
                }
            }
        }
        String password = AttributeGetter.getPassword();
        try {
            User user = AccountManager.login(domain, userID, password);
            if(!user.getNotFirstTimeLogin()){
                System.out.println(BoundaryStrings.separatorThin);
                System.out.println(Colors.red+"Since it is your first time logging in...You'll have to change your password!"+Colors.reset);
                System.out.println(BoundaryStrings.separatorThin);
                ChangeAccountPassword.changePassword(domain,userID);
            }
            switch (domain) {
                case STUDENT -> {
                    if(StudentRepository.getInstance().getByID(userID).isCampCommitteeMember()){
                        System.out.println(BoundaryStrings.separatorThin);
                        System.out.println(Colors.red+"Since you are a camp committee member, you will be directed to the camp committee page where you can access special privileges!"+Colors.reset);
                        System.out.println(BoundaryStrings.separatorThin);
                        user = CommitteeRepository.getInstance().getByID(user.getID());
                        CommitteeMainPage.commiteeMainPage(user);
                    }
                    else{
                        StudentMainPage.studentMainPage(user);
                    }
                }
                case STAFF -> StaffMainPage.staffMainPage(user);
                case COMMITTEE -> CommitteeMainPage.commiteeMainPage(user);
                default -> throw new IllegalStateException("Unexpected value: " + domain);
            }
            return;
        } catch (PasswordIncorrectException e) {
            System.out.println("Password incorrect.");
        } catch (ModelNotFoundException e) {
            System.out.println("User not found.");
        }
        System.out.println("Enter [b] to go back, or any other key to try again.");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("b")) {
            throw new PageBackException();
        } else {
            System.out.println("Please try again.");
            login();
        }
    }
}
