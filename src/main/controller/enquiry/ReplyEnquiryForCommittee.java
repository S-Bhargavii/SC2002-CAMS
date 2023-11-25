package main.controller.enquiry;

import main.model.enquiry.EnquiriesViewerForCommittee;
import main.model.enquiry.Enquiry;
import main.model.enquiry.EnquiryViewer;
import main.model.user.Committee;
import main.model.user.User;
import main.repository.enquiry.EnquiryRepository;
import main.repository.user.CommitteeRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.ui.AttributeGetter;

import java.util.List;
import java.util.Scanner;

public class ReplyEnquiryForCommittee implements ReplyEnquiry{
    /**
     * A  method which is to be implemented by the inherited classes
     * @param user the user replying to the enquiry
     * @throws ModelNotFoundException if the enquiry that the user is answering is not found in the enquiry repository
     * @throws ModelAlreadyExistsException if when the user answers to an enquiry, then a new enquiry object is created, if this already exists in the enquiry repository, then an error is thrown
     */
    @Override
    public void replyEnquiry(User user) throws ModelNotFoundException, ModelAlreadyExistsException {
        Committee committee = CommitteeRepository.getInstance().getByID(user.getID());
        EnquiryViewer enquiryViewerForCommittee = new EnquiriesViewerForCommittee();
        List<Integer> optionsAvailable = enquiryViewerForCommittee.displayEnquiryInfo(committee);
        if(optionsAvailable.size()!=0){
            System.out.println("Select An Enquiry To Answer ");
            System.out.println("Please Enter the Enquiry ID ( the number on the top row ) of the Enquiry");
            int choice = AttributeGetter.readInt();
            while(!optionsAvailable.contains(choice)){
                System.out.println("Please only select from the options given above...");
                System.out.println("Please Enter the Enquiry ID ( the number on the top row ) of the Enquiry");
                choice = AttributeGetter.readInt();
            }
            Enquiry q = EnquiryRepository.getInstance().getByID(Integer.toString(choice));
            q.displayEnquiryInfo();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your reply");
            String reply = scanner.nextLine();
            Enquiry e = new Enquiry(q.getCampName(),q.getQuestion(),reply,q.getAskedByStudentId(),"Committee",committee.getUserName());
            e.displayReplyInfo();
            if(!q.getAnswered()){
                EnquiryManager.deleteEnquiry(q);
            }
            EnquiryManager.answerToEnquiry(e);
            committee.setPoints(committee.getPoints()+1);
            CommitteeRepository.getInstance().update(committee);
        }
    }
}
