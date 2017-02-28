package be.stesch.person.dao;

import be.stesch.person.model.Person;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static be.stesch.person.test.arquillian.ArquillianUtils.createPersonAppEnterpriseArchive;
import static java.time.LocalDateTime.of;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static org.hamcrest.Matchers.*;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertThat;

/**
 * @author Steve Schols
 * @since 28/08/2015
 */
@RunWith(Arquillian.class)
public class PersonDaoBeanTest {

    @Inject
    private PersonDao personDao;

    @Deployment
    public static EnterpriseArchive createDeployment() {
        JavaArchive testArchive = create(JavaArchive.class)
                .addClass(PersonDaoBeanTest.class);

        return createPersonAppEnterpriseArchive()
                .addAsLibrary(testArchive);
    }

    @Test
//    @Transactional
    // TODO See https://docs.jboss.org/author/display/ARQ/Persistence
    public void testPersistPerson() throws Exception {
        Person person = new Person(null, "Test", "Person", SINGLE);

        personDao.persist(person);

        assertThat(person.getId(), is(not(nullValue())));
    }

    @Test
//    @DataSet("datasets/PersonDataSet.xml")
    public void testUpdatePerson() {
        Long personId = 1L;
        Person person = personDao.find(personId);

        LocalDateTime expectedCreationDate = of(2015, 8, 27, 0, 0);
        LocalDateTime actualCreationDate = ofInstant(person.getCreationDate().toInstant(), systemDefault());
        assertThat(person.getId(), is(personId));
        assertThat(person.getFirstName(), is("Test"));
        assertThat(person.getLastName(), is("Person"));
        assertThat(person.getMaritalStatus(), is(SINGLE));
        assertThat(actualCreationDate, is(expectedCreationDate));

        person.setFirstName("John");
        person.setLastName("Doe");
        person.setMaritalStatus(MARRIED);

        person = personDao.merge(person);

        assertThat(person.getId(), is(personId));
        assertThat(person.getFirstName(), is("John"));
        assertThat(person.getLastName(), is("Doe"));
        assertThat(person.getMaritalStatus(), is(MARRIED));
        assertThat(actualCreationDate, is(expectedCreationDate));
    }

}
