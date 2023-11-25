package main.model.enquiry;

import main.boundary.UIEntry;
import main.model.Displayable;
import main.model.Model;
import main.model.camp.Camp;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.repository.camp.CampRepository;
import main.repository.enquiry.EnquiryRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.iocontrol.Mappable;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  Class for Enquiry
 */
public class Enquiry implements Model, Displayable {
    /**
     *  A unique id is given to each enquiry for easy selection by user
     */
    private int enquiryID;
    /**
     *  The name of the camp this enquiry is for
     */
    private String campName;
    /**
     *  The actual enquiry ( question ) submitted by the student
     */
    private String question;
    /**
     *  The answer when answered by staff or committee
     */
    private String answer;
    /**
     *  Tells if the enquiry has been processed or not
     */
    private Boolean isAnswered;
    /**
     *  The ID of the student that asked the question
     */
    private String askedByStudentId;
    /**
     *   The type of the user who answered it --> Staff / Camp Committee
     */
    private String answeredByUserType;
    /**
     *  The ID of the user that answered it
     */
    private String answeredByUserId;

    /**
     *  Creates a new enquiry with map
     *
     * @param map
     */

    public Enquiry(Map<String, String> map){
        fromMap(map);
    }

    /**
     *  This constructor is used when a camp committee/ staff answers an enquiry
     * @param campName
     * @param question
     * @param answer
     * @param askedByStudentId
     * @param answeredByUserType
     * @param answeredByUserId
     */
    public Enquiry(String campName,String question,String answer, String askedByStudentId, String answeredByUserType, String answeredByUserId){
        this.enquiryID = ++UIEntry.enquiryIDTracker;
        this.campName = campName;
        this.question = question;
        this.answer = answer;
        this.isAnswered = Boolean.TRUE;
        this.askedByStudentId = askedByStudentId;
        this.answeredByUserType = answeredByUserType;
        this.answeredByUserId = answeredByUserId;
    }

    /**
     * This constructor is used when a student asks an enquiry
     * @param askedByStudentId
     * @param campName
     * @param question
     */
    public Enquiry(String askedByStudentId, String campName, String question){
        // this constructor is used when the student wants to ask an enquiry
        this.enquiryID = ++UIEntry.enquiryIDTracker;
        this.campName = campName;
        this.question = question;
        this.answer = null;
        this.isAnswered = Boolean.FALSE;
        this.askedByStudentId = askedByStudentId ;
        this.answeredByUserType = null;
        this.answeredByUserId = null;
    }

    /**
     *  Displays the enquiry and reply info when submitted by the staff / camp committee
     */
    public void displayReplyInfo(){
        StringBuilder output = new StringBuilder();
        output.append(BoundaryStrings.separator).append("\n");
        String format = "| %-30s: %-70s |%n";
        String format2 = "| %-30s: %-79s |%n";
        output.append(String.format(format,"Enquiry ID",this.getEnquiryID()));
        output.append(String.format(format,"Camp Name",this.getCampName()));
        output.append(String.format(format,"Enquiry",this.getQuestion()));
        output.append(String.format(format,"Asked By",this.getAskedByStudentId()));
        output.append(String.format(format2,"Status",Colors.green+"Answered"+Colors.reset));
        output.append(BoundaryStrings.separatorThin).append("\n");
        output.append(String.format(format,"Reply",this.getAnswer()));
        output.append(String.format(format,"Replied By (Staff/Camp Com) ",this.getAnsweredByUserType()));
        output.append(String.format(format,"Replied By",this.getAnsweredByUserId()));
        output.append(BoundaryStrings.separator).append("\n");
        System.out.println(output);
    }

    /**
     *  Displays only the enquiry info when submitted by the student
     */
    public void displayEnquiryInfo(){
        StringBuilder output = new StringBuilder();
        output.append(BoundaryStrings.separator).append("\n");
        String format = "| %-30s: %-70s |%n";
        String format2 = "| %-30s: %-79s |%n";
        output.append(String.format(format,"Enquiry ID",this.getEnquiryID()));
        output.append(String.format(format,"Camp Name",this.getCampName()));
        output.append(String.format(format,"Enquiry",this.getQuestion()));
        output.append(String.format(format,"Asked By",this.getAskedByStudentId()));
        if(this.isAnswered){
            output.append(String.format(format2,"Status",Colors.green+"Answered"+Colors.reset));
        }
        else{
            output.append(String.format(format2,"Status",Colors.red+"Unanswered"+Colors.reset));
        }
        output.append(BoundaryStrings.separator).append("\n");
        System.out.println(output);
    }

    /**
     * Gets the enquiry ID
     * @return enquiry ID
     */
    public int getEnquiryID() {
        return enquiryID;
    }

    /**
     * Gets the camp name
     * @return camp Name
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Gets the question (enquiry ) asked by the student
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question ( enquiry )
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the answer ( answer )
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Gets if the enquiry has been processed or answered
     * @return isAnswered
     */
    public Boolean getAnswered() {
        return isAnswered;
    }

    /**
     * Gets the studentId of the student that asked the enquiry
     * @return askedByStudentID
     */
    public String getAskedByStudentId() {
        return askedByStudentId;
    }

    /**
     * Gets the type of the user that answered the question
     * @return answeredByUserType
     */
    public String getAnsweredByUserType() {
        return answeredByUserType;
    }

    /**
     * Gets the id of the user that answered the question
     * @return answeredByUserID
     */
    public String getAnsweredByUserId() {
        return answeredByUserId;
    }

    @Override
    public String getDisplayableString() throws ModelNotFoundException {
        return null;
    }

    @Override
    public String getSplitter() {
        return "=======================================================";
    }

    @Override
    public String getID() {
        return Integer.toString(getEnquiryID());
    }
}
