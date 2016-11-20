package be.stesch.person.common;

import static java.util.UUID.randomUUID;

/**
 * Created by u420643 on 11/17/2016.
 */
public final class CorrelationId {

    private CorrelationId() {
    }

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setCorrelationId(String correlationId) {
        THREAD_LOCAL.set(correlationId);
    }

    public static String getCorrelationId() {
        return THREAD_LOCAL.get();
    }

    public static String generateAndSetCorrelationId() {
        setCorrelationId(randomUUID().toString());

        return getCorrelationId();
    }

}
