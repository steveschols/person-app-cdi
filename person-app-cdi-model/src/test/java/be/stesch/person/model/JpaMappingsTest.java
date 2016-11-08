package be.stesch.person.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.orm.jpa.JpaUnitils;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import static org.unitils.database.util.TransactionMode.ROLLBACK;

/**
 * @author Steve Schols
 * @since 27/08/2015
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
//@JpaEntityManagerFactory(persistenceUnit = "person-app-test", configFile = "/src/test/resources/META-INF/persistence-test.xml")
@JpaEntityManagerFactory(persistenceUnit = "person-app-test")
@Transactional(ROLLBACK)
public class JpaMappingsTest {

    @Test
    public void testJpaMappings() throws Exception {
        JpaUnitils.assertMappingWithDatabaseConsistent();
    }

}
