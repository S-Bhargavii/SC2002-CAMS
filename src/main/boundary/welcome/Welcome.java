package main.boundary.welcome;

import main.utils.ui.AttributeGetter;
import main.boundary.account.LoginUI;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;

/**
 * This class provides a UI for the user to enter the system.
 */
public class Welcome {
    /**
     * Displays a welcome page.
     */
    public static void welcome() {
        ChangePage.changePage();
        System.out.println(BoundaryStrings.WELCOME_LOGO);
        System.out.println("Welcome to the Camp Application and Management System (CAMs).\n");
        System.out.println(BoundaryStrings.separator);
        System.out.println("Please enter your choice to continue.");
        System.out.println("\t1. Login");
        System.out.println("\t2. Exit");
        System.out.print("Your choice (1-2): ");
        try {
            while (true) {
                int choice = AttributeGetter.readInt();
                switch (choice) {
                    case 1 -> LoginUI.login();
                    case 2 -> ExitPage.exitPage();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (PageBackException e) {
            welcome();
        } catch (ModelNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
