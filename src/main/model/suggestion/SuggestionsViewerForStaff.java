package main.model.suggestion;

import main.model.camp.Camp;
import main.model.user.Staff;
import main.model.user.User;
import main.repository.camp.CampRepository;
import main.utils.exception.ModelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * implements the suggestionsViewerByUser interface for staff to view suggestions
 */
public class SuggestionsViewerForStaff implements SuggestionsViewerByUser{

    /**
     * displays all the non-processed suggestions submitted to the camps that are created by the staff
     * @param staff user that wants to view suggestions
     * @return the id of all the non processed suggestions for easy access
     * @throws ModelNotFoundException
     */
    @Override
    public List<Integer> getNonProcessedByUser(User staff) throws ModelNotFoundException{
        List<Camp> campList = CampRepository.getInstance().findByRules(c-> Objects.equals(c.getStaffInChargeID(), staff.getID()));
        List<Integer> suggestionsIDList = new ArrayList<>();
        for(Camp c: campList){
            suggestionsIDList.addAll(SuggestionsViewerByCamp.getNonProcessedSuggestionsByCamp(c));
        }
        return suggestionsIDList;
    }

    /**
     * displays all the suggestions submitted to the camps that are created by the staff
     * @param staff the staff that wants to view suggestions
     * @return the id of all the suggestions for easy access
     * @throws ModelNotFoundException
     */
    @Override
    public List<Integer> getAllSuggestionsByUser(User staff) throws ModelNotFoundException {
        List<Camp> campList = CampRepository.getInstance().findByRules(c-> Objects.equals(c.getStaffInChargeID(), staff.getID()));
        List<Integer> suggestionsList = new ArrayList<>();
        for(Camp c: campList){
            suggestionsList.addAll(SuggestionsViewerByCamp.getAllSuggestionsByCamp(c));
        }
        return suggestionsList;
    }
}
