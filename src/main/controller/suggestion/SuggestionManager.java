package main.controller.suggestion;

import main.model.suggestion.Suggestion;
import main.model.user.Committee;
import main.repository.enquiry.EnquiryRepository;
import main.repository.suggestion.SuggestionRepository;
import main.repository.user.CommitteeRepository;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;

import java.util.Objects;

/**
 * Controls the logic behind every possible interaction wiht the stuggestions
 */
public class SuggestionManager {
    /**
     * Controls the logic behind adding suggestions to the repository
     * @param s suggestion that the committee member wants to submit
     * @throws ModelAlreadyExistsException if a suggestion with the same id already exists in the repository
     */
    public static void submitSuggestion(Suggestion s) throws ModelAlreadyExistsException {
        SuggestionRepository.getInstance().add(s);
        System.out.println("Suggestion has been submitted !");
    }

    /**
     * Controls the logic behind editting suggestions and updating in the repository
     * @param s the suggestion to be editted
     * @throws ModelNotFoundException if the suggestion with that id is not found in the repository
     */
    public static void editSuggestion(Suggestion s) throws ModelNotFoundException {
        SuggestionRepository.getInstance().update(s);
        System.out.println(s.getDisplayableString());
        System.out.println("Suggestion has been edited!");
    }

    /**
     * Controls the logic behind deleting suggestions in the repository
     * @param s the suggestion to be deleted
     * @throws ModelNotFoundException if the suggestion is to be deleted is not found in the repository
     */
    public static void deleteSuggestion(Suggestion s) throws ModelNotFoundException {
        SuggestionRepository.getInstance().remove(s.getID());
    }

    /**
     * Controls the logic behind approving suggestions by the staff
     * @param s the suggestion ot be approved
     * @throws ModelNotFoundException if the suggestion ot be approved is not found in the repository
     */
    public static void approveSuggestion(Suggestion s) throws ModelNotFoundException {
        s.setProcessed(true);
        s.setApproved(true);
        s.setRejected(false);
        SuggestionRepository.getInstance().update(s);
        Committee committee = CommitteeRepository.getInstance().findByRules(com-> Objects.equals(com.getUserName(), s.getSuggestedByCampComName())).get(0);
        committee.setPoints(committee.getPoints()+1);
        CommitteeRepository.getInstance().update(committee);
        System.out.println("Suggestion has been approved!");
    }

    /**
     * controls the logic behind rejecting a suggestion
     * @param s the suggestion to be rejected
     * @throws ModelNotFoundException if the suggestion ot be rejected is not found in the repository
     */
    public static void rejectSuggestion(Suggestion s) throws ModelNotFoundException{
        s.setProcessed(true);
        s.setRejected(true);
        s.setApproved(false);
        SuggestionRepository.getInstance().update(s);
    }

}
