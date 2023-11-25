package main.repository.user;

import main.model.user.Committee;
import main.model.user.Staff;
import main.repository.Repository;
import main.utils.config.Location;

import java.util.List;
import java.util.Map;
/**
 * The CommitteeRepository class is a repository for managing the persistence of student objects
 * through file I/O operations.
 * It extends the Repository class, which provides basic CRUD operations for the repository.
 */
public class CommitteeRepository extends Repository<Committee> {
    /**
     * The path of the repository file.
     */
    final private static String FILE_PATH = "/data/user/committee.txt";

    /**
     * Constructor for creating a new instance of the CommitteeRepository class.
     */
    public CommitteeRepository() {
        super();
        load();
    }

    /**
     * Gets a new instance of the CommitteeRepository class.
     *
     * @return a new instance of the CommitteeRepository class
     */
    public static CommitteeRepository getInstance() {
        return new CommitteeRepository();
    }

    /**
     * Gets the path of the repository file.
     *
     * @return the path of the repository file
     */
    @Override
    public String getFilePath() {
        return Location.RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Sets the list of mappable objects to a list of Staff objects.
     *
     * @param listOfMappableObjects the list of mappable objects
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Committee(map));
        }
    }
}
