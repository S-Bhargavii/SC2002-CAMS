package main.repository.enquiry;


import main.model.enquiry.Enquiry;
import main.repository.Repository;
import java.util.List;
import java.util.Map;
import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * The EnquiryRepository class is a repository for managing the persistence of student objects
 * through file I/O operations.
 * It extends the Repository class, which provides basic CRUD operations for the repository.
 */
public class EnquiryRepository extends Repository<Enquiry> {
    /**
     * The path of the repository file.
     */
    private static final String FILE_PATH = "/data/enquiry/enquiries.txt";
    /**
     * Constructor for creating a new instance of the Enquiry Repository class.
     */
    EnquiryRepository() {
        super();
        load();
    }
    /**
     * Gets a new instance of the EnquiryRepository class.
     *
     * @return a new instance of the EnquiryRepository class
     */
    public static EnquiryRepository getInstance() {
        return new EnquiryRepository();
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
            getAll().add(new Enquiry(map));
        }
    }

}
