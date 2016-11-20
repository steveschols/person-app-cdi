package be.stesch.person.web.rs.resource;

import be.stesch.person.adapter.PersonAdapter;
import be.stesch.person.common.exception.BusinessException;
import be.stesch.person.person.v1.PersonType;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static be.stesch.person.common.web.PersonAppURIFactory.*;
import static be.stesch.person.web.rs.common.PersonAppMediaType.PERSON_V1_JSON;
import static be.stesch.person.web.rs.common.PersonAppMediaType.PERSON_V1_XML;
import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.status;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Path(PERSON_SERVICES_PATH)
@RequestScoped
public class PersonResource {

    @Inject
    private PersonAdapter personAdapter;

    @POST
    @Consumes({PERSON_V1_JSON, PERSON_V1_XML})
    public Response createPerson(PersonType personType) throws BusinessException {
        Long personId = personAdapter.createPerson(personType);

        return status(CREATED)
                .contentLocation(getPersonUri(personId))
                .build();
    }

    @PUT
    @Path(PERSON_ID_PATH)
    @Consumes({PERSON_V1_JSON, PERSON_V1_XML})
    public Response updatePerson(@PathParam(PERSON_ID_PATH_PARAM) Long personId, PersonType personType)
            throws BusinessException {
        personId = personAdapter.updatePerson(personId, personType);

        return status(NO_CONTENT)
                .contentLocation(getPersonUri(personId))
                .build();
    }

    @GET
    @Path(PERSON_ID_PATH)
    @Produces({PERSON_V1_JSON, PERSON_V1_XML})
    public Response getPerson(@PathParam(PERSON_ID_PATH_PARAM) Long personId) throws BusinessException {
        PersonType personType = personAdapter.getPerson(personId);

        if (personType != null) {
            return status(OK)
                    .entity(personType)
                    .build();
        }
        return status(NOT_FOUND).build();
    }

}
