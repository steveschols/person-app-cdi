package be.stesch.person.dao;

import be.stesch.person.model.Person;
import be.stesch.person.model.PersonTestData;
import be.stesch.person.model.event.MaritalStatusChangeEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import java.time.LocalDateTime;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static be.stesch.person.model.PersonTestData.createPerson;
import static java.time.LocalDateTime.of;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.unitils.database.util.TransactionMode.ROLLBACK;
import static org.unitils.orm.jpa.JpaUnitils.injectEntityManagerInto;

/**
 * @author Steve Schols
 * @since 28/08/2015
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@JpaEntityManagerFactory(persistenceUnit = "person-app-test")
@Transactional(ROLLBACK)
public class PersonDaoBeanTest {

    @Mock
    private Event<MaritalStatusChangeEvent> notificationEvent;
    @Mock
    private BeanManager beanManager;

    private PersonDao personDao = new PersonDaoBean();

    @Before
    public void setUp() {
        injectEntityManagerInto(personDao);
        initMocks(this);
    }

    @Test
    public void testPersistPerson() throws Exception {
        Person person = createPerson(null, "Test", "Person", SINGLE);

        personDao.persist(person);

        assertThat(person.getId(), is(not(nullValue())));
    }

    @Test
    @DataSet("datasets/PersonDataSet.xml")
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
