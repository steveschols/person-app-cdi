package be.stesch.person.web.rs.resource;

import be.stesch.person.business.CreatePersonBO;
import be.stesch.person.business.GetPersonBO;
import be.stesch.person.business.UpdatePersonBO;
import be.stesch.person.model.Person;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static be.stesch.person.common.URIFactory.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.status;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Path(PERSON_SERVICES_PATH)
@RequestScoped
public class PersonResource {

    @Inject
    private CreatePersonBO createPersonBO;
    @Inject
    private UpdatePersonBO updatePersonBO;
    @Inject
    private GetPersonBO getPersonBO;

    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    public Response createPerson(Person person) {
        createPersonBO.setPerson(person);
        Person createdPerson = createPersonBO.execute();

        return status(CREATED)
                .contentLocation(createdPerson.getUri())
                .build();
    }

    @PUT
    @Path(PERSON_ID_PATH)
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    public Response updatePerson(@PathParam(PERSON_ID_PATH_PARAM) String personId, Person person) {
        updatePersonBO.setId(personId);
        updatePersonBO.setPerson(person);
        Person updatedPerson = updatePersonBO.execute();

        return status(NO_CONTENT)
                .contentLocation(updatedPerson.getUri())
                .build();
    }

    @GET
    @Path(PERSON_ID_PATH)
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getPerson(@PathParam(PERSON_ID_PATH_PARAM) String personId) {
        getPersonBO.setId(personId);
        Person person = getPersonBO.execute();

        if (person != null) {
            return status(OK)
                    .entity(person)
                    .build();
        }
        return status(NOT_FOUND).build();
    }

}
