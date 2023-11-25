package main.model.user;
import main.model.camp.Camp;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.iocontrol.Mappable;
import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;
import main.utils.ui.ChangePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a staff, which is a type of user.
 * It extends the User class and includes a supervisor ID field.
 */
public class Staff implements User {
    /**
     * The ID of the staff.
     */
    private String staffID;
    /**
     * The name of the staff
     */
    private String staffName;
    /**
     * The email of a staff
     */
    private String email;
    /**
     * The hashed password of the staff
     */
    private String hashedPassword;
    /**
     * The faculty of the staff
     */
    private String faculty;
    /**
     * This tells if the staff has logged in before or if this is the first time
     */
    private boolean notFirstTimeLogin = false;
    /**
     * Constructs a new Staff object with the specified supervisor ID.
     *
     * @param staffID   the ID of the supervisor.
     * @param staffName the name of the supervisor.
     * @param email          the email of the supervisor.
     *
     */
    public Staff(String staffID, String staffName, String email,String faculty) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.email = email;
        this.faculty = faculty;
    }

    /**
     * Constructs a new Staff object with the specified staff ID and password.
     *
     * @param staffID   the ID of the staff.
     * @param staffName the name of the staff.
     * @param email          the email of the staff.
     * @param hashedPassword the password of the staff.
     * @param faculty       the faculty of the staff
     */
    public Staff(String staffID, String staffName, String email, @NotNull String hashedPassword, String faculty) {
        this.hashedPassword = hashedPassword;
        this.staffID = staffID;
        this.staffName = staffName;
        this.email = email;
        this.faculty = faculty;
    }

    /**
     * Constructs a new Staff object from a map
     *
     * @param map the map
     */
    public Staff(Map<String, String> map) {
        this.fromMap(map);
    }

    /**
     * default constructor for Staff class
     */
    public Staff() {
        this.staffID = EmptyID.EMPTY_ID;
        this.staffName = EmptyID.EMPTY_ID;
        this.email = EmptyID.EMPTY_ID;
        this.faculty = EmptyID.EMPTY_ID;
    }



    public static User getUser(Map<String, String> map) {
        return new Staff(map);
    }

    /**
     * Gets the user ID of the user.
     *
     * @return the ID of the user.
     */
    @Override
    public String getID() {
        return this.staffID;
    }

    /**
     * Gets the username
     *
     * @return the name of the user
     */
    @Override
    public String getUserName() {
        return this.staffName;
    }

    public static String getUserNameByID(String ID) throws ModelNotFoundException {
        Staff staff = StaffRepository.getInstance().getByID(ID);
        return staff.getUserName();
    }
    /**
     * Gets the hashed password of the user
     *
     * @return the hashed password of the user
     */
    @Override
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     * Sets the hashed password of the user
     *
     * @param hashedPassword the hashed password of the user
     */
    @Override
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets if this is the first time the staff is logging in
     * @return
     */
    @Override
    public boolean getNotFirstTimeLogin() {
        return notFirstTimeLogin;
    }

    /**
     * sets the setNotFirstTimeLogin to true after the user logs in for the first time
     */
    @Override
    public void setNotFirstTimeLogin(){
        this.notFirstTimeLogin = true;
    }

    /**
     * Gets the faculty that the staff belongs to
     * @return the faculty of the staff
     */
    @Override
    public String getFaculty() {
        return faculty;
    }

    /**
     * Sets the faculty
     * @param faculty the faculty that is being set to
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * gives the camps that the staff has created
     * @return the list of the ids of the camp for the staff to select from
     * @throws ModelNotFoundException
     */
    @Override
    public List<Integer> displayCampsEnrolled() throws ModelNotFoundException {
        ChangePage.changePage();
        List<Integer> options = new ArrayList<>();
        List<Camp> campList= CampRepository.getInstance().findByRules(c-> Objects.equals(c.getStaffInChargeID(), this.getID()));
        for ( Camp camp : campList){
            options.add(camp.getCampId());
            System.out.println(camp.getDisplayableString());
        }
        return options;
    }
}
