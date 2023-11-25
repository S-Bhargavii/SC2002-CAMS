package main.boundary;

import main.boundary.welcome.Welcome;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;

/**
 * This class is the entry point of the application.
 */
public class UIEntry {
    /**
     * Checks if the application is being run for the first time.
     *
     * @return true if the application is being run for the first time, false otherwise.
     */
    private static boolean firstStart() {
        return AccountManager.repositoryIsEmpty() && CampManager.repositoryIsEmpty();
    }

    /**
     *  a global static variable to keep track of the camp id for easy access
     */
    public static int campIDTracker = 0;
    /**
     *  a global static variable to keep track of the enquiry id for easy access
     */
    public static int enquiryIDTracker = 0;
    /**
     *  a global static variable to keep track of the suggestions id for easy access
     */
    public static int suggestionsIDTracker = 0;
    /**
     * Starts the application.
     * If the application is being run for the first time, it loads the default users and camps.
     * Then it displays the welcome page.
     */
    public static void start() {
        if (firstStart()) {
            AccountManager.loadUsers();
            CampManager.loadCamps();
        }

        Welcome.welcome();
    }

}
