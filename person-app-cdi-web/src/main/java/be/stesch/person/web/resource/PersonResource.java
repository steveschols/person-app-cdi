package be.stesch.person.web.resource;

import be.stesch.person.business.GetPersonBO;
import be.stesch.person.model.Person;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Path("person-services/persons")
public class PersonResource {

    @Inject
    private GetPersonBO getPersonBO;

    @GET
    @Path("{personId}")
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Person getPerson(@PathParam("personId") String personId) {
        getPersonBO.setId(personId);

        return getPersonBO.execute();
    }

}
