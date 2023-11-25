package main.model.suggestion;

import main.model.camp.Camp;
import main.repository.suggestion.SuggestionRepository;
import main.utils.exception.ModelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * class to view suggestions by camp
 */
public interface SuggestionsViewerByCamp {
    /**
     * displays all the non-processed ( non-approved and non rejecetd ) suggestions by camp
     * @param camp by which camp we want
     * @return the list of ids of all suggestions
     * @throws ModelNotFoundException
     */
    public static List<Integer> getNonProcessedSuggestionsByCamp(Camp camp) throws ModelNotFoundException {
        List<Integer> suggestionsIDList = new ArrayList<>();
        List<Suggestion> suggestionList = SuggestionRepository.getInstance().getList();
        for (Suggestion s : suggestionList) {
            if (Objects.equals(camp.getCampName(), s.getCampName()) && !s.isProcessed()) {
                suggestionsIDList.add(s.getSuggestionID());
                System.out.println(s.getDisplayableString());
            }
        }
        return suggestionsIDList;
    }

    /**
     * gets alll suggestions ( both processed and non-processed)
     * @param camp by which camp we want
     * @return the list of ids of all suggestions
     * @throws ModelNotFoundException
     */
    public static List<Integer> getAllSuggestionsByCamp(Camp camp) throws ModelNotFoundException {
        List<Integer> suggestionsIDList = new ArrayList<>();
        List<Suggestion> suggestionList = SuggestionRepository.getInstance().getList();
        for(Suggestion s: suggestionList){
            if(Objects.equals(camp.getCampName(),s.getCampName())){
                suggestionsIDList.add(s.getSuggestionID());
                System.out.println(s.getDisplayableString());
            }
        }
        return suggestionsIDList;
    }

}
