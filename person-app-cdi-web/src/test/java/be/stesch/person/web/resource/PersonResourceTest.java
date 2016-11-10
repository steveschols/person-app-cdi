package be.stesch.person.web.resource;

import be.stesch.person.business.CreatePersonBO;
import be.stesch.person.business.GetPersonBO;
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
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.hamcrest.Matchers.is;
import static org.jboss.resteasy.mock.MockDispatcherFactory.createDispatcher;
import static org.jboss.resteasy.mock.MockHttpRequest.get;
import static org.jboss.resteasy.mock.MockHttpRequest.post;
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
    private GetPersonBO getPersonBO;

    @InjectMocks
    private PersonResource personResource;

    @Test
    public void testCreatePersonJson() throws Exception {
        Person person = new Person("John", "Doe", SINGLE);
        testCreatePerson(person, JsonTestUtils.convert(person), APPLICATION_JSON);
    }

    @Test
    public void testCreatePersonXml() throws Exception {
        Person person = new Person("John", "Doe", SINGLE);
        testCreatePerson(person, JaxbTestUtils.convert(person), APPLICATION_XML);
    }

    @Test
    public void testGetPersonJson() throws Exception {
        testGetPerson(APPLICATION_JSON);
    }

    @Test
    public void testGetPersonXml() throws Exception {
        testGetPerson(APPLICATION_XML);
    }

    private Dispatcher getDispatcher() {
        Dispatcher dispatcher = createDispatcher();
        dispatcher.getRegistry().addSingletonResource(personResource);

        return dispatcher;
    }

    private void testCreatePerson(Person person, byte[] personByteArray, String mediaType) throws Exception {
        Dispatcher dispatcher = getDispatcher();
        MockHttpRequest request = post("/person-services/persons").contentType(mediaType);
        MockHttpResponse response = new MockHttpResponse();
        request.content(personByteArray);

        when(createPersonBO.execute()).thenReturn(person);

        dispatcher.invoke(request, response);

        verify(createPersonBO).execute();
        assertThat(response.getStatus(), is(SC_CREATED));
    }

    private void testGetPerson(String mediaType) throws URISyntaxException {
        Dispatcher dispatcher = getDispatcher();
        MockHttpRequest request = get("/person-services/persons/1").accept(mediaType);
        MockHttpResponse response = new MockHttpResponse();

        Person person = new Person("John", "Doe", SINGLE);

        when(getPersonBO.execute()).thenReturn(person);

        dispatcher.invoke(request, response);

        verify(getPersonBO).execute();
        assertThat(response.getStatus(), is(SC_OK));
    }

}