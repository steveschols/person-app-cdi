package be.stesch.person.common;

import org.junit.Test;

import java.net.URI;

import static be.stesch.person.common.web.PersonAppURIFactory.PERSON_SERVICES_PATH;
import static be.stesch.person.common.web.PersonAppURIFactory.getPersonUri;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Steve Schols on 11/10/2016.
 */
public class PersonAppURIFactoryTest {

    @Test
    public void testGetPersonUri() throws Exception {
        URI personUri = getPersonUri(1L);
        assertThat(personUri, is(new URI(PERSON_SERVICES_PATH + "/1")));
    }

}