package main.model.user;

import main.model.camp.Camp;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.Mappable;
import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.ChangePage;
import main.utils.ui.DateStringConverter;
import main.utils.ui.IntStringConverter;

import java.util.*;

/**
 * This class represents a student, which is a type of user.
 * It extends the User class and includes a student ID field.
 */
public class Student implements User, Mappable {
    /**
     * This is to know whether the student has already logged in before or if its the first time
     */
    boolean notFirstTimeLogin = false;
    /**
     * The ID of the student.
     * The username of the student
     */
    private String studentID;
    /**
     * The name of a student
     */
    private String studentName;
    /**
     * The email of a student
     */
    private String email;

    private String faculty;
    private String hashedPassword;
    private List<String>RegisteredCampsAsAttendee;
    private String RegisteredCampAsCommittee;
    private List<String>WithdrawnCamps;
    private boolean isCampCommitteeMember;

    /**
     * Constructs a new Student object with the specified student ID and default password.
     *
     * @param studentID   the ID of the student.
     * @param studentName the name of the student.
     * @param email       the email of the student.
     */
    public Student(String studentID, String studentName, String email,String faculty) {

        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
        this.faculty = faculty;
        this.RegisteredCampsAsAttendee = new ArrayList<>();
        this.RegisteredCampAsCommittee = "";
        this.WithdrawnCamps = new ArrayList<>();
        this.isCampCommitteeMember = false;
    }

    /**
     * Constructs a new Student object with the specified student ID and password.
     *
     * @param studentID      the ID of the student.
     * @param studentName    the name of the student.
     * @param email          the email of the student.
     * @param hashedPassword the password of the student.
     * @param faculty        the faculty of the student
     */
    public Student(String studentID, String studentName, String email, @NotNull String hashedPassword,String faculty) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.faculty = faculty;
        this.RegisteredCampsAsAttendee = new ArrayList<>();
        this.RegisteredCampAsCommittee = null;
        this.WithdrawnCamps = new ArrayList<>();
        this.isCampCommitteeMember = false;
    }

    public Student(Student s){
//        System.out.println("super constructutor called");
//        System.out.println("valeu being passed in"+s.studentID);
        this.studentID = s.getStudentID();
        this.studentName = s.getUserName();
        this.email = s.getEmail();
        this.hashedPassword = s.getHashedPassword();
        this.faculty = s.getFaculty();
        this.RegisteredCampsAsAttendee = s.getRegisteredCampsAsAttendee();
        this.RegisteredCampAsCommittee = s.getRegisteredCampAsCommittee();
        this.WithdrawnCamps = s.getWithdrawnCamps();
        this.isCampCommitteeMember = true;
        this.notFirstTimeLogin = s.getNotFirstTimeLogin();
//        System.out.println("DID IT WORK"+this.studentID);
    }

    /**
     * Constructs a new Student object with the specified student ID and password.
     *
     * @param informationMap the map
     */
    public Student(Map<String, String> informationMap) {
        fromMap(informationMap);
    }

    /**
     * default constructor for Student class
     */
    public Student() {
        super();
        this.email = EmptyID.EMPTY_ID;
        this.studentID = EmptyID.EMPTY_ID;
        this.studentName = EmptyID.EMPTY_ID;
        this.faculty = EmptyID.EMPTY_ID;
    }

    public String getStudentProfile(){
        StringBuilder output = new StringBuilder();
        output.append(BoundaryStrings.separator).append("\n");
        String format = "| %-50s: %-50s |%n";
        output.append(String.format(format,"Student Name",this.getUserName()));
        output.append(String.format(format,"Student Faculty",this.getFaculty()));
        if(this.isCampCommitteeMember()){
            output.append(String.format(format,"Camp Committee Member","YES"));
            output.append(String.format(format,"Registered Camps as Camp Committee",this.getRegisteredCampAsCommittee()));
        }
        else{
            output.append(String.format(format,"Camp Committee Member","NO"));
        }
        output.append(String.format(format,"Registered Camps as Camp Attendee",this.getRegisteredCampsAsAttendee().toString()));
        output.append(BoundaryStrings.separator).append("\n");
        return output.toString();
    }

    public void displayStudentProfile() throws PageBackException {
        ChangePage.changePage();
        System.out.println(getStudentProfile());
        System.out.println("Enter enter to go back to the Main Menu! ");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }
    /**

     Creates a new Student object based on the information in the provided map.
     The map should contain the necessary information to construct the Student object,
     such as the student's name, email, and ID.
     @param informationMap a map containing the information required to create a new Student object
     @return a new Student object with the information provided in the map
     */
    public static User getUser(Map<String, String> informationMap) {
        return new Student(informationMap);
    }

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    @Override
    public String getID() {
        return studentID;
    }

    public List<String> getRegisteredCampsAsAttendee() {
        return RegisteredCampsAsAttendee;
    }

    public void setRegisteredCampsAsAttendee(List<String> registeredCampsAsAttendee) {
        RegisteredCampsAsAttendee = registeredCampsAsAttendee;
    }

    public void addRegisteredCampAsAttendee(String campname){
        this.RegisteredCampsAsAttendee.add(campname);
    }
    public void addWithdrawnCamps(String campname){
        this.getWithdrawnCamps().add(campname);
    }

    public void removeRegisteredCampAsAttendee(String campname){
        this.getRegisteredCampsAsAttendee().remove(campname);
    }

    public String getRegisteredCampAsCommittee() {
        return RegisteredCampAsCommittee;
    }

    public void setRegisteredCampAsCommittee(String registeredCampAsCommittee) {
        RegisteredCampAsCommittee = registeredCampAsCommittee;
    }

    public boolean isCampCommitteeMember() {
        return isCampCommitteeMember;
    }

    public void setCampCommitteeMember(boolean campCommitteeMember) {
        isCampCommitteeMember = campCommitteeMember;
    }

    public List<String> getWithdrawnCamps() {
        return WithdrawnCamps;
    }

    public void setWithdrawnCamps(List<String> withdrawnCamps) {
        WithdrawnCamps = withdrawnCamps;
    }

    /**
     * Gets the username of the user
     *
     * @return the name of the user
     */
    @Override
    public String getUserName() {
        return studentName;
    }

    public static String getUserNameByID(String ID) throws ModelNotFoundException {
        Student student = StudentRepository.getInstance().getByID(ID);
        return student.getUserName();
    }

    public static String getFacultyByID(String ID) throws ModelNotFoundException{
        Student student = StudentRepository.getInstance().getByID(ID);
        return student.getFaculty();
    }

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    @Override
    public String getEmail() {
        return email;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        System.out.println("ENTERED SET");
        this.studentID = studentID;
        System.out.println("SET SUCCESSFULL");
    }


    /**
     * getter for the password
     *
     * @return hashedPassword
     */
    public String getHashedPassword() {
        return hashedPassword;
    }
    @Override
    public boolean getNotFirstTimeLogin() {
        return notFirstTimeLogin;
    }

    @Override
    public void setNotFirstTimeLogin(){
        notFirstTimeLogin = true;
    }

    /**
     * setter for the password
     *
     * @param hashedPassword the password that to be set
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    @Override
    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    @Override
    public List<Integer> displayCampsEnrolled() throws ModelNotFoundException {
        Camp c = null;
        List<Integer> options = new ArrayList<>();
        String campInformation;
        if(this.isCampCommitteeMember){
            c = CampRepository.getInstance().getByID(this.getRegisteredCampAsCommittee());
            options.add(c.getCampId());
            campInformation = c.getCampPreview(this);
            System.out.println(campInformation);
        }

        for(String campsRegistered : this.getRegisteredCampsAsAttendee()) {
            if (Objects.equals(campsRegistered, "")) {
                continue;
            }
            c = CampRepository.getInstance().getByID(campsRegistered.trim());
            options.add(c.getCampId());
            campInformation = c.getCampPreview(this);
            System.out.println(campInformation);
        }
        if(options.size()==0){
            System.out.println("You haven't registered for any camps yet...");
        }
        return options;
    }

}
