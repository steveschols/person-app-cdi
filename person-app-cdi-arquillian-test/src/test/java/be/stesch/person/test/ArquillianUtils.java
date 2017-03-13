package be.stesch.person.test;

import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

/**
 * Created by Steve.Schols on 26/02/2017.
 */
public abstract class ArquillianUtils {

    public static EnterpriseArchive createPersonAppEnterpriseArchive() {
//        File earFile = new File("../person-app-cdi-ear/target/person-app.ear");
//        return createFromZipFile(EnterpriseArchive.class, earFile)
        return create(EnterpriseArchive.class)
                .addAsLibrary(create(JavaArchive.class).addPackages(true, "be.stesch.person.model"))
                .addAsLibrary(create(JavaArchive.class).addAsResource("arquillian-persistence.xml", "META-INF/persistence.xml"))
                .addAsModule(create(JavaArchive.class).addPackages(true, "be.stesch.person.dao"));
    }

}
