package main.repository.suggestion;

import main.model.suggestion.Suggestion;
import main.repository.Repository;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;
/**
 * The SuggestionsRepository class is a repository for managing the persistence of student objects
 * through file I/O operations.
 * It extends the Repository class, which provides basic CRUD operations for the repository.
 */
public class SuggestionRepository extends Repository<Suggestion> {
    /**
     * The path of the repository file.
     */
    private static final String FILE_PATH = "/data/suggestions/suggestions.txt";
    /**
     * Constructor for creating a new instance of the SuggestionsRepository class.
     */
    SuggestionRepository() {
        super();
        load();
    }
    /**
     * Gets a new instance of the SuggestionsRepository class.
     *
     * @return a new instance of the SuggestionsRepository class
     */
    public static SuggestionRepository getInstance() {
        return new SuggestionRepository();
    }

    /**
     * Gets the path of the repository file.
     *
     * @return the path of the repository file
     */
    @Override
    public String getFilePath() {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Sets the list of mappable objects to a list of Staff objects.
     *
     * @param listOfMappableObjects the list of mappable objects
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Suggestion(map));
        }
    }
}
