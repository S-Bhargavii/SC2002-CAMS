package main.controller.enquiry;

import main.model.user.User;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;

/**
 * interface
 */
public interface ReplyEnquiry {
    /**
     * An abstract method which is to be implemented by the inherited classes
     * @param user the user replying to the enquiry
     * @throws ModelNotFoundException if the enquiry that the user is answering is not found in the enquiry repository
     * @throws ModelAlreadyExistsException if when the user answers to an enquiry, then a new enquiry object is created, if this already exists in the enquiry repository, then an error is thrown
     */
    public void replyEnquiry(User user) throws ModelNotFoundException, ModelAlreadyExistsException;
}
