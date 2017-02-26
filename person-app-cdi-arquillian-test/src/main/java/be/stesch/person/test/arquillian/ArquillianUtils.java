package be.stesch.person.test.arquillian;

import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;

import java.io.File;

import static org.jboss.shrinkwrap.api.ShrinkWrap.createFromZipFile;

/**
 * Created by Steve.Schols on 26/02/2017.
 */
public abstract class ArquillianUtils {

    public static EnterpriseArchive createPersonAppEnterpriseArchive() {
        File earFile = new File("../person-app-cdi-ear/target/person-app.ear");

        return createFromZipFile(EnterpriseArchive.class, earFile);
    }

}
