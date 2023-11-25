package main.model.user;

/**
 * This enum represents the type of the faculty the user is from
 */
public enum Faculty {
    /**
     * The user is from SCSE
     */
    SCSE("SCSE"),
    /**
     * The user is from ADM
     */
    ADM("ADM"),
    /**
     * The user is from EEE
     */
    EEE("EEE"),
    /**
     * The user is from NBS
     */
    NBS("NBS"),
    ALL("ALL");
    private String representation;
    Faculty(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }
}
