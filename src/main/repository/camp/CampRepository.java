package main.repository.camp;

import main.model.camp.Camp;
import main.model.user.Student;
import main.repository.Repository;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * The CampRepository class is a repository for managing the persistence of student objects
 * through file I/O operations.
 * It extends the Repository class, which provides basic CRUD operations for the repository.
 */
public class CampRepository extends Repository<Camp> {

    /**
     * The path of the repository file.
     */
    private static final String FILE_PATH = "/data/camp/camps.txt";

    /**
     * Constructor for the CampRepository class.
     */
    CampRepository() {
        super();
        load();
    }

    /**
     * Gets a new instance of the CampRepository class.
     *
     * @return a new instance of the CampRepository class
     */
    public static CampRepository getInstance() {
        return new CampRepository();
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
     * Sets the list of mappable objects.
     *
     * @param listOfMappableObjects the list of mappable objects
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Camp(map));
        }
    }
}

