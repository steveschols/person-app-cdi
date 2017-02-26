package be.stesch.person.model;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static be.stesch.person.test.arquillian.ArquillianUtils.createPersonAppEnterpriseArchive;
import static org.hamcrest.Matchers.is;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertThat;

/**
 * Created by Steve.Schols on 12/02/2017.
 */
@RunWith(Arquillian.class)
public class PersonTest {

    @Deployment
    public static EnterpriseArchive createDeployment() {
        JavaArchive testArchive = create(JavaArchive.class)
                .addClass(PersonTest.class);

        return createPersonAppEnterpriseArchive()
                .addAsLibrary(testArchive);
    }

    @Test
    public void testPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("Steve");
        person.setLastName("Schols");

        assertThat(person.getFirstName(), is("Steve"));
        assertThat(person.getLastName(), is("Schols"));
    }

}
