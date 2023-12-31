package main.controller.account.user;

//import main.model.user.Coordinator;
import main.model.user.Student;
import main.model.user.Staff;
import main.model.user.User;
//import main.repository.user.CoordinatorRepository;
//import main.repository.user.FacultyRepository;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelAlreadyExistsException;

/**
 * The UserAdder class provides a utility for adding users to the database.
 */
public class UserAdder {
    /**
     * Adds the specified user to the database.
     *
     * @param user the user to be added
     * @throws ModelAlreadyExistsException if the user already exists in the database
     */
    public static void addUser(User user) throws ModelAlreadyExistsException {
        if (user instanceof Student student) {
            addStudent(student);
        } else if (user instanceof Staff staff) {
            addStaff(staff);
        } /*else if (user instanceof Coordinator coordinator) {
            addCoordinator(coordinator);
        }*/
    }

    /**
     * Adds the specified coordinator to the database.
     *
     * @param coordinator the coordinator to be added
     * @throws ModelAlreadyExistsException if the coordinator already exists in the database
     */
    /*private static void addCoordinator(Coordinator coordinator) throws ModelAlreadyExistsException {
        CoordinatorRepository.getInstance().add(coordinator);
    }(/

    /**
     * Adds the specified supervisor to the database.
     *
     * @param staff the supervisor to be added
     * @throws ModelAlreadyExistsException if the supervisor already exists in the database
     */
    private static void addStaff(Staff staff) throws ModelAlreadyExistsException {
        StaffRepository.getInstance().add(staff);
    }

    /**
     * Adds the specified student to the database.
     *
     * @param student the student to be added
     * @throws ModelAlreadyExistsException if the student already exists in the database
     */
    private static void addStudent(Student student) throws ModelAlreadyExistsException {
        StudentRepository.getInstance().add(student);
    }
}
