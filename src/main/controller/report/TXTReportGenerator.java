package main.controller.report;

import main.model.camp.Camp;
import main.model.enquiry.Enquiry;
import main.model.user.Committee;
import main.model.user.Student;
import main.repository.enquiry.EnquiryRepository;
import main.utils.config.Location;
import main.utils.exception.ModelNotFoundException;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.Colors;
import main.utils.ui.DateStringConverter;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * Class which implements the report generator
 */
public class TXTReportGenerator implements ReportGenerator {
    /**
     * Formatter for camp details
     */

    private final String formatCampDetails = "%-20s %-20s %-30s %-20s %-20s%n";
    /**
     * Formatter for date details
     */
    private final String formatDateDetails = "%-30s %-20s %-20s%n";
    /**
     * formatter for student details
     */
    private final String formatStudentDetails = "%-20s %-20s %-20s%n";
    /**
     * Formatter for performance details
     */
    private final String formatPerformanceDetails = "%-20s %-20s %-20s %-20s%n";
    /**
     * Formatter for eqnuiry details
     */
    private final String formatEnquiryDetails = "%-40s %-40s %-20s %-20s %-20s%n";

    /**
     * A function to generate the enquiry list txt upon giving the camp
     * @param camp --> which camp's attendee list do we want to generate
     */
    @Override
    public void generateEnquiryReport(Camp camp){
        String campName = camp.getID().replace(" ", "_");
        String filepath = Location.RESOURCE_LOCATION + "/data/report/" + campName + "_EnquiryReport.txt";
        try {
            File file = new File(filepath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            writeCampDetails(writer, camp);
            writeEnquiryDetails(writer,camp);
            writer.close();

            System.out.println("The Enquiry Report has been generated. Please navigate to : " + Colors.blue + filepath + Colors.reset);

        } catch (IOException | ModelNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * A function to generate the performance list txt upon giving the camp
     * @param camp --> which camp's attendee list do we want to generate
     */
    @Override
    public void generatePerformanceReport(Camp camp) {
        String campName = camp.getID().replace(" ", "_");
        String filepath = Location.RESOURCE_LOCATION + "/data/report/" + campName + "_PerformanceReport.txt";
        try {
            File file = new File(filepath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            writeCampDetails(writer, camp);
            writePerformanceDetails(writer, camp);
            writer.close();

            System.out.println("The Performance Report has been generated. Please navigate to : " + Colors.blue + filepath + Colors.reset);

        } catch (IOException | ModelNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * A function to generate the attendee list txt upon giving the camp
     * @param camp --> which camp's attendee list do we want to generate
     */
    @Override
    public void generateAttendeeList(Camp camp) {
        String campName = camp.getID().replace(" ", "_");
        String filepath = Location.RESOURCE_LOCATION + "/data/report/" + campName + "_AttendeeList.txt";
        try {
            File file = new File(filepath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

            writeCampDetails(writer, camp);
            writeAttendeeDetails(writer, camp);
            writer.close();

            System.out.println("The List of Attendees has been generated. Please navigate to : " + Colors.blue + filepath + Colors.reset);

        } catch (IOException | ModelNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * A function to generate the committee list txt upon giving the camp
     * @param camp --> which camp's attendee list do we want to generate
     */
    @Override
    public void generateCommitteeList(Camp camp) {
        String campName = camp.getID().replace(" ", "_");
        String filepath = Location.RESOURCE_LOCATION + "/data/report/" + campName + "_CommitteeList.txt";
        try {
            File file = new File(filepath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

            writeCampDetails(writer, camp);
            writeCommitteeDetails(writer, camp);
            writer.close();

            System.out.println("The List of Committee Members has been generated. Please navigate to : " + Colors.blue + filepath + Colors.reset);

        } catch (IOException | ModelNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * A function to generate the attendee + committee list txt upon giving the camp
     * @param camp --> which camp's attendee list do we want to generate
     */
    @Override
    public void generateStudentsList(Camp camp) {
        String campName = camp.getID().replace(" ", "_");
        String filepath = Location.RESOURCE_LOCATION + "/data/report/" + campName + "_StudentList.txt";
        try {
            File file = new File(filepath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

            writeCampDetails(writer, camp);
            writeAttendeeDetails(writer, camp);
            writeCommitteeDetails(writer, camp);
            writer.close();

            System.out.println("The List of Attendees & Committee Members has been generated. Please navigate to : " + Colors.blue + filepath + Colors.reset);

        } catch (IOException | ModelNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    /**
     *  This is a method to write the camp details to the given file
     * @param writer the file to which we want to write
     * @param camp for which camp we want the details
     * @throws IOException when there is an issue writing data to the file
     * @throws ModelNotFoundException when it cant find the camp from the camp repository
     */
    @Override
    public void writeCampDetails(BufferedWriter writer, Camp camp) throws IOException, ModelNotFoundException {
        try {
            writer.write("Camp Details\n");
            writer.write(BoundaryStrings.separatorThin + "\n");
            writer.write(String.format(formatCampDetails, "Camp Name", "Staff-In-Charge", "Description", "Location", "Faculty"));
            writer.write(String.format(formatCampDetails, camp.getCampName(), camp.getStaffInChargeUserName(), camp.getDescription(), camp.getLocation(), camp.getFaculty()));
            writer.write(BoundaryStrings.separator + "\n");
            writer.write("Camp Dates\n" + BoundaryStrings.separatorThin + "\n");
            writer.write(String.format(formatDateDetails, "Registration Closing Date", "Start Date", "End Date"));
            writer.write(String.format(formatDateDetails, DateStringConverter.dateString(camp.getRegistrationClosingDate()), DateStringConverter.dateString(camp.getStartDate()), DateStringConverter.dateString(camp.getEndDate())));
            writer.write(BoundaryStrings.separator + "\n");
        } catch (ModelNotFoundException e) {
            System.out.println("Model not found!");
        }
    }
    /**
     *  This is a method to write the enquiry details to the given file
     * @param writer the file to which we want to write
     * @param camp for which camp we want the details
     * @throws IOException when there is an issue writing data to the file
     * @throws ModelNotFoundException when it cant find the camp from the camp repository
     */
    @Override
    public void writeEnquiryDetails(BufferedWriter writer,Camp camp) throws IOException,ModelNotFoundException{
        writer.write("Enquiry List\n");
        writer.write(BoundaryStrings.separatorThin+"\n");
        writer.write(String.format(formatEnquiryDetails,"Enquiry","Reply","Asked By","Answered By","Staff/Com"));
        List<Enquiry> enquiryList = EnquiryRepository.getInstance().getList();
        for(Enquiry e:enquiryList){
            if(Objects.equals(e.getCampName(), camp.getCampName()))
            {
                writer.write(String.format(formatEnquiryDetails,e.getQuestion(),e.getAnswer(),e.getAskedByStudentId(),e.getAnsweredByUserId(),e.getAnsweredByUserType()));
            }
        }
    }
    /**
     *  This is a method to write the camp attendee details to the given file
     * @param writer the file to which we want to write
     * @param camp  for which camp do we want the details for
     * @throws IOException when there is an issue writing data to the file
     * @throws ModelNotFoundException when it cant find the camp from the camp repository
     */
    @Override
    public void writeAttendeeDetails(BufferedWriter writer, Camp camp) throws IOException,ModelNotFoundException {
        try {
            writer.write("Attendee list\n");
            writer.write(BoundaryStrings.separatorThin + "\n");
            writer.write(String.format(formatStudentDetails, "User ID", "Student Name", "Faculty"));
            List<String> attendeeList = camp.getCampAttendeesID();
            for (String id : attendeeList) {
                if (Objects.equals(id, "")) {
                    continue;
                }
                writer.write(String.format(formatStudentDetails, id, Student.getUserNameByID(id), Student.getFacultyByID(id)));
            }
            writer.write(BoundaryStrings.separator + "\n");
        } catch (ModelNotFoundException | IOException e) {
            System.out.println("Model not found!");
        }
    }

    /**
     *  This is a method to write the camp committee details to the given file
     * @param writer the file to which we want to write
     * @param camp  for which camp do we want the details for
     * @throws IOException when there is an issue writing data to the file
     * @throws ModelNotFoundException when it cant find the camp from the camp repository
     */
    @Override
    public void writeCommitteeDetails(BufferedWriter writer, Camp camp) throws IOException, ModelNotFoundException {
        try {
            writer.write("Committee list\n");
            writer.write(BoundaryStrings.separatorThin + "\n");
            writer.write(String.format(formatStudentDetails, "User ID", "Student Name", "Faculty"));
            List<String> committeeList = camp.getCampCommitteeMembersID();
            for (String id : committeeList) {
                if (Objects.equals(id, "")) {
                    continue;
                }
                writer.write(String.format(formatStudentDetails, id, Student.getUserNameByID(id), Student.getFacultyByID(id)));
            }
            writer.write(BoundaryStrings.separator + "\n");
        } catch (ModelNotFoundException e) {
            System.out.println("Model not found!");
        }
    }

    /**
     *  This is a method to write the performance details to the given file
     * @param writer the file to which we want to write
     * @param camp for which camp we want the details
     * @throws IOException when there is an issue writing data to the file
     * @throws ModelNotFoundException when it cant find the camp from the camp repository
     */
    @Override
    public void writePerformanceDetails(Writer writer, Camp camp) throws IOException, ModelNotFoundException {
        try {
            writer.write("Performance list of Camp Committee Members\n");
            writer.write(BoundaryStrings.separatorThin + "\n");
            writer.write(String.format(formatPerformanceDetails, "User ID", "Comm Member Name", "Points", "Faculty"));
            List<String> committeeList = camp.getCampCommitteeMembersID();
            for (String id : committeeList) {
                if (Objects.equals(id, "")) {
                    continue;
                }
                writer.write(String.format(formatPerformanceDetails, id, Committee.getUserNameByID(id), Committee.getPointsByID(id), Committee.getFacultyByID(id)));
            }
            writer.write(BoundaryStrings.separator + "\n");
        } catch (ModelNotFoundException e) {
            System.out.println("Model not found!");
        }
    }
}


