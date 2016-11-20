package be.stesch.person.common.exception;

import be.stesch.person.common.CorrelationId;

/**
 * Created by u420643 on 11/16/2016.
 */
public class BusinessException extends PersonAppException {

    public BusinessException(String message) {
        this(message, CorrelationId.getCorrelationId());
    }

    private BusinessException(String message, String correlationId) {
        super(message, correlationId);
    }

}
