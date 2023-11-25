package main.controller.report;

import main.model.camp.Camp;
import main.utils.exception.ModelNotFoundException;

import java.io.BufferedWriter;
import java.io.IOException;
/**
 * Interface for generating an attendee list
 * --> this acts as a contract for any other class implementing in this future
 */
public interface StudentListGenerator {
    /**
     * A function to generate the attendee + committee list txt upon giving the camp
     * @param camp --> which camp's attendee list do we want to generate
     */
    public void generateStudentsList(Camp camp);
    /**
     *  This is a method to write the camp committee details to the given file
     * @param writer the file to which we want to write
     * @param camp  for which camp do we want the details for
     * @throws IOException when there is an issue writing data to the file
     * @throws ModelNotFoundException when it cant find the camp from the camp repository
     */
    public void writeCommitteeDetails(BufferedWriter writer, Camp camp) throws IOException, ModelNotFoundException;
    /**
     *  This is a method to write the camp attendee details to the given file
     * @param writer the file to which we want to write
     * @param camp  for which camp do we want the details for
     * @throws IOException when there is an issue writing data to the file
     * @throws ModelNotFoundException when it cant find the camp from the camp repository
     */
    public void writeAttendeeDetails(BufferedWriter writer, Camp camp) throws IOException, ModelNotFoundException;

    /**
     *  This is a method to write the camp details to the given file
     * @param writer the file to which we want to write
     * @param camp for which camp we want the details
     * @throws IOException when there is an issue writing data to the file
     * @throws ModelNotFoundException when it cant find the camp from the camp repository
     */
    public void writeCampDetails(BufferedWriter writer, Camp camp) throws IOException, ModelNotFoundException;

}
