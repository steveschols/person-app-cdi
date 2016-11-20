package be.stesch.person.web.rs.exception;

import be.stesch.person.common.exception.BusinessException;
import be.stesch.person.common.v1.BusinessExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

import static be.stesch.person.web.rs.common.PersonAppMediaType.BUSINESS_EXCEPTION_V1_JSON;
import static be.stesch.person.web.rs.common.PersonAppMediaType.BUSINESS_EXCEPTION_V1_XML;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.PRECONDITION_FAILED;
import static javax.ws.rs.core.Response.status;
import static org.apache.commons.lang3.StringUtils.lowerCase;

/**
 * Created by u420643 on 11/16/2016.
 */
@Provider
public class ResourceExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceExceptionMapper.class);

    @Context
    private HttpHeaders httpHeaders;

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);

        if (exception instanceof BusinessException) {
            return status(PRECONDITION_FAILED)
                    .entity(mapBusinessException((BusinessException) exception))
                    .type(isJsonExpected() ? BUSINESS_EXCEPTION_V1_JSON : BUSINESS_EXCEPTION_V1_XML)
                    .build();
        }
        return status(INTERNAL_SERVER_ERROR).build();
    }

    private BusinessExceptionType mapBusinessException(BusinessException businessException) {
        return new BusinessExceptionType()
                .withMessage(businessException.getMessage())
                .withCorrelationId(businessException.getCorrelationId());
    }

    private boolean isJsonExpected() {
        if (httpHeaders == null) {
            return false;
        }
        List<MediaType> acceptableMediaTypes = httpHeaders.getAcceptableMediaTypes();
        boolean isJsonExpected = acceptableMediaTypesHaveSuffix(acceptableMediaTypes, "json");
        boolean isXmlExpected = acceptableMediaTypesHaveSuffix(acceptableMediaTypes, "xml");

        return isJsonExpected && !isXmlExpected;
    }

    private boolean acceptableMediaTypesHaveSuffix(List<MediaType> mediaTypes, String suffix) {
        return mediaTypes.stream().anyMatch(mediaType -> lowerCase(mediaType.toString()).endsWith(suffix));
    }

}
