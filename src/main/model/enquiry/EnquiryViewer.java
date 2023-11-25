package main.model.enquiry;

import main.model.camp.Camp;
import main.model.user.Student;
import main.model.user.User;
import main.repository.enquiry.EnquiryRepository;
import main.utils.exception.ModelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class for viewing enquiries
 */
public abstract class EnquiryViewer {
    /**
     * Displaying the list of enquiries based on the camp name
     * @param camp
     * @return list of the ids of the enquiries
     */
    public static List<Integer> displayEnquiryInfo(Camp camp){
        List<Integer> enquiryIDList = new ArrayList<>();
        List<Enquiry> enquiryList = EnquiryRepository.getInstance().getList();
        for(Enquiry e : enquiryList){
            if(Objects.equals(camp.getCampName(), e.getCampName())){
                if(!enquiryIDList.contains(e.getEnquiryID())){
                    enquiryIDList.add(e.getEnquiryID());
                }
                if(!e.getAnswered()){
                    e.displayEnquiryInfo();
                }
                else{
                    e.displayReplyInfo();
                }
            }
        }
        return enquiryIDList;
    }

    /**
     * Abstract class to be implemented by classes extending the EnquiryViewer abstract class
     * @param user
     * @return list of the ids of the enquiries
     * @throws ModelNotFoundException
     */
    public abstract List<Integer> displayEnquiryInfo(User user) throws ModelNotFoundException;
}
