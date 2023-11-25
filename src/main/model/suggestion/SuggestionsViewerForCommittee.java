package main.model.suggestion;

import main.model.user.User;
import main.repository.suggestion.SuggestionRepository;
import main.utils.exception.ModelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * implements the suggestionsViewerByUser interface for committee to view suggestions
 */
public class SuggestionsViewerForCommittee implements SuggestionsViewerByUser{

    /**
     * gets all the suggestions that the committee member has submitted
     * @param committee the user
     * @return list of the ids of suggestions submitted by the committee
     * @throws ModelNotFoundException
     */
    @Override
    public List<Integer> getAllSuggestionsByUser(User committee) throws ModelNotFoundException {
        List<Suggestion> suggestionsList = SuggestionRepository.getInstance().findByRules(s-> Objects.equals(s.getSuggestedByCampComName(), committee.getUserName()));
        List<Integer> optionsAvailable = new ArrayList<>();
        for(Suggestion s:suggestionsList){
            optionsAvailable.add(s.getSuggestionID());
            System.out.println(s.getDisplayableString());
        }
        if(optionsAvailable.size()==0){
            System.out.println("You haven't submitted any non processed suggestions yet..");
        }
        return optionsAvailable;
    }

    /**
     * gets all the non-processed suggestions that the committee member has submitted
     * @param committee user that wants to view suggestions
     * @return list of the ids of non-processed suggestions submitted by the committee
     * @throws ModelNotFoundException
     */
    @Override
    public List<Integer> getNonProcessedByUser(User committee) throws ModelNotFoundException {
        List<Suggestion> suggestionsList = SuggestionRepository.getInstance().findByRules(s-> !s.isProcessed(),s-> Objects.equals(s.getSuggestedByCampComName(), committee.getUserName()));
        List<Integer> optionsAvailable = new ArrayList<>();
        for(Suggestion s:suggestionsList){
            optionsAvailable.add(s.getSuggestionID());
            System.out.println(s.getDisplayableString());
        }
        if(optionsAvailable.size()==0){
            System.out.println("You haven't submitted any non processed suggestions yet..");
        }
        return optionsAvailable;
    }
}
