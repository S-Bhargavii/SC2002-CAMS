package main.repository.user;

import main.model.user.Staff;
import main.repository.Repository;
import main.utils.config.Location;

import java.util.List;
import java.util.Map;

/**
 * The StaffRepository class is a repository for storing and managing Staff objects in a file
 * through file I/O operations.
 * It extends the Repository class, which provides basic CRUD operations for the repository.
 */
public class StaffRepository extends Repository<Staff> {

    /**
     * The path of the repository file.
     */
    final private static String FILE_PATH = "/data/user/staff.txt";

    /**
     * Constructor for creating a new instance of the StaffRepository class.
     */
    public StaffRepository() {
        super();
        load();
    }

    /**
     * Gets a new instance of the StaffRepository class.
     *
     * @return a new instance of the StaffRepository class
     */
    public static StaffRepository getInstance() {
        return new StaffRepository();
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
            getAll().add(new Staff(map));
        }
    }
}
