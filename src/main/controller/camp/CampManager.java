package main.controller.camp;
import main.model.camp.Camp;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.config.Location;
import main.utils.exception.ModelAlreadyExistsException;
import main.utils.exception.ModelNotFoundException;
import main.utils.iocontrol.CSVReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This the Camp manager class
 */
public class CampManager {
    /**
     * This method controls the logic behind creating a camp
     * @param c the camp object that is being created
     * @throws ModelAlreadyExistsException is thrown when the camp already exists in the camp repository
     */
    public static void createCamp(Camp c) throws ModelAlreadyExistsException {
        try{
            CampRepository.getInstance().add(c);
        }catch (ModelAlreadyExistsException e){
            throw new ModelAlreadyExistsException();
        }
    }

    /**
     * This method controls the logic behind deleting a camp
     * @param c the camp that is being deleted
     * @throws ModelNotFoundException is thrown when the camp is not found in the camp repository
     */
    public static void deleteCamp(Camp c) throws ModelNotFoundException {
        CampRepository.getInstance().remove(c.getID());
        System.out.println("Camp Deleted !");
    }

    /**
     * This method controls the logic behind editting a camp
     * @param c the camp that is being edited
     * @throws ModelNotFoundException is thrown if the camp is not found in the camp repository
     */
    public static void editCamp(Camp c) throws ModelNotFoundException {
        CampRepository.getInstance().update(c);
        System.out.println("Here is the updated camp information!");
        System.out.println(c.getDisplayableString());
    }

    /**
     * This method is used to load camps from the camps.csv file
     */
    public static void loadCamps() {
        List<List<String>> campList = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/camps.csv", true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (List<String> row : campList) {
            String campName = row.get(0);
            LocalDate startDate = LocalDate.parse(row.get(1),formatter);
            LocalDate endDate = LocalDate.parse(row.get(2),formatter);
            LocalDate registrationClosingDate = LocalDate.parse(row.get(3),formatter);
            Boolean visibility = Boolean.parseBoolean(row.get(4));
            String faculty = row.get(5);
            String location = row.get(6);
            int totalSlots = Integer.parseInt(row.get(7));
            int campCommitteeSlots = Integer.parseInt(row.get(8));
            String description = row.get(9);
            String staffInCharge = row.get(10);
            try {
                Camp c = new Camp(campName,startDate,endDate,registrationClosingDate,visibility,faculty,location,totalSlots,campCommitteeSlots, description,staffInCharge);
                createCamp(c);
            } catch (ModelAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *  checks if the camp repository is empty and loads information from the csv file to the camp repository text file
     * @return if the camp repository is empty
     */
    public static boolean repositoryIsEmpty() {
        return CampRepository.getInstance().isEmpty();
    }
}
