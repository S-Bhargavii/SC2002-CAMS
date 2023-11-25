package main.model.camp;

import main.model.user.Staff;
import main.model.user.Student;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.ui.AttributeGetter;
import main.utils.ui.ChangePage;
import main.utils.ui.Colors;
import main.utils.ui.DateClash;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  Class for viewing camps
 */
public class CampViewer {
    /**
     * Displays all the visible camps to the student of hte same faculty and ALL
     * @param student the student object who wants to view the camp
     * @return the list of the ids of camps
     * @throws ModelNotFoundException
     */
    public static List<Integer> displayAllVisibleCamps(Student student) throws ModelNotFoundException {
        List<Integer> options = new ArrayList<>();
        List<Camp> campList= CampRepository.getInstance().findByRules(c->c.getVisibility()==Boolean.TRUE, c -> Objects.equals(c.getFaculty(), student.getFaculty()) || Objects.equals(c.getFaculty(), "ALL"));
        Collections.sort(campList, Comparator.comparing(obj -> ((Camp) obj).getCampName()));
        for ( Camp camp : campList){
            System.out.println(camp.getDisplayableString());
            options.add(camp.getCampId());
        }
        return options;
    }


    /**
     * Displays the info of all camps
     * @throws ModelNotFoundException
     */
    public static void displayAllCampInfo() throws ModelNotFoundException {
        ChangePage.changePage();
        List<Camp> campList= CampRepository.getInstance().getList();
        for ( Camp camp : campList){
            System.out.println(camp.getDisplayableString());
        }
    }

    /**
     * Displays all the upcoming registered camps of the student
     * @param student who wants to view it
     * @throws ModelNotFoundException
     */
    public static void displayUpcomingRegisteredCamps(Student student) throws ModelNotFoundException{
        Camp c = null;
        List<Integer> options = new ArrayList<>();
        if(student.isCampCommitteeMember()){
            c = CampRepository.getInstance().getByID(student.getRegisteredCampAsCommittee());
            if(c.getStartDate().isAfter(LocalDate.now())) {
                options.add(c.getCampId());
                System.out.println(c.getCampPreview(student));
            }
        }
        for(String campsRegistered : student.getRegisteredCampsAsAttendee()) {
            if (Objects.equals(campsRegistered, "")) {
                continue;
            }
            if(c.getStartDate().isAfter(LocalDate.now())) {
                options.add(c.getCampId());
                System.out.println(c.getCampPreview(student));
            }
        }
        if(options.size()==0){
            System.out.println("You don't have any upcoming registered camps");
        }
    }

    /**
     * Displays all the visbile camps by filter --> can filter on start date, end date, registraion date,, faculty, staff in charge etc
     *
     * @param student of the student who wants to view it
     * @throws ModelNotFoundException
     */
    public static void displayAllVisibleCampsByFilter(Student student) throws ModelNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to apply any filter? [Y/N]");
        String choice = AttributeGetter.ynGetter();
        if(choice.equals("N")){
            CampViewer.displayAllVisibleCamps(student);
        }
        else{
            List<Camp> campList= CampRepository.getInstance().findByRules(c->c.getVisibility()==Boolean.TRUE, c -> Objects.equals(c.getFaculty(), student.getFaculty()) || Objects.equals(c.getFaculty(), "ALL"));
            Collections.sort(campList, Comparator.comparing(obj -> ((Camp) obj).getCampName()));
            List<Integer> options = new ArrayList<>();
            options.add(1);
            options.add(2);
            int option;
            System.out.println("For the following questions : Press Y to apply a filter, and N to move onto the next question");
            System.out.println("START DATE");
            choice = AttributeGetter.ynGetter();
            if(Objects.equals(choice, "Y")){
                System.out.println("Enter the start date");
                LocalDate startDate = AttributeGetter.getAnyDate();
                System.out.println("Do you want to get the list of camps that start before or after this date ");
                System.out.println("\t1. On and After");
                System.out.println("\t2. On and Before");
                option = AttributeGetter.optionGetter(options);
                switch(option){
                    case 1-> campList = campList.stream().filter(camp -> camp.getStartDate().isAfter(startDate)).collect(Collectors.toList());
                    case 2-> campList = campList.stream().filter(camp->camp.getStartDate().isBefore(startDate)).collect(Collectors.toList());
                }
            }
            System.out.println("END DATE");
            choice = AttributeGetter.ynGetter();

            if(Objects.equals(choice, "Y")){
                System.out.println("Enter the end date");
                LocalDate endDate = AttributeGetter.getAnyDate();
                System.out.println("Do you want to get the list of camps that start before or after this date ");
                System.out.println("\t1. On and After");
                System.out.println("\t2. On and Before");
                option = AttributeGetter.optionGetter(options);
                switch (option) {
                    case 1 -> campList = campList.stream().filter(camp -> camp.getEndDate().isAfter(endDate)).collect(Collectors.toList());
                    case 2 -> campList = campList.stream().filter(camp -> camp.getEndDate().isBefore(endDate)).collect(Collectors.toList());
                }
            }

            System.out.println("REGISTRATION CLOSING DATE");
            choice = AttributeGetter.ynGetter();
            if(Objects.equals(choice, "Y")){
                System.out.println("Enter the registration closing date");
                LocalDate registrationClosingDate = AttributeGetter.getAnyDate();
                System.out.println("Do you want to get the list of camps that start before or after this date ");
                System.out.println("\t1. On and After");
                System.out.println("\t2. On and Before");
                option = AttributeGetter.optionGetter(options);
                switch (option) {
                    case 1 -> campList = campList.stream().filter(camp -> camp.getRegistrationClosingDate().isAfter(registrationClosingDate)).collect(Collectors.toList());
                    case 2 -> campList = campList.stream().filter(camp -> camp.getRegistrationClosingDate().isBefore(registrationClosingDate)).collect(Collectors.toList());
                }
            }
            System.out.println("FACULTY");
            choice = AttributeGetter.ynGetter();
            if(Objects.equals(choice,"Y")){
                System.out.println("Please chose the faculty you want to filter by");
                System.out.println("\t1. "+student.getFaculty());
                System.out.println("\t2. ALL");
                option = AttributeGetter.optionGetter(options);
                switch(option){
                    case 1-> campList = campList.stream().filter(camp -> Objects.equals(camp.getFaculty(), student.getFaculty())).collect(Collectors.toList());
                    case 2->campList = campList.stream().filter(camp-> Objects.equals(camp.getFaculty(), "ALL")).collect(Collectors.toList());
                }
            }

            System.out.println("LOCATION");
            choice = AttributeGetter.ynGetter();
            if(Objects.equals(choice,"Y")){
                System.out.println("Enter the location you want to filter by");
                String location = scanner.nextLine();
                campList = campList.stream().filter(camp-> Objects.equals(camp.getLocation(), location)).collect(Collectors.toList());
            }

            System.out.println("STAFF IN CHARGE");
            choice = AttributeGetter.ynGetter();
            if(Objects.equals(choice,"Y")) {
                List<Staff> staffList = StaffRepository.getInstance().getList();
                List<String> staffNames = new ArrayList<>();
                options.clear();
                int i = 0;
                for (Staff s : staffList) {
                    staffNames.add(s.getID());
                    options.add(i);
                    i++;
                    System.out.println("\t" + i + ". " + s.getUserName());
                }
                option = AttributeGetter.optionGetter(options);
                int index = option;
                String staffID = staffNames.get(index-1);
                campList = campList.stream().filter(camp -> Objects.equals(camp.getStaffInChargeID(), staffID)).collect(Collectors.toList());

            }
            if(campList.size()==0){
                System.out.println("No camps exist with the filters given");
            }
            for (Camp c : campList) {
                System.out.println(c.getDisplayableString());
            }
        }
    }


    /**
     * Given a student, it displays all the camps that the student can register TO
     * @param student the student who wants to view
     * @return nothing
     * @throws ModelNotFoundException
     */
    public static List<Integer> registrableCamps(Student student) throws ModelNotFoundException {
        System.out.println(Colors.blue+"NOTE:");
        System.out.println("\t1. You can't see camps whose visibility has been turned off by the staff in charge.");
        System.out.println("\t2. You can't see the camps whose timings clash with your registered camps.");
        System.out.println("\t3. You can't see the camps which have already been filled up.");
        System.out.println("\t4. You can't see the camps to which you have already registered."+Colors.reset);
        Scanner s = new Scanner(System.in);
        List<Integer> optionsAvailable = new ArrayList<>();
        List<Camp> allCamps = CampRepository.getInstance().findByRules(c->c.getRegistrationClosingDate().isAfter(LocalDate.now()), Camp::getVisibility, c->c.getTotalSlots()>0, c -> Objects.equals(c.getFaculty(), student.getFaculty()) || Objects.equals(c.getFaculty(), "ALL"));
        for(Camp c:allCamps){
            if(student.getRegisteredCampsAsAttendee().contains(c.getCampName())){}
            else if(Objects.equals(c.getCampName(), student.getRegisteredCampAsCommittee())){}
            else if(DateClash.dateClash(c,student)){}
            else{
                optionsAvailable.add(c.getCampId());
                System.out.println(c.getDisplayableString());
            }
        }
        return optionsAvailable;
    }

}

