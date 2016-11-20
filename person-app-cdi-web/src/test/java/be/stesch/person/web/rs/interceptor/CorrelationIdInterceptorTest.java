package be.stesch.person.web.rs.interceptor;

import be.stesch.person.common.CorrelationId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedMap;

import static be.stesch.person.common.CorrelationId.*;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Created by u420643 on 11/17/2016.
 */
//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(CorrelationId.class)
public class CorrelationIdInterceptorTest {

    @Mock
    private ContainerRequestContext containerRequestContext;
    @Mock
    private ContainerResponseContext containerResponseContext;
    @Mock
    private MultivaluedMap<String, Object> responseHeaders;

    private CorrelationIdInterceptor correlationIdInterceptor = new CorrelationIdInterceptor();

    @Test
    public void testRequestDoesNotContainCorrelationId() throws Exception {
        mockStatic(CorrelationId.class);

        correlationIdInterceptor.filter(containerRequestContext);

        verifyStatic();
        generateAndSetCorrelationId();
    }

    @Test
    public void testRequestAlreadyContainsCorrelationId() throws Exception {
        String correlationId = randomUUID().toString();

        mockStatic(CorrelationId.class);
        when(containerRequestContext.getHeaderString(CORRELATION_ID_HEADER)).thenReturn(correlationId);

        correlationIdInterceptor.filter(containerRequestContext);

        verifyStatic();
        setCorrelationId(correlationId);
    }

    @Test
    public void testResponseDoesNotContainCorrelationId() throws Exception {
        String correlationId = randomUUID().toString();

        mockStatic(CorrelationId.class);
        when(getCorrelationId()).thenReturn(correlationId);
        when(containerResponseContext.getHeaders()).thenReturn(responseHeaders);

        correlationIdInterceptor.filter(containerRequestContext, containerResponseContext);

        verifyStatic();
        getCorrelationId();
        verify(responseHeaders).add(CORRELATION_ID_HEADER, correlationId);
    }

    @Test
    public void testResponseAlreadyContainsCorrelationId() throws Exception {
        String correlationId = randomUUID().toString();

        mockStatic(CorrelationId.class);
        when(containerResponseContext.getHeaderString(CORRELATION_ID_HEADER)).thenReturn(correlationId);

        correlationIdInterceptor.filter(containerRequestContext, containerResponseContext);

        verifyStatic(never());
        getCorrelationId();
        verify(responseHeaders, never()).add(CORRELATION_ID_HEADER, correlationId);
    }

}