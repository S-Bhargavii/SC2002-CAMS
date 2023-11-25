package main.utils.ui;

import main.model.user.UserType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class is used to interact with the user and retrieve various attributes such as domain, password, UserID, and username.
 */
public class AttributeGetter {
    /**
     * Prompts the user to select their domain and returns their chosen UserType.
     *
     * @return The UserType chosen by the user.
     */
    public static UserType getDomain() {
        System.out.println(BoundaryStrings.separator);
        System.out.println("\t1. Student");
        System.out.println("\t2. Staff");
        System.out.println("\t3. Camp Committee");
        System.out.println(BoundaryStrings.separator);
        System.out.print("Please select your domain (1-3): ");
        UserType userType = null;
        while (userType == null) {
            Scanner scanner = new Scanner(System.in);
            int domain;
            try {
                domain = AttributeGetter.readInt();
            } catch (Exception e) {
                System.out.println("Please enter a number.");
                continue;
            }
            userType = switch (domain) {
                case 1 -> UserType.STUDENT;
                case 2 -> UserType.STAFF;
                case 3 -> UserType.COMMITTEE;
                default -> null;
            };
            if (userType == null) {
                System.out.println("Invalid domain. Please try again.");
            }
        }
        return userType;
    }

    /**
     * Prompts the user to enter their password securely without displaying it on the screen.
     *
     * @return The password entered by the user.
     */
    public static String getPassword() {
        System.out.print("Please enter your password: ");
        return PasswordReader.getPassword();
    }

    /**
     * Prompts the user to enter their UserID.
     *
     * @return The UserID entered by the user.
     */
    public static String getUserID() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your UserID (Press enter if you forget): ");
        return scanner.nextLine();
    }

    /**
     * Prompts the user to enter their username.
     *
     * @return The username entered by the user.
     */

    public static String getUserName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        return scanner.nextLine();
    }

    public static String facultyGetter(){
        System.out.println("Please select the faculty you want this camp to be open to");
        String faculty = null;
        System.out.println("\t1. SCSE");
        System.out.println("\t2. ADM");
        System.out.println("\t3. EEE");
        System.out.println("\t4. NBS");
        System.out.println("\t5. ALL");
        int choice = AttributeGetter.readInt();
        while(!(choice >= 1 && choice <= 5)){
            System.out.println("Option doesn't exist...");
            System.out.println("Please try again...");
            System.out.println("Please select the faculty you want this camp to be open to");
            System.out.println("\t1. SCSE");
            System.out.println("\t2. ADM");
            System.out.println("\t3. EEE");
            System.out.println("\t4. NBS");
            System.out.println("\t5. ALL");
            choice = AttributeGetter.readInt();
        }
        switch(choice){
            case 1:
                faculty = "SCSE";
                break;
            case 2:
                faculty = "ADM";
                break;
            case 3:
                faculty = "EEE";
                break;
            case 4:
                faculty = "NBS";
                break;
            case 5:
                faculty = "ALL";
                break;
            default:
                break;
        }
        return faculty;
    }

    public static Boolean visibilityGetter(){
        System.out.println("Please select the visibility of the camps: ");
        System.out.println("\t1. ON");
        System.out.println("\t2. OFF");
        Boolean visibility = null;
        int choice = AttributeGetter.readInt();
        while(choice!=1&&choice!=2){
            System.out.println("Option doesn't exist...");
            System.out.println("Please try again...");
            System.out.println("Please select the visibility of the camps: ");
            System.out.println("\t1. ON");
            System.out.println("\t2. OFF");
            choice = AttributeGetter.readInt();
        }
        switch(choice){
            case 1:
                visibility = Boolean.TRUE;
                break;
            case 2:
                visibility = Boolean.FALSE;
                break;
            default:
                break;
        }
        return visibility;
    }

    public static LocalDate getFutureDate(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a date (DD-MM-YYYY): ");
        String userInput = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date  = null;
        try {
            // Parse the user input into a LocalDate object using the specified format
            date = LocalDate.parse(userInput, formatter);
            if(date.isBefore(LocalDate.now())){
                System.out.println("The Date is in the past. Please try again");
                date = getFutureDate();
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter the date in the format DD-MM-YYYY.");
            date = getFutureDate();
        }
        return date;
    }

    public static LocalDate getAnyDate(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a date (DD-MM-YYYY): ");
        String userInput = scanner.nextLine();
        // Define the expected date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = null;
        try{
            date = LocalDate.parse(userInput,formatter);
        }catch(Exception e){
            System.out.println("Invalid date format. Please enter the date in the format DD-MM-YYYY.");
            date = getAnyDate();
        }
        return date;
    }

    public static int readInt() {
        try {
            return new Scanner(System.in).nextInt();
        } catch (Exception e) {
            System.out.println("Please enter a valid integer.");
            return readInt();
        }
    }

    public static int optionGetter(List<Integer> options){
        int choice = AttributeGetter.readInt();
        while(!options.contains(choice)){
            System.out.println("Please only choose from the options given to you!");
            choice = AttributeGetter.readInt();
        }
        return choice;
    }

    public static boolean confirmationGetter(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to confirm your actions [Y/N]");
        String choice = scanner.nextLine();
        boolean boolchoice = false;
        if(!Objects.equals(choice, "Y") && !Objects.equals(choice, "N")&& !Objects.equals(choice, "n")&& !Objects.equals(choice, "y")){
            System.out.println("Please enter Y or N...");
            boolchoice = confirmationGetter();
        }
        else if(Objects.equals(choice,"Y")|| Objects.equals(choice, "y")){
            boolchoice = true;
        }
        else{
            boolchoice = false;
        }
        return boolchoice;
    }

    public static String ynGetter(){
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        while(!Objects.equals(choice, "Y") && !Objects.equals(choice, "N")){
            System.out.println("Please enter Y for yes or N for no...");
            choice = scanner.nextLine();
        }
        return choice;
    }

    public static String listTypeGetterForCom(){
        System.out.println("Please select the type of list that you want to generate: ");
        System.out.println("\t1. List of Attendees");
        System.out.println("\t2. List of Committee Members");
        System.out.println("\t3. List of All Students");
        String listtype = null;
        int choice = AttributeGetter.readInt();
        while(choice!=1&&choice!=2&&choice!=3){
            System.out.println("Please select the type of list that you want to generate: ");
            System.out.println("\t1. List of Attendees");
            System.out.println("\t2. List of Committee Members");
            System.out.println("\t3. List of All Students");

            choice = AttributeGetter.readInt();
        }
        switch(choice){
            case 1:
                listtype = "attendee";
                break;
            case 2:
                listtype = "committee";
                break;
            case 3:
                listtype = "all";
                break;
        }
        return listtype;
    }

    public static String listTypeGetterForStaff(){
        System.out.println("Please select the type of list that you want to generate: ");
        System.out.println("\t1. List of Attendees");
        System.out.println("\t2. List of Committee Members");
        System.out.println("\t3. List of All Students");
        System.out.println("\t4. List of Enquiries");
        String listtype = null;
        int choice = AttributeGetter.readInt();
        while(choice!=1&&choice!=2&&choice!=3&&choice!=4){
            System.out.println("Please select the type of list that you want to generate: ");
            System.out.println("\t1. List of Attendees");
            System.out.println("\t2. List of Committee Members");
            System.out.println("\t3. List of All Students");
            System.out.println("\t4. List of Enquiries");

            choice = AttributeGetter.readInt();
        }
        switch(choice){
            case 1:
                listtype = "attendee";
                break;
            case 2:
                listtype = "committee";
                break;
            case 3:
                listtype = "all";
                break;
            case 4:
                listtype = "enquiry";
                break;
        }
        return listtype;
    }
}
