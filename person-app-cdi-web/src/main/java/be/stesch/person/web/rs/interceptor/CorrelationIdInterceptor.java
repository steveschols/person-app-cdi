package be.stesch.person.web.rs.interceptor;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static be.stesch.person.common.CorrelationId.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by u420643 on 11/17/2016.
 */
@Provider
public class CorrelationIdInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String correlationId = requestContext.getHeaderString(CORRELATION_ID_HEADER);
        if (isNotBlank(correlationId)) {
            setCorrelationId(correlationId);
        } else {
            generateAndSetCorrelationId();
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (isNotBlank(responseContext.getHeaderString(CORRELATION_ID_HEADER))) {
            return;
        }

        String correlationId = getCorrelationId();
        if (isNotBlank(correlationId)) {
            responseContext.getHeaders().add(CORRELATION_ID_HEADER, correlationId);
        }
    }

}
