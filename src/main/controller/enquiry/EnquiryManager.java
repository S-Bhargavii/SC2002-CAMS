package main.controller.enquiry;

import main.model.enquiry.Enquiry;
import main.repository.enquiry.EnquiryRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;

public class EnquiryManager {
    /**
     * It controls the logic behind ( updates the enquiry repository) when the student submits an enquiry
     * @param enquiry the enquiry that is being asked
     * @throws ModelAlreadyExistsException if an enquiry with the same id exists in the enquiry repository
     */
    public static void askEnquiry(Enquiry enquiry) throws ModelAlreadyExistsException {
        enquiry.displayEnquiryInfo();
        EnquiryRepository.getInstance().add(enquiry);
        System.out.println("Enquiry Asked!");
    }

    /**
     * It controls the logic behind (updates the enquiry repository ) when the staff or committee replies to an enquiry
     * @param enquiry a new enquiry object is created when answered
     * @throws ModelAlreadyExistsException if an enquiry with the same id exists, then an error is thrown
     */
    public static void answerToEnquiry(Enquiry enquiry) throws ModelAlreadyExistsException{
        EnquiryRepository.getInstance().add(enquiry);
        System.out.println("Enquiry Answered!");
    }

    /**
     * It controls the logic behind (updates the enquiry repository) when the student edits the enquiry before processing
     * @param enquiry the enquiry object that is being edited
     * @throws ModelNotFoundException if the enquiry that is being edited is not found in the enquiry repository
     */
    public static void editEnquiry(Enquiry enquiry) throws ModelNotFoundException{
        EnquiryRepository.getInstance().update(enquiry);
        enquiry.displayEnquiryInfo();
        System.out.println("Enquiry Edited!");
    }

    /**
     * It controls the logic behind (updates the enquiry repository ) when the student deletes an enquiry before processing
     * @param enquiry the enquiry object that is to be deleted
     * @throws ModelNotFoundException if this enquiry object is not found in the enquiry repository
     */
    public static void deleteEnquiry(Enquiry enquiry) throws ModelNotFoundException {
        EnquiryRepository.getInstance().remove(enquiry.getID());
    }
}
