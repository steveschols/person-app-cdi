package be.stesch.person.model.listener;

import be.stesch.person.model.Person;
import be.stesch.person.model.event.MaritalStatusChangeEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.unitils.database.util.TransactionMode.ROLLBACK;

/**
 * @author Steve Schols
 * @since 3/09/2015
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@JpaEntityManagerFactory(persistenceUnit = "person-app-test")
@Transactional(ROLLBACK)
public class OriginalStateEntityListenerTest {

    @Mock
    private Event<MaritalStatusChangeEvent> notificationEvent;
    @Mock
    private BeanManager beanManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DataSet("/datasets/PersonDataSet.xml")
    public void testPersonOriginalMaritalStatus() throws Exception {
        Person person = entityManager.find(Person.class, 1L);

        assertThat(person.getMaritalStatus(), is(SINGLE));
        assertThat(person.getOriginalMaritalStatus(), is(SINGLE));

        person.setMaritalStatus(MARRIED);

        assertThat(person.getMaritalStatus(), is(MARRIED));
        assertThat(person.getOriginalMaritalStatus(), is(SINGLE));
    }

}