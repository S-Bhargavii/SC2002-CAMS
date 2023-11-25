package main.model.enquiry;

import main.model.camp.Camp;
import main.model.user.User;
import main.repository.camp.CampRepository;
import main.repository.enquiry.EnquiryRepository;
import main.utils.exception.ModelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  Extending the enquiriesViewer exclusively for staff to view their enquiries
 */
public class EnquiriesViewerForStaff extends EnquiryViewer{
    /**
     * Methods for staff to view the enquiries submitted to the camps they created
     * @param staff
     * @return List of the ids of the enquiries submitted
     */
    public List<Integer> displayEnquiryInfo(User staff) {
        List<Camp> campList = CampRepository.getInstance().findByRules(c-> Objects.equals(c.getStaffInChargeID(), staff.getID()));
        List<String> campListNames = new ArrayList<>();
        List<Integer> enquiryIDList = new ArrayList<>();
        for(Camp c:campList){
            campListNames.add(c.getCampName());
        }
        List<Enquiry> enquiryList = EnquiryRepository.getInstance().getList();
        for(Enquiry e : enquiryList){
            if(campListNames.contains(e.getCampName())){
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
}
