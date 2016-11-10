package be.stesch.person.web.resource;

import be.stesch.person.business.GetPersonBO;
import be.stesch.person.model.Person;
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
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.hamcrest.Matchers.is;
import static org.jboss.resteasy.mock.MockDispatcherFactory.createDispatcher;
import static org.jboss.resteasy.mock.MockHttpRequest.get;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonResourceTest {

    @Mock
    private GetPersonBO getPersonBO;

    @InjectMocks
    private PersonResource personResource;

    @Test
    public void testGetPersonApplicationJson() throws Exception {
        testGetPerson(APPLICATION_JSON);
    }

    @Test
    public void testGetPersonXml() throws Exception {
        testGetPerson(APPLICATION_XML);
    }

    private void testGetPerson(String mediaType) throws URISyntaxException {
        Dispatcher dispatcher = createDispatcher();
        dispatcher.getRegistry().addSingletonResource(personResource);

        MockHttpRequest request = get("/person-services/persons/1").accept(mediaType);
        MockHttpResponse response = new MockHttpResponse();

        Person person = new Person("John", "Doe", SINGLE);

        when(getPersonBO.execute()).thenReturn(person);

        dispatcher.invoke(request, response);

        verify(getPersonBO).execute();
        assertThat(response.getStatus(), is(SC_OK));
    }

}