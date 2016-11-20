package be.stesch.person.web.rs.resource;

import be.stesch.person.adapter.PersonAdapter;
import be.stesch.person.common.web.PersonAppURIFactory;
import be.stesch.person.person.v1.PersonType;
import be.stesch.person.test.util.JaxbTestUtils;
import be.stesch.person.test.util.JsonTestUtils;
import be.stesch.person.web.rs.common.PersonAppMediaType;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URISyntaxException;

import static be.stesch.person.common.web.PersonAppURIFactory.getPersonUri;
import static be.stesch.person.web.rs.common.PersonAppMediaType.PERSON_V1_JSON;
import static be.stesch.person.web.rs.common.PersonAppMediaType.PERSON_V1_XML;
import static javax.servlet.http.HttpServletResponse.*;
import static javax.ws.rs.core.HttpHeaders.CONTENT_LOCATION;
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
    private PersonAdapter personAdapter;

    @InjectMocks
    private PersonResource personResource;

    @Test
    public void testCreatePersonJson() throws Exception {
        PersonType personType = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        testCreatePerson(personType, 1L, PERSON_V1_JSON);
    }

    @Test
    public void testCreatePersonXml() throws Exception {
        PersonType personType = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        testCreatePerson(personType, 1L, PERSON_V1_XML);
    }

    @Test
    public void testUpdatePersonJson() throws Exception {
        PersonType personType = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        testUpdatePerson(personType, 1L, PERSON_V1_JSON);
    }

    @Test
    public void testUpdatePersonXml() throws Exception {
        PersonType personType = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        testUpdatePerson(personType, 1L, PERSON_V1_XML);
    }

    @Test
    public void testGetPersonJson() throws Exception {
        PersonType returnedPerson = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        testGetPerson(1L, returnedPerson, PERSON_V1_JSON);
    }

    @Test
    public void testGetPersonXml() throws Exception {
        PersonType returnedPerson = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        testGetPerson(1L, returnedPerson, PERSON_V1_XML);
    }

    private Dispatcher getDispatcher() {
        Dispatcher dispatcher = createDispatcher();
        dispatcher.getRegistry().addSingletonResource(personResource);

        return dispatcher;
    }

    private void testCreatePerson(PersonType personType, Long returnedPersonId, String mediaType)
            throws Exception {
        Dispatcher dispatcher = getDispatcher();
        MockHttpRequest request = post("/person-services/persons").contentType(mediaType);
        MockHttpResponse response = new MockHttpResponse();
        request.content(convertPersonType(personType, mediaType));

        when(personAdapter.createPerson(personType)).thenReturn(returnedPersonId);

        dispatcher.invoke(request, response);

        verify(personAdapter).createPerson(personType);
        assertThat(response.getStatus(), is(SC_CREATED));
        assertThat(response.getOutputHeaders(), hasKey(CONTENT_LOCATION));
        assertThat(response.getOutputHeaders().get(CONTENT_LOCATION), hasItem(getPersonUri(returnedPersonId)));
        assertThat(response.getContentAsString(), is(isEmptyString()));
    }

    private void testUpdatePerson(PersonType personType, Long returnedPersonId, String mediaType)
            throws Exception {
        Dispatcher dispatcher = getDispatcher();
        MockHttpRequest request = put("/person-services/persons/1").contentType(mediaType);
        MockHttpResponse response = new MockHttpResponse();
        request.content(convertPersonType(personType, mediaType));

        when(personAdapter.updatePerson(returnedPersonId, personType)).thenReturn(returnedPersonId);

        dispatcher.invoke(request, response);

        verify(personAdapter).updatePerson(returnedPersonId, personType);
        assertThat(response.getStatus(), is(SC_NO_CONTENT));
        assertThat(response.getOutputHeaders(), hasKey(CONTENT_LOCATION));
        assertThat(response.getOutputHeaders().get(CONTENT_LOCATION), hasItem(PersonAppURIFactory.getPersonUri(returnedPersonId)));
        assertThat(response.getContentAsString(), is(isEmptyString()));
    }

    private void testGetPerson(Long personId, PersonType returnedPersonType, String mediaType) throws URISyntaxException {
        Dispatcher dispatcher = getDispatcher();
        MockHttpRequest request = get("/person-services/persons/1").accept(mediaType);
        MockHttpResponse response = new MockHttpResponse();

        when(personAdapter.getPerson(personId)).thenReturn(returnedPersonType);

        dispatcher.invoke(request, response);

        verify(personAdapter).getPerson(personId);
        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getContentAsString(), containsString(returnedPersonType.getFirstName()));
        assertThat(response.getContentAsString(), containsString(returnedPersonType.getLastName()));
        assertThat(response.getContentAsString(), containsString(returnedPersonType.getMaritalStatus()));
        assertThat(response.getOutputHeaders(), not(hasKey(CONTENT_LOCATION)));
    }

    private byte[] convertPersonType(PersonType personType, String mediaType) throws Exception {
        byte[] personByteArray;
        if (PersonAppMediaType.PERSON_V1_JSON.equals(mediaType)) {
            personByteArray = JsonTestUtils.convert(personType);
        } else if (PersonAppMediaType.PERSON_V1_XML.equals(mediaType)) {
            personByteArray = JaxbTestUtils.convert(personType, PersonType.class);
        } else {
            throw new IllegalArgumentException("MediaType " + mediaType + " not supported!");
        }
        return personByteArray;
    }

}