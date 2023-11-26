package main.boundary.welcome;

import main.repository.camp.CampRepository;
import main.repository.enquiry.EnquiryRepository;
import main.repository.suggestion.SuggestionRepository;
import main.repository.user.CommitteeRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;

public class HardReload {
    /**
     * Clears the data from all repositories and reloads the main class.
     * This is used for testing the whole application.
     */
    public static void reload() {
        StudentRepository.getInstance().clear();
        StaffRepository.getInstance().clear();
        CommitteeRepository.getInstance().clear();
        CampRepository.getInstance().clear();
        EnquiryRepository.getInstance().clear();
        SuggestionRepository.getInstance().clear();
//        Main.main(null);
    }
}
