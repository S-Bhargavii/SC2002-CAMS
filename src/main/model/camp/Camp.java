package main.model.camp;
import main.Main;
import main.boundary.UIEntry;
import main.model.Model;
import main.model.Displayable;
import main.model.user.Committee;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.repository.user.StaffRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.DateStringConverter;
import main.utils.ui.IntStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Camp implements Model, Displayable {
    /**
     * the camp ID of camp
     */
    private int campId;
    /**
     *  the name of the camp
     */
    private String campName;
    /**
     *  the start date of camp
     */
    private LocalDate startDate;
    /**
     *  the end date of camp
     */
    private LocalDate endDate;
    /**
     *  the registration closing date of camp
     */
    private LocalDate registrationClosingDate;
    /**
     *  the faculty that the camp is open to
     */
    private String faculty;
    /**
     *  the visibility of the camp whether it is on or off
     */
    private Boolean visibility;
    /**
     *  the location of the camp
     */
    private String location;
    /**
     *  the total number of slots available
     */
    private int totalSlots;
    /**
     *  the total camp committee slots available
     */
    private int campCommitteeSlots;
    /**
     *  the total camp attendee slots available
     */
    private int campAttendeeSlots;
    /**
     *  the description of the camp
     */
    private String description;
    /**
     *  the id of the staff who created the camp
     */
    private String staffInChargeID;
    /**
     *  the list of names of camp attendees
     */
    private List<String> campAttendeesID;
    /**
     *  the list of names of camp committees
     */
    private List<String> campCommitteeMembersID;

    /**
     * Camp constructor
     * @param map
     */
    public Camp(Map<String, String> map){
        fromMap(map);
    }

    /**
     * Constructor for a camp
     * @param CampName
     * @param StartDate
     * @param EndDate
     * @param RegistrationClosingDate
     * @param visibility
     * @param Faculty
     * @param Location
     * @param TotalSlots
     * @param CampCommitteeSlots
     * @param Description
     * @param StaffInChargeID
     */
    public Camp(String CampName, LocalDate StartDate, LocalDate EndDate, LocalDate RegistrationClosingDate, Boolean visibility, String Faculty,
                String Location, int TotalSlots, int CampCommitteeSlots, String Description, String StaffInChargeID){
        UIEntry.campIDTracker++;
        this.campId = UIEntry.campIDTracker;
        this.campName = CampName;
        this.startDate = StartDate;
        this.endDate = EndDate;
        this.registrationClosingDate = RegistrationClosingDate;
        this.visibility = visibility;
        this.faculty = Faculty;
        this.location = Location;
        this.totalSlots = TotalSlots;
        this.campAttendeeSlots = TotalSlots - CampCommitteeSlots;
        this.campCommitteeSlots = CampCommitteeSlots;
        this.description = Description;
        this.staffInChargeID = StaffInChargeID;
        this.campAttendeesID = new ArrayList<>();
        this.campCommitteeMembersID = new ArrayList<>();
    }

    /**
     * Gets the camp preview
     * @return
     * @throws ModelNotFoundException
     */
    public String getCampPreview() throws ModelNotFoundException {
        try{
            StringBuilder output = new StringBuilder();
            output.append(BoundaryStrings.separator).append("\n");
            String format = "| %-40s: %-60s |%n";
            output.append(String.format(format,"Camp Id",this.campId));
            output.append(String.format(format, "Camp Name", this.getCampName()));
            output.append(String.format(format, "Camp Start Date", DateStringConverter.dateString(this.getStartDate())));
            output.append(String.format(format, "Camp End Date", DateStringConverter.dateString(this.getEndDate())));
            output.append(String.format(format, "Camp Registration Close Date", DateStringConverter.dateString(this.getRegistrationClosingDate())));
            output.append(String.format(format,"Camp Visibility",this.getVisibility()?"ON":"OFF"));
            output.append(String.format(format, "Faculty that can register for Camp", this.getFaculty()));
            output.append(String.format(format, "Total Attendee Slots Available", IntStringConverter.intString(this.getCampAttendeeSlots())));
            output.append(String.format(format, "Total Committee Slots Available", IntStringConverter.intString(this.getCampCommitteeSlots())));
            output.append(String.format(format, "Camp Description", this.getDescription()));
            output.append(String.format(format, "Camp created by", StaffRepository.getInstance().getByID(this.staffInChargeID).getUserName()));
            output.append(BoundaryStrings.separator).append("\n");
            return output.toString();
        }catch(ModelNotFoundException e){
            throw new ModelNotFoundException();
        }
    }

    /**
     * Gets the camp preview for a student
     * @param student
     * @return
     * @throws ModelNotFoundException
     */
    public String getCampPreview(Student student) throws ModelNotFoundException {
        try{
            StringBuilder output = new StringBuilder();
            output.append(BoundaryStrings.separator).append("\n");
            String format = "| %-40s: %-60s |%n";
            output.append(String.format(format,"Camp Id",this.getCampId()));
            output.append(String.format(format, "Camp Name", this.getCampName()));
            if(Objects.equals(student.getRegisteredCampAsCommittee(), this.getCampName())){
                output.append(String.format(format,"Registered As","Camp Committee"));
            }
            else{
                output.append(String.format(format,"Registered As","Camp Attendee"));
            }
            output.append(String.format(format, "Camp Start Date", DateStringConverter.dateString(this.getStartDate())));
            output.append(String.format(format, "Camp End Date", DateStringConverter.dateString(this.getEndDate())));
            output.append(String.format(format, "Camp Registration Close Date", DateStringConverter.dateString(this.getRegistrationClosingDate())));
            output.append(String.format(format, "Faculty that can register for Camp", this.getFaculty()));
            output.append(String.format(format, "Total Attendee Slots Available", IntStringConverter.intString(this.getCampAttendeeSlots())));
            output.append(String.format(format, "Total Committee Slots Available", IntStringConverter.intString(this.getCampCommitteeSlots())));
            output.append(String.format(format, "Camp Description", this.getDescription()));
            output.append(String.format(format, "Camp created by", StaffRepository.getInstance().getByID(this.staffInChargeID).getUserName()));
            output.append(BoundaryStrings.separator).append("\n");
            return output.toString();
        }catch (ModelNotFoundException e){
            throw new ModelNotFoundException();
        }
    }

    /**
     * gets the camp id
     * @return campid
     */
    public int getCampId() {
        return campId;
    }

    /**
     * gets the camp name
     * @return campname
     */
    public String getCampName() {
        return campName;
    }

    /**
     * gets the StartDate
     * @return startdate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * gets the enddate
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * gets the registration closing date
     * @return registration closing date
     */
    public LocalDate getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    /**
     * gets the visibility of the camp
     * @return visibility
     */

    public Boolean getVisibility() {
        return visibility;
    }

    /**
     * sets the visbility of the camp
     * @param visibility
     */
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * gets the location of the camp
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location of the camp
     * @param location
     */

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * gets the description of the camp
     * @return description
     */

    public String getDescription() {
        return description;
    }

    /**
     * sets the description of the camp
     * @param description
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets the total slots ( attendee + committee ) of the camp
     * @return total slots
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * gets the number of attendee slots of the camp
     * @return attendee slots
     */
    public int getCampAttendeeSlots() {
        return campAttendeeSlots;
    }

    /**
     * gets the number of committee slots of the camp
     * @return committee slots
     */
    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }

    /**
     * sets the total number of slots of a camp
     * @param totalSlots
     */
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    /**
     * sets the total number of attendee slots
     * @param campAttendeeSlots
     */
    public void setCampAttendeeSlots(int campAttendeeSlots) {
        this.campAttendeeSlots = campAttendeeSlots;
    }

    /**
     * sets the total number of committee slots
     * @param campCommitteeSlots
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.campCommitteeSlots = campCommitteeSlots;
    }

    /**
     * this method is used to add the id of camp attendee to the campAttendee list of the camp
     * @param campAttendee
     */
    public void addCampAttendee(String campAttendee){
        this.campAttendeesID.add(campAttendee);
    }

    /**
     * this method removes camp attendee from the campAttendee list of the camp
     * @param campAttendee
     */
    public void removeCampAttendee(String campAttendee){
        this.campAttendeesID.remove(campAttendee);
    }

    /**
     * this method is used to add the id of Camp Committee to the Camp Committee list of the camp
     * @param campCommittee
     */
    public void addCampCommittee(String campCommittee){
        this.campCommitteeMembersID.add(campCommittee);
    }

    /**
     * this method is used to remove committee from the camp
     * @param campCommittee
     */
    public void removeCampCommittee(String campCommittee){
        this.campCommitteeMembersID.remove(campCommittee);
    }

    public String getStaffInChargeID() {
        return staffInChargeID;
    }
    public String getStaffInChargeUserName() throws ModelNotFoundException {
        Staff staff = StaffRepository.getInstance().getByID(this.getStaffInChargeID());
        return staff.getUserName();
    }

    public List<String> getCampAttendeesID() {
        return campAttendeesID;
    }

    public List<String> getCampCommitteeMembersID() {
        return campCommitteeMembersID;
    }

    @Override
    public String getDisplayableString() throws ModelNotFoundException {
        return getCampPreview();
    }

    @Override
    public String getSplitter() {
        return "=======================================================";
    }

    @Override
    public String getID() {
        /**
         * the ID of the Camp is the name of the camp
         */
        return this.campName;
    }

    /**
     *  gets which faculty the camp is open to
     * @return
     */
    public String getFaculty() {
        return this.faculty;
    }

    /**
     *  sets which faculty the camp is open to
     * @param faculty
     */

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }


}
