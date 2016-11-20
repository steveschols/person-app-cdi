package be.stesch.person.common.exception;

/**
 * Created by u420643 on 11/17/2016.
 */
public class PersonAppException extends RuntimeException {

    private String correlationId;

    PersonAppException(String message, String correlationId) {
        super(message);
        this.correlationId = correlationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public String toString() {
        return "PersonAppException{" +
                "correlationId='" + correlationId + '\'' +
                "}\n" + super.toString();
    }

}
