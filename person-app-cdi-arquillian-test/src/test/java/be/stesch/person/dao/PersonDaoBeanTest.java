package be.stesch.person.dao;

import be.stesch.person.model.Person;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static be.stesch.person.model.PersonTestData.createPerson;
import static be.stesch.person.test.ArquillianUtils.createPersonAppEnterpriseArchive;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Steve Schols
 * @since 28/08/2015
 */
@PersistenceTest
@RunWith(Arquillian.class)
public class PersonDaoBeanTest {

    @Inject
    private PersonDao personDao;

    @Deployment
    public static EnterpriseArchive createDeployment() {
        return createPersonAppEnterpriseArchive();
    }

    @Test
    public void persistPerson() throws Exception {
        Person person = createPerson(null, "Test", "Person", SINGLE);

        personDao.persist(person);

        assertThat(person.getId(), is(not(nullValue())));
        assertThat(person.getCreationDate(), is(not(nullValue())));
        assertThat(person.getLastUpdateDate(), is(nullValue()));
    }

    @Test
    @UsingDataSet
    @ShouldMatchDataSet
    public void updatePerson() throws Exception {
        Person person = personDao.find(1L);

        assertThat(person.getOriginalMaritalStatus(), is(SINGLE));

        person.setFirstName("John");
        person.setLastName("Doe");
        person.setMaritalStatus(MARRIED);

        person = personDao.merge(person);

        // The flush might or might not be necessary, depending on the used implementation as per JPA-specification;
        //
        // The PreUpdate and PostUpdate callbacks occur before and after the database update operations to
        // entity data respectively. These database operations may occur at the time the entity state is updated or
        // they may occur at the time state is flushed to the database (which may be at the end of the transaction).
        personDao.flush();

        assertThat(person.getLastUpdateDate(), is(not(nullValue())));
    }

}
