package be.stesch.person.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static be.stesch.person.model.PersonTestData.createPerson;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.unitils.database.util.TransactionMode.ROLLBACK;
import static org.unitils.orm.jpa.JpaUnitils.flushDatabaseUpdates;

/**
 * Created by u420643 on 3/14/2017.
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@JpaEntityManagerFactory(persistenceUnit = "person-app-test")
@Transactional(ROLLBACK)
public class PersonTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testAuditDataOnPersist() throws Exception {
        Person person = createPerson(null, "Test", "Person", SINGLE);

        entityManager.persist(person);

        assertThat(person.getCreationDate(), is(not(nullValue())));
        assertThat(person.getLastUpdateDate(), is(nullValue()));
    }

    @Test
    @DataSet("datasets/PersonDataSet.xml")
    // TODO: Move PersonDataSet.xml to person-app-test module
    // If I do that while using unitils 3.3, I get an "URI is not hierarchical" exception.
    // This should be fixed in unitils 3.4.2, but then other persistence-related issues occur.
    public void testAuditDataOnUpdate() throws Exception {
        Person person = entityManager.find(Person.class, 1L);
        person.setFirstName("John");
        person.setLastName("Doe");

        person = entityManager.merge(person);
        // The flush might or might not be necessary, depending on the used implementation as per JPA-specification;
        //
        // The PreUpdate and PostUpdate callbacks occur before and after the database update operations to
        // entity data respectively. These database operations may occur at the time the entity state is updated or
        // they may occur at the time state is flushed to the database (which may be at the end of the transaction).
        flushDatabaseUpdates();

        assertThat(person.getLastUpdateDate(), is(not(nullValue())));
    }

    @Test
    @DataSet("/datasets/PersonDataSet.xml")
    // TODO: Move PersonDataSet.xml to person-app-test module
    // If I do that while using unitils 3.3, I get an "URI is not hierarchical" exception.
    // This should be fixed in unitils 3.4.2, but then other persistence-related issues occur.
    public void testOriginalMaritalStatus() throws Exception {
        Person person = entityManager.find(Person.class, 1L);

        assertThat(person.getMaritalStatus(), is(SINGLE));
        assertThat(person.getOriginalMaritalStatus(), is(SINGLE));

        person.setMaritalStatus(MARRIED);

        assertThat(person.getMaritalStatus(), is(MARRIED));
        assertThat(person.getOriginalMaritalStatus(), is(SINGLE));
    }

}
