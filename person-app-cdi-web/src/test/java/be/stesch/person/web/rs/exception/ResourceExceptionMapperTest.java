package be.stesch.person.web.rs.exception;

import be.stesch.person.common.exception.BusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static be.stesch.person.web.rs.common.PersonAppMediaType.BUSINESS_EXCEPTION_V1_JSON;
import static be.stesch.person.web.rs.common.PersonAppMediaType.BUSINESS_EXCEPTION_V1_XML;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static javax.ws.rs.core.MediaType.valueOf;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.PRECONDITION_FAILED;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by u420643 on 11/16/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceExceptionMapperTest {

    @Mock
    private HttpHeaders httpHeaders;

    @InjectMocks
    private ResourceExceptionMapper resourceExceptionMapper;

    @Test
    public void testMapBusinessException() throws Exception {
        Response response = resourceExceptionMapper.toResponse(new BusinessException("FUBAR"));

        assertThat(response.getStatus(), is(PRECONDITION_FAILED.getStatusCode()));
    }

    @Test
    public void testMapRuntimeException() throws Exception {
        Response response = resourceExceptionMapper.toResponse(new RuntimeException());

        assertThat(response.getStatus(), is(INTERNAL_SERVER_ERROR.getStatusCode()));
    }

    @Test
    public void testMapBusinessExceptionAsJson() throws Exception {
        when(httpHeaders.getAcceptableMediaTypes()).thenReturn(singletonList(valueOf(BUSINESS_EXCEPTION_V1_JSON)));

        Response response = resourceExceptionMapper.toResponse(new BusinessException("FUBAR"));

        verify(httpHeaders).getAcceptableMediaTypes();
        assertThat(response.getMediaType(), is(valueOf(BUSINESS_EXCEPTION_V1_JSON)));
    }

    @Test
    public void testMapBusinessExceptionAsXml() throws Exception {
        when(httpHeaders.getAcceptableMediaTypes()).thenReturn(singletonList(valueOf(BUSINESS_EXCEPTION_V1_XML)));

        Response response = resourceExceptionMapper.toResponse(new BusinessException("FUBAR"));

        verify(httpHeaders).getAcceptableMediaTypes();
        assertThat(response.getMediaType(), is(valueOf(BUSINESS_EXCEPTION_V1_XML)));
    }

    @Test
    public void testMapBusinessExceptionXmlMediaTypeHasPriority() throws Exception {
        when(httpHeaders.getAcceptableMediaTypes())
                .thenReturn(asList(valueOf(BUSINESS_EXCEPTION_V1_JSON), valueOf(BUSINESS_EXCEPTION_V1_XML)));

        Response response = resourceExceptionMapper.toResponse(new BusinessException("FUBAR"));

        verify(httpHeaders).getAcceptableMediaTypes();
        assertThat(response.getMediaType(), is(valueOf(BUSINESS_EXCEPTION_V1_XML)));
    }

}