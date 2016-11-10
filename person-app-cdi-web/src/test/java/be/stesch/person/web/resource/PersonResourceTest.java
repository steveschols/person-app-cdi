package be.stesch.person.web.resource;

import be.stesch.person.business.CreatePersonBO;
import be.stesch.person.business.GetPersonBO;
import be.stesch.person.business.UpdatePersonBO;
import be.stesch.person.model.Person;
import be.stesch.person.test.util.JaxbTestUtils;
import be.stesch.person.test.util.JsonTestUtils;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URISyntaxException;

import static be.stesch.person.model.MaritalStatus.SINGLE;
import static javax.servlet.http.HttpServletResponse.*;
import static javax.ws.rs.core.HttpHeaders.CONTENT_LOCATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.hamcrest.Matchers.*;
import static org.jboss.resteasy.mock.MockDispatcherFactory.createDispatcher;
import static org.jboss.resteasy.mock.MockHttpRequest.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonResourceTest {

    @Mock
    private CreatePersonBO createPersonBO;
    @Mock
    private UpdatePersonBO updatePersonBO;
    @Mock
    private GetPersonBO getPersonBO;

    @InjectMocks
    private PersonResource personResource;

    @Test
    public void testCreatePersonJson() throws Exception {
        Person createdPerson = new Person(1L, "John", "Doe", SINGLE);
        testCreatePerson(JsonTestUtils.convert(new Person(null, "John", "Doe", SINGLE)), createdPerson, APPLICATION_JSON);
    }

    @Test
    public void testCreatePersonXml() throws Exception {
        Person createdPerson = new Person(1L, "John", "Doe", SINGLE);
        testCreatePerson(JaxbTestUtils.convert(new Person(null, "John", "Doe", SINGLE)), createdPerson, APPLICATION_XML);
    }

    @Test
    public void testUpdatePersonJson() throws Exception {
        Person updatedPerson = new Person(1L, "John", "Doe", SINGLE);
        testUpdatePerson(JsonTestUtils.convert(new Person(null, "John", "Doe", SINGLE)), updatedPerson, APPLICATION_JSON);
    }

    @Test
    public void testUpdatePersonXml() throws Exception {
        Person updatedPerson = new Person(1L, "John", "Doe", SINGLE);
        testUpdatePerson(JaxbTestUtils.convert(new Person(null, "John", "Doe", SINGLE)), updatedPerson, APPLICATION_XML);
    }

    @Test
    public void testGetPersonJson() throws Exception {
        Person foundPerson = new Person(1L, "John", "Doe", SINGLE);
        testGetPerson(foundPerson, APPLICATION_JSON);
    }

    @Test
    public void testGetPersonXml() throws Exception {
        Person foundPerson = new Person(1L, "John", "Doe", SINGLE);
        testGetPerson(foundPerson, APPLICATION_XML);
    }

    private Dispatcher getDispatcher() {
        Dispatcher dispatcher = createDispatcher();
        dispatcher.getRegistry().addSingletonResource(personResource);

        return dispatcher;
    }

    private void testCreatePerson(byte[] personByteArray, Person createdPerson, String mediaType)
            throws Exception {
        Dispatcher dispatcher = getDispatcher();
        MockHttpRequest request = post("/person-services/persons").contentType(mediaType);
        MockHttpResponse response = new MockHttpResponse();
        request.content(personByteArray);

        when(createPersonBO.execute()).thenReturn(createdPerson);

        dispatcher.invoke(request, response);

        verify(createPersonBO).execute();
        assertThat(response.getStatus(), is(SC_CREATED));
        assertThat(response.getOutputHeaders(), hasKey(CONTENT_LOCATION));
        assertThat(response.getOutputHeaders().get(CONTENT_LOCATION), hasItem(createdPerson.getUri()));
        assertThat(response.getContentAsString(), is(isEmptyString()));
    }

    private void testUpdatePerson(byte[] personByteArray, Person updatedPerson, String mediaType)
            throws Exception {
        Dispatcher dispatcher = getDispatcher();
        MockHttpRequest request = put("/person-services/persons/1").contentType(mediaType);
        MockHttpResponse response = new MockHttpResponse();
        request.content(personByteArray);

        when(updatePersonBO.execute()).thenReturn(updatedPerson);

        dispatcher.invoke(request, response);

        verify(updatePersonBO).execute();
        assertThat(response.getStatus(), is(SC_NO_CONTENT));
        assertThat(response.getOutputHeaders(), hasKey(CONTENT_LOCATION));
        assertThat(response.getOutputHeaders().get(CONTENT_LOCATION), hasItem(updatedPerson.getUri()));
        assertThat(response.getContentAsString(), is(isEmptyString()));
    }

    private void testGetPerson(Person person, String mediaType) throws URISyntaxException {
        Dispatcher dispatcher = getDispatcher();
        MockHttpRequest request = get("/person-services/persons/1").accept(mediaType);
        MockHttpResponse response = new MockHttpResponse();

        when(getPersonBO.execute()).thenReturn(person);

        dispatcher.invoke(request, response);

        verify(getPersonBO).execute();
        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getContentAsString(), containsString(person.getFirstName()));
        assertThat(response.getContentAsString(), containsString(person.getLastName()));
        assertThat(response.getContentAsString(), containsString(person.getMaritalStatus().toString()));
        assertThat(response.getOutputHeaders(), not(hasKey(CONTENT_LOCATION)));
    }

}