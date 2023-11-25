package main.model.enquiry;

import main.model.camp.Camp;
import main.model.user.Committee;
import main.model.user.User;
import main.repository.camp.CampRepository;
import main.repository.user.CommitteeRepository;
import main.utils.exception.ModelNotFoundException;
import java.util.List;

/**
 *  Extending the enquiriesViewer exclusively for committee to view their enquiriesS
 */

public class EnquiriesViewerForCommittee extends EnquiryViewer {
    /**
     * Methods for committee to view the enquiries submitted to the camps they oversee
     * @param committee
     * @return List of the ids of the enquiries submitted
     */
    @Override
    public List<Integer> displayEnquiryInfo(User committee) throws ModelNotFoundException {
        Committee committee1= CommitteeRepository.getInstance().getByID(committee.getID());
        String campName = committee1.getRegisteredCampAsCommittee();
        Camp camp = CampRepository.getInstance().getByID(campName);
        return displayEnquiryInfo(camp);
    }
}

