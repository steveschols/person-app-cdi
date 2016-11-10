package be.stesch.person.web.resource;

import be.stesch.person.business.CreatePersonBO;
import be.stesch.person.business.GetPersonBO;
import be.stesch.person.model.Person;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.status;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Path("person-services/persons")
public class PersonResource {

    @Inject
    private CreatePersonBO createPersonBO;
    @Inject
    private GetPersonBO getPersonBO;

    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    public Response createPerson(Person person) {
        createPersonBO.setPerson(person);
        createPersonBO.execute();

        return status(CREATED).build();
    }

    @GET
    @Path("{personId}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Person getPerson(@PathParam("personId") String personId) {
        getPersonBO.setId(personId);

        return getPersonBO.execute();
    }

}
