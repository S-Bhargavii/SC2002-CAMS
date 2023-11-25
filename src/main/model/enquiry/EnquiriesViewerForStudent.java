package main.model.enquiry;

import main.model.user.Student;
import main.model.user.User;
import main.repository.enquiry.EnquiryRepository;
import main.utils.exception.ModelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  Extending the enquiriesViewer exclusively for student to view their enquiries
 */
public class EnquiriesViewerForStudent extends EnquiryViewer{
    /**
     * Method for student to view the enquiries submitted to various camps
     * @param student
     * @return List of the ids of the enquiries submitted
     */
    @Override
    public List<Integer> displayEnquiryInfo(User student) throws ModelNotFoundException {
        List<Enquiry> enquiryList = EnquiryRepository.getInstance().findByRules(e-> Objects.equals(e.getAskedByStudentId(), student.getUserName()));
        List<Integer> enquiryIDList = new ArrayList<>();
        for(Enquiry e : enquiryList){
            enquiryIDList.add(e.getEnquiryID());
        }
        for(Integer id: enquiryIDList){
            Enquiry e = EnquiryRepository.getInstance().getByID(Integer.toString(id));
            if(!e.getAnswered()){
                e.displayEnquiryInfo();
            }
            else{
                e.displayReplyInfo();
            }
        }
        return enquiryIDList;
    }
}
