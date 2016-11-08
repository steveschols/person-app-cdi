package be.stesch.person.model;

/**
 * @author Steve Schols
 * @since 1/09/2015
 */
public enum MaritalStatus {

    SINGLE("single"), MARRIED("married");

    private final String label;

    MaritalStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
