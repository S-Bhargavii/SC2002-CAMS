package main.model.user;

import main.boundary.mainpage.CommitteeMainPage;
import main.model.camp.Camp;
import main.repository.camp.CampRepository;
import main.repository.user.CommitteeRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.iocontrol.Mappable;
import main.utils.parameters.EmptyID;
import main.utils.ui.BoundaryStrings;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

/**
 * This class represents a committee, which is a type of student.
 * It extends the User class and includes a student ID field.
 */
public class Committee extends Student implements User,Mappable{
    /**
     * The number of points the student owns
     */
    private int points;
    /**
     * The student ID
     */
    private String studentID;
    /**
     * The email of a student
     */
    public Committee(Map<String, String> informationMap) {
        fromMap(informationMap);
    }

    public Committee(Student s){
        super(s);
        this.studentID = s.getStudentID();
        this.points = 0;
    }


    /**
     * Gets the id of the user
     *
     * @return the id of the user
     */
    @Override
    public String getID() {
        return this.studentID;
    }

    /**
     * Gets the number of points that camp committee owns
     * @param id the id of the camp committee
     * @return the number of points owned
     * @throws ModelNotFoundException if the committee is not found in the committee repository
     */
    public static int getPointsByID(String id) throws ModelNotFoundException {
        Committee committee = CommitteeRepository.getInstance().getByID(id);
        return committee.getPoints();
    }

    /**
     *  getting the committee profile
     * @return the string for committee profile
     */
    @Override
    public String getStudentProfile(){
        StringBuilder output = new StringBuilder();
        output.append(BoundaryStrings.separator).append("\n");
        String format = "| %-50s: %-50s |%n";
        output.append(String.format(format,"Student Name",this.getUserName()));
        output.append(String.format(format,"Student Faculty",this.getFaculty()));
        output.append(String.format(format,"Camp Committee Member","YES"));
        output.append(String.format(format,"Total Points Recieved",this.getPoints()));
        output.append(String.format(format,"Registered Camps as Camp Committee",this.getRegisteredCampAsCommittee()));
        output.append(String.format(format,"Registered Camps as Camp Attendee",this.getRegisteredCampsAsAttendee().toString()));
        output.append(BoundaryStrings.separator).append("\n");
        return output.toString();
    }

    /**
     * Gets the number of points that the committee has
     * @return the number of points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the number of points
     * Used to increase points everytime a suggestion is approved and enquiry is answered
     * @param points the points the committee member owns
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
