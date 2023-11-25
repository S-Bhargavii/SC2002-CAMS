package main.model.suggestion;

import main.model.user.User;
import main.utils.exception.ModelNotFoundException;

import java.util.List;

/**
 * class that allows users to view suggestions
 * by users here --> staff, committee
 */
public interface SuggestionsViewerByUser extends SuggestionsViewerByCamp{
    /**
     * gets all the suggestions by user
     * @param user the user
     * @return a list of ids of suggestions
     * @throws ModelNotFoundException
     */
    public abstract List<Integer> getAllSuggestionsByUser(User user) throws ModelNotFoundException;

    /**
     * gets all the non processed suggestions by user
     * @param user user that wants to view suggestions
     * @return
     * @throws ModelNotFoundException
     */
    public abstract List<Integer> getNonProcessedByUser(User user) throws ModelNotFoundException;
}
