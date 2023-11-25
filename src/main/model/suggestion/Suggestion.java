package main.model.suggestion;

import main.boundary.UIEntry;
import main.model.Displayable;
import main.model.Model;
import main.model.camp.Camp;
import main.model.enquiry.Enquiry;
import main.model.user.Committee;
import main.model.user.Staff;
import main.repository.camp.CampRepository;
import main.repository.suggestion.SuggestionRepository;
import main.utils.exception.ModelNotFoundException;
import main.utils.ui.BoundaryStrings;
import main.utils.ui.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * class for suggestions
 */
public class Suggestion implements Model, Displayable {
    /**
     *  the id of the suggestion for easy tracking and access
     */
    private int suggestionID;
    /**
     *  which camp does this suggestion belong to
     */
    private String campName;
    /**
     *  the name of the committee member this suggestion submitted by
     */
    private String suggestedByCampComName;
    /**
     *  the suggestion
     */
    private String suggestion;
    /**
     * whether it is approved by staff
     */
    private boolean isApproved;
    /**
     * whether it is rejected by staff
     */
    private boolean isRejected;
    /**
     * whether it has been processed, i.e approved or rejected by staff
     *
     */
    private boolean isProcessed;

    /**
     * Constructor for suggestion
     * @param campName name of the camp the suggestion is being submitted to
     * @param suggestion the suggestion
     * @param suggestionByComName the name of the camp committee member
     */
    public Suggestion(String campName, String suggestion,String suggestionByComName){
        UIEntry.suggestionsIDTracker++;
        this.suggestionID = UIEntry.suggestionsIDTracker;
        this.campName = campName;
        this.suggestedByCampComName = suggestionByComName;
        this.suggestion = suggestion;
        this.isApproved = false;
        this.isRejected = false;
        this.isProcessed = false;
    }

    /**
     * gets the preview of the suggestion to display
     * @return
     */
    public String getPreview(){
        StringBuilder output = new StringBuilder();
        output.append(BoundaryStrings.separator).append("\n");
        String format = "| %-30s: %-70s |%n";
        String format2 = "| %-30s: %-79s |%n";
        output.append(String.format(format,"Suggestion ID",this.suggestionID));
        output.append(String.format(format,"Camp Name",this.campName));
        output.append(String.format(format,"New Description Suggestion",this.suggestion));
        output.append(String.format(format,"Suggested By",this.suggestedByCampComName));
        if(isProcessed){
            if(isRejected){
                output.append(String.format(format2,"Status", Colors.red +"REJECTED"+Colors.reset));
            }
            else{
                output.append(String.format(format2,"Status", Colors.green +"APPROVED"+Colors.reset));
            }
        }
        else{
            output.append(String.format(format2,"Status", Colors.blue +"HAS NOT BEEN PROCESSED YET"+Colors.reset));
        }
        output.append(BoundaryStrings.separator).append("\n");
        return output.toString();
    }

    /**
     * getter for suggestion id
     * @return suggestion id
     */
    public int getSuggestionID() {
        return suggestionID;
    }

    /**
     * getter for the camp name of the suggestion
     * @return camp name
     */
    public String getCampName() {
        return campName;
    }

    /**
     * getter for the name of the camp committee member that suggested it
     * @return camp com name
     */
    public String getSuggestedByCampComName() {
        return suggestedByCampComName;
    }

    /**
     * setter for suggestion --> this is used for updating suggestions
     * @param suggestion
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * setter for isapproved
     * @param approved
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    /**
     * setter for isrejected
     * @param rejected
     */
    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    /**
     * getter for is processed
     * @return isprocessed
     */
    public boolean isProcessed() {
        return isProcessed;
    }

    /**
     * setter for processed
     * @param processed
     */
    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    /**
     * setter to create object when mapping from map
     * @param map
     */
    public Suggestion(Map<String, String> map){
        fromMap(map);
    }

    @Override
    public String getID() {
        return Integer.toString(suggestionID);
    }

    @Override
    public String getDisplayableString() throws ModelNotFoundException {
        return getPreview();
    }

    @Override
    public String getSplitter() {
        return BoundaryStrings.separator;
    }
}
