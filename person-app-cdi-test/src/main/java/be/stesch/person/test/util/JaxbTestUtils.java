package be.stesch.person.test.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

import static javax.xml.bind.JAXBContext.newInstance;

/**
 * Created by Steve Schols on 11/10/2016.
 */
public final class JaxbTestUtils {

    private JaxbTestUtils() {
    }

    public static byte[] convert(Object obj) throws JAXBException {
        JAXBContext jaxbContext = newInstance(obj.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();

        final StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);

        return writer.toString().getBytes();
    }

}
